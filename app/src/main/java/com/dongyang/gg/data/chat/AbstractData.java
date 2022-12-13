package com.dongyang.gg.data.chat;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dongyang.gg.data.RoomData;
import com.dongyang.gg.data.dto.ChatDTO;
import com.dongyang.gg.view.ChatView;

import java.util.HashMap;

public abstract class AbstractData {

    protected final int MAX_WIDTH = 600;
    protected final int MAX_HEIGHT = 600;

    protected ChatDTO dto;

    private TextView textRead;

    public AbstractData(ChatDTO dto){
        this.dto = dto;
    }

    public AbstractData(String type, String send_id, String recv_id, long time, boolean read){
        dto = new ChatDTO();
        dto.id = -1;
        dto.message = new HashMap<>();
        dto.message.put("type", type);
        dto.sender = send_id;
        dto.RoomCode = RoomData.getRoomCode(send_id, recv_id);
        dto.date = time;
        dto.read = read;
    }

    public String getType(){ return this.dto.message.get("type"); }

    public String getSend_id() {
        return dto.sender;
    }

    public int getID() { return this.dto.id; }

    public String getRoomCode(){
        return this.dto.RoomCode;
    }

    public long getTime() {
        return dto.date;
    }

    public boolean getRead() {
        return dto.read;
    }

    public void setRead(boolean bool){
        dto.read = bool;
    }

    public void uploadTextRead(TextView tv){
        this.textRead = tv;
    }

    public ChatDTO getDTO(){
        return this.dto;
    }


    public abstract View build(AppCompatActivity act, ChatView view);

}
