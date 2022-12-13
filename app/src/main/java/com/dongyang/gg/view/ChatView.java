package com.dongyang.gg.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongyang.gg.R;





import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatView extends LinearLayout {

    LinearLayout layoutItem;
    TextView textTime;

    public ChatView(Context context){
        super(context);
        init(context);
    }

    public ChatView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.chat_item, this, true);

        layoutItem = findViewById(R.id.layoutChattingItem);
        textTime = findViewById(R.id.textRecentDate);
    }

    public LinearLayout getInnerLayout(){
        return this.layoutItem;
    }

    public void setTime(long time){
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        textTime.setText(format.format(date));
    }

}
