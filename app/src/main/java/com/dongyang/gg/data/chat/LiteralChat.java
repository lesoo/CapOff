package com.dongyang.gg.data.chat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dongyang.gg.data.dto.ChatDTO;
import com.dongyang.gg.view.ChatView;

public class LiteralChat extends AbstractData{

    private final String msg;

    public LiteralChat(ChatDTO dto){
        super(dto);
        this.msg = dto.message.get("value");
    }

    public LiteralChat(String send_id, String recv_id, long time, boolean read, String value){
        super("Literal", send_id, recv_id, time, read);
        this.msg = value;
        dto.message.put("value", msg);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View build(AppCompatActivity act, ChatView cv) {

        TextView view = new TextView(cv.getContext());
        view.setMaxWidth(MAX_WIDTH);
        view.setText(msg);
        view.setTextColor(Color.BLACK);
        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        cv.setTime(getTime());

        cv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        return view;
    }

}
