package com.dongyang.gg.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dongyang.gg.R;
import com.dongyang.gg.activity.Chat_Activity;
import com.dongyang.gg.data.chat.AbstractData;
import com.dongyang.gg.data.chat.ImageChat;
import com.dongyang.gg.data.chat.LiteralChat;
import com.dongyang.gg.data.chat.MapChat;
import com.dongyang.gg.fragment.MapFragment;
import com.dongyang.gg.view.ChatView;

import java.util.ArrayList;
import java.util.LinkedList;

public class ChattingAdapter extends BaseAdapter {

    private final Chat_Activity main;

    public ChattingAdapter(Chat_Activity main){
        this.main = main;
    }


    LinkedList<AbstractData> chats = new LinkedList<>();

    public int getCount(){
        return this.chats.size();
    }

    public Object getItem(int position){
        return chats.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        AbstractData ad = chats.get(position);
        ChatView cv = new ChatView(main.getApplicationContext());
        View view = ad.build(main, cv);



        LinearLayout ll = cv.findViewById(R.id.layoutChatting);
        View textLayout = ll.getChildAt(0);
        View textTime = ll.getChildAt(1);
        View textRead = ll.getChildAt(2);

        // TODO : 본인 아이디로 수정
        if(ad.getSend_id().equalsIgnoreCase("khy010802")){ // 본인이 보낸 메세지

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.RIGHT;
            ll.setLayoutParams(params);

            textLayout.setBackgroundColor(Color.parseColor("#ffffcf"));

            ll.removeAllViewsInLayout();
            ll.addView(textRead);
            ll.addView(textTime);


            ll.addView(textLayout);

        } else if(!ad.getRead()){ // 남이 보낸 메세지
            // TODO : 읽었다 패킷 서버로 보내기
            ad.setRead(true);
        }

        // TODO : 읽음 시간 나면 추가할 것
        textRead.setVisibility(View.INVISIBLE);
        // 읽음, 안읽음 처리
        if(!ad.getRead()){
            //
        }

        cv.getInnerLayout().addView(view);

        ad.uploadTextRead((TextView) textRead);

        return cv;
    }

    public void addItemReversed(AbstractData ad){
        chats.addFirst(ad);
    }

    public void addItem(AbstractData literalChat){
        chats.addLast(literalChat);
    }


}
