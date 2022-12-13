package com.dongyang.gg.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongyang.gg.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatListView extends LinearLayout {

    TextView textSenderId;
    TextView textMsg;
    TextView textTime;

    public ChatListView(Context context){
        super(context);
        init(context);
    }

    public ChatListView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.chat_list_item, this, true);

        textSenderId = findViewById(R.id.textSenderID);
        textMsg = findViewById(R.id.textRecentChat);
        textTime = findViewById(R.id.textRecentDate);
    }

    public void setID(String sender_id){
        textSenderId.setText(sender_id);
    }

    public void setMessage(String msg){
        textMsg.setText(msg);
    }

    public void setTime(long time){
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        textTime.setText(format.format(date));
    }

}
