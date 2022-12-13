package com.dongyang.gg.data.chat;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dongyang.gg.data.dto.ChatDTO;
import com.dongyang.gg.view.ChatView;


public class ImageChat extends AbstractData{

    private final Uri uri;
    private String local_path;

    private final static String SERVER_IP = "http://15.164.98.47";

    public ImageChat(ChatDTO dto){
        super(dto);
        this.uri = Uri.parse(SERVER_IP + "/chat/" + this.dto.message.get("filename"));
    }

    public ImageChat(String send_id, String recv_id, long time, boolean read, Uri uri, String path){
        super("Image", send_id, recv_id, time, read);
        String[] split = path.split("/");
        String filename = split[split.length-1];
        this.uri = Uri.parse(SERVER_IP + "/chat/" + path);
        this.local_path = path;
        dto.message.put("filename", filename);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View build(AppCompatActivity act, ChatView cv) {

        ImageView imageView = new ImageView(cv.getContext());
        if(local_path != null){
            Glide.with(cv.getContext())
                    .load(local_path)
                    .override(400, 400)
                    .into(imageView);
/*            Bitmap bm = BitmapFactory.decodeFile(local_path);
            imageView.setImageBitmap(bm);*/
        } else {
            System.out.println("URI : " + uri);
            Glide.with(cv.getContext())
                    .load(uri)
                    .override(400, 400)
                    .into(imageView);
        }
        cv.setTime(getTime());

        return imageView;
    }

}
