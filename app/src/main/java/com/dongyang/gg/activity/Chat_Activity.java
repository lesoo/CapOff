package com.dongyang.gg.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.dongyang.gg.R;
import com.dongyang.gg.adapter.ChattingAdapter;
import com.dongyang.gg.data.chat.AbstractData;
import com.dongyang.gg.data.dto.ChatDTO;
import com.dongyang.gg.data.chat.ImageChat;
import com.dongyang.gg.data.chat.LiteralChat;
import com.dongyang.gg.data.RoomData;
import com.dongyang.gg.data.chat.MapChat;
import com.dongyang.gg.fragment.MapTestActivity;
import com.dongyang.gg.util.ImageUtil;
import com.dongyang.gg.util.NotificationUtil;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Chat_Activity extends AppCompatActivity{

    //
    public static final String SERVER_IP = "http://15.164.98.47:5125";
    public static final String LOCAL_IP = "http://10.0.2.2:5125";

    private int last_chat_id;
    private boolean can_send = false;

    private Activity context;

    private Gson gson;

    private String user;
    private String target;

    private ListView listViewChat;
    private ChattingAdapter adapter;

    private Button btnSendChat;
    private ImageView btnSendLoc;
    private ImageButton btnSendImg;
    private EditText editMsg;

    private Socket mSocket;

    private ActivityResultLauncher mapCallBack;
    private double map_latitude;
    private double map_longitude;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main_page);

        // 이미지 권한
        verifyStoragePermissions(this);

        // TODO : 삭제할것
        NotificationUtil.createNotificationChannel(this);

        listViewChat = findViewById(R.id.listViewChatting);
        btnSendChat = findViewById(R.id.btnChatSend);
        btnSendLoc = findViewById(R.id.btnLocSend);
        btnSendImg = findViewById(R.id.btnImgSend);
        editMsg = findViewById(R.id.editChatting);
        adapter = new ChattingAdapter(this);

        context = this;

        gson = new Gson();

        listViewChat.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView listView, int newState) {
                if (!listViewChat.canScrollVertically(-1)) {
                    // 위로 끝까지 당기면, 메세지 업데이트 로직
                    update_log();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        btnSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChatMessage();
            }
        });

        mapCallBack = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            map_longitude = result.getData().getDoubleExtra("longitude", 0);
                            map_latitude = result.getData().getDoubleExtra("latitude", 0);

                            sendLocMessage();
                        }
                    }
                });

        btnSendLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapTestActivity.class);
                intent.putExtra("canpoint", true);
                mapCallBack.launch(intent);
            }
        });
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK && result.getData() != null){
                            Intent intent = result.getData();
                            Uri uri = intent.getData();
                            sendImageMessage(uri, getPathFromUri(uri));
                        }
                    }

                    public String getPathFromUri(Uri uri) {
                        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToNext();
                        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex("_data"));
                        cursor.close();

                        return path;
                    }
                }
        );

        btnSendImg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher.launch(intent);
            }
        });

        /*
        # 로직
        1. 데이터베이스에서 채팅 로그 불러오기
        2. 채팅 서버 접속
        3. 즐겁게 채팅한다
         */

        // 1. 데이터 베이스에서 채팅 로그 불러오기
        System.out.println("DATABASE");

        // 2. 채팅 서버 접속
        init();
        System.out.println("INIT");

        user = "khy010802";
        target = "local";

        /*sendChat(new LiteralChat(UUID.randomUUID().toString(), user, target, time, 1, "테스트 발신 메세지입니다."));
        sendChat(new LiteralChat(UUID.randomUUID().toString(), target, user, time + 2, 1, "테스트 수신 메세지입니다."));
        sendChat(new LiteralChat(UUID.randomUUID().toString(), target, user, time + 3, 1, "테스트 수신 메세지입니다.2"));
        sendChat(new LiteralChat(UUID.randomUUID().toString(),  target, user, time + 4, 1, "어어어엄청 긴 채팅 내용"
                + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용"
                + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용"
                + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용"));
        sendChat(new LiteralChat(UUID.randomUUID().toString(), target, user, time + 5, 1, "넌 누구야"));
        sendChat(new LiteralChat(UUID.randomUUID().toString(), target, user, time + 6, 1, "ㅎㅇㅎㅇ"));
        sendChat(new LiteralChat(UUID.randomUUID().toString(), user, target, time + 7, 1, "어어어엄청 긴 채팅 내용"
                + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용"
                + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용"
                + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용" + " 어어어엄청 긴 채팅 내용"));

        sendChat(new LiteralChat(UUID.randomUUID().toString(), user, target, time + 8, 1, "읽음/안읽음 테스트"));*/

        listViewChat.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        System.out.println("NOW TIME : " + System.currentTimeMillis());

        scrollDown();
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void init() {
        try {
            mSocket = IO.socket(LOCAL_IP);

            mSocket.on(Socket.EVENT_CONNECT, args -> {
                update_log();
                mSocket.emit("enter", gson.toJson(new RoomData("khy010802", "local"))); // TODO : 테스트 데이터, 수정할 것.
            });

            mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("DISCONNECTED " + args[0]);
                }
            });

            mSocket.on(Socket.EVENT_CONNECT_ERROR, args -> {
                System.out.println("CONNECT_ERROR " + args[0]);
            });

            // 상대방이 내 메세지 읽음, 해당 메세지 읽음으로 처리
           /* mSocket.on("server_read", args -> {
                AbstractData data = AbstractData.getChatData(args[0].toString());
                System.out.println(args[0]);

                if(data != null){
                    data.setRead(true);
                }
            });*/

            // 상대방이 메세지 보냈으니 UI에 업데이트
            mSocket.on("update", args -> {
                ChatDTO dto = gson.fromJson(args[0].toString(), ChatDTO.class);
                AbstractData data;
                String type = dto.message.get("type");
                switch(type){
                    default:
                    case "Literal":
                        data = new LiteralChat(dto);
                        break;
                    case "Image":
                        data = new ImageChat(dto);
                        break;
                    case "Map":
                        data = new MapChat(dto);
                        break;

                }

                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        addItem(data, false, false);
                    }
                });
            });

            mSocket.on("server_update", args -> {
                System.out.println("GET " + args[0].toString());
                List<AbstractData> dataList = new ArrayList<>();
                ChatDTO[] dto = gson.fromJson(args[0].toString(), ChatDTO[].class);
                for(ChatDTO cd : dto){
                    AbstractData data;
                    String type = cd.message.get("type");
                    switch(type){
                        default:
                        case "Literal":
                            data = new LiteralChat(cd);
                            break;
                        case "Image":
                            data = new ImageChat(cd);
                            break;
                        case "Map":
                            data = new MapChat(cd);
                            break;
                    }
                    dataList.add(data);
                }

                runOnUiThread(() -> {
                    if(!can_send){
                        for(AbstractData ad : dataList){
                            addItem(ad, true, false);
                        }
                        can_send = true;
                    } else {
                        Collections.reverse(dataList);
                        for(AbstractData ad : dataList){
                            addItem(ad, false, true);
                        }
                    }

                });
            });
            mSocket.connect();

            Log.d("SOCKET", "Connection success : " + mSocket.id());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // TODO : 로그인 데이터 받아서 여기에 넣을 것.

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.emit("left", gson.toJson(new RoomData(user, target)));
        mSocket.disconnect();
    }

    private void sendImageMessage(Uri uri, String path)
    {
        if(!can_send) return;
        new Thread(() -> {
            ImageUtil.uploadImage(path);
        }).start();

        ImageChat ic = new ImageChat(user, target, System.currentTimeMillis(), false, uri, path);
        sendChat(ic);
    }

    private void sendLocMessage(){
        if(!can_send) return;
        MapChat mc = new MapChat(user, target, System.currentTimeMillis(), false, map_latitude, map_longitude);
        sendChat(mc);
    }

    private void sendChatMessage(){
        if(!can_send) return;
        String msg = editMsg.getText().toString();
        LiteralChat cd = new LiteralChat(user, target, System.currentTimeMillis(), false, msg);
        sendChat(cd);
    }

    private void sendChat(AbstractData data){
        mSocket.emit("newMessage", gson.toJson(data.getDTO()));
        editMsg.setText("");
        addItem(data, false, false);
    }

    private void addItem(AbstractData data, boolean first, boolean all){

        System.out.println(data.getDTO().id + " CHAT ADDED " + data.getTime());
        int count = adapter.getCount();
        if(all){
            adapter.addItemReversed(data);
        } else {
            adapter.addItem(data);
        }

        adapter.notifyDataSetChanged();
        if(first)
            scrollDown();
        else{
            listViewChat.setSelection(count);
        }
    }

    public void scrollDown(){
        listViewChat.setSelection(adapter.getCount() - 1);
    }




    public void update_log(){
        ChatDTO dto;
        if(adapter.getCount() == 0){
            dto = new ChatDTO();
            dto.id = -2;
            dto.RoomCode = RoomData.getRoomCode(user, target);
        } else {
            AbstractData data = (AbstractData) adapter.getItem(0);
            dto = data.getDTO();
        }

        if(dto.id == 0 || dto.id == -1){
            return;
        }

        mSocket.emit("client_update", gson.toJson(dto));
    }
}


