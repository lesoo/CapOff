package com.dongyang.gg.data.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dongyang.gg.data.dto.ChatDTO;
import com.dongyang.gg.fragment.MapFragment;
import com.dongyang.gg.fragment.MapTestActivity;
import com.dongyang.gg.view.ChatView;
import com.google.android.gms.maps.GoogleMap;

public class MapChat extends AbstractData {

    private final double latitude;
    private final double longitude;

    private static int FRAME_LAYOUT_ID = 314159;

    private GoogleMap map;

    public MapChat(ChatDTO dto){
        super(dto);
        this.latitude = Double.parseDouble(dto.message.get("latitude"));
        this.longitude = Double.parseDouble(dto.message.get("longitude"));
    }

    public MapChat(String send_id, String recv_id, long time, boolean read, double latitude, double longitude){
        super("Map", send_id, recv_id, time, read);
        // TODO : DEBUG
        dto.message.put("latitude", String.valueOf(latitude));
        dto.message.put("longitude", String.valueOf(longitude));
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    @SuppressLint("ResourceType")
    @Override
    public View build(AppCompatActivity act, ChatView view) {

        view.setTime(getTime());

        // 틀
        LinearLayout linearLayout = new LinearLayout(view.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(600, 710, Gravity.CENTER);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // 지도
        FrameLayout frame = new FrameLayout(view.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(600, 600, Gravity.CENTER);
        frame.setLayoutParams(params);
        frame.setId(FRAME_LAYOUT_ID);
        FRAME_LAYOUT_ID += 1;
        frame.setClickable(false);

        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);

        Fragment fragment = new MapFragment();
        fragment.setArguments(bundle);

        FragmentTransaction trans = act.getSupportFragmentManager().beginTransaction();
        trans.add(frame.getId(), fragment);
        trans.commit();

        // 버튼
        Button button = new Button(view.getContext());
        LinearLayout.LayoutParams bParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        bParams.setMargins(0, 10, 0, 0);
        button.setLayoutParams(bParams);
        button.setText("자세히 보기");
        button.setWidth(600);
        button.setHeight(100);
        View.OnClickListener listener = view1 -> {
            Intent intent = new Intent(view1.getContext(), MapTestActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            intent.putExtra("canpoint", false);
            view1.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        };
        button.setOnClickListener(listener);

        linearLayout.addView(frame);
        linearLayout.addView(button);

        return linearLayout;
    }



}
