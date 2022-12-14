package com.dongyang.gg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.dongyang.gg.R;
import com.dongyang.gg.data.chat.AbstractData;
import com.dongyang.gg.data.chat.LiteralChat;
import com.dongyang.gg.view.ChatListView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Chat_ListviewActivity extends AppCompatActivity{

    private ListView listViewChat;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);

        listViewChat = findViewById(R.id.listViewChatting);
        adapter = new ListViewAdapter();

        BottomNavigationView BNV = findViewById(R.id.bottom_navigation);
        BNV.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Category:
                        Intent intent = new Intent(getApplicationContext(), Item_ListActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.Chatting:
                        Intent intent1 = new Intent(getApplicationContext(), Chat_ListviewActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.WishList:
                        Intent intent2 = new Intent(getApplicationContext(), Item_ListActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.MyPage:
                        Intent intent3 = new Intent(getApplicationContext(), Chat_ListviewActivity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.Main:
                        Intent intent4 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent4);
                        return true;
                }
                return false;
            }
        });

        // For Test
        adapter.addItem(new LiteralChat("khy010802", "local", new Date().getTime(), false, "????????? ??????????????????."));
        //adapter.addItem(new LiteralChat( UUID.randomUUID().toString(), "khy010802", "local2", new Date().getTime() - 300000L, 1, "????????? ?????????2?????????."));
        //adapter.addItem(new LiteralChat( UUID.randomUUID().toString(), "khy010802", "local3", new Date().getTime() - 500000L, 1, "????????? ?????????3?????????."));
        //adapter.addItem(new LiteralChat(UUID.randomUUID().toString(), "khy010802", "local4", new Date().getTime() - 1000000L, 1, "????????? ?????????4?????????."));

        listViewChat.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public class ListViewAdapter extends BaseAdapter {
        ArrayList<LiteralChat> chats = new ArrayList<>();
        // recv_id??? ????????? id??? chat ????????? ???, send_id ?????? ?????? ?????? ????????? ??????
        // ????????? long ?????? ????????????. ???, ?????? ?????? ?????? 0????????? ??????.

        public int getCount(){
            return this.chats.size();
        }

        public Object getItem(int position){
            return chats.get(position);
        }

        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ChatListView view = null;
            if(view == null){
                view = new ChatListView(getApplicationContext());
            } else {
                view = (ChatListView) convertView;
            }

            AbstractData cd = chats.get(position);
            view.setID(cd.getSend_id());
            String s = cd.getDTO().message.get("type");
            String msg;
            if(s.equalsIgnoreCase("Map"))
                msg = "????????? ??????????????????.";
            else if(s.equalsIgnoreCase("Image"))
                msg = "????????? ??????????????????.";
            else
                msg = cd.getDTO().message.get("value");
            view.setMessage(msg);
            view.setTime(cd.getTime());

            view.setOnClickListener((v) -> {
                Intent intent1 = new Intent(getApplicationContext(), Chat_Activity.class);
                startActivity(intent1);
            });

            return view;
        }

        public void addItem(LiteralChat literalChat){
            chats.add(literalChat);
        }
    }

}

