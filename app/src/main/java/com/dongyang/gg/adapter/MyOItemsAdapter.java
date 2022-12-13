package com.dongyang.gg.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dongyang.gg.R;
import com.dongyang.gg.activity.DetailListActivity;
import com.dongyang.gg.data.item.MyOItemData;

import java.util.ArrayList;

public class MyOItemsAdapter extends RecyclerView.Adapter<MyOItemsAdapter.CustomViewHolder> {

    private ArrayList<MyOItemData> mList = null;
    private Activity context = null;


    public MyOItemsAdapter(Activity context, ArrayList<MyOItemData> list) {
        this.context = context;
        this.mList = list;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView pic;
        protected TextView name;
        protected TextView price;
        protected ImageView num;



        public CustomViewHolder(View view) {
            super(view);
            this.pic = (ImageView) view.findViewById(R.id.textView_list_pic);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.price = (TextView) view.findViewById(R.id.textView_list_price);
            this.num=(ImageView) view.findViewById(R.id.btn_list_detail);
            this.num.setOnClickListener(new View.OnClickListener() {
                private int num;

                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), DetailListActivity.class);
                   // intentd.putExtra("iinum", "0");//수정해야함

                    context.startActivity(intent);
                }
            });

        }
    }




    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_list_view, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        //viewholder.pic.setText(mList.get(position).getItem_pic());
        viewholder.name.setText(mList.get(position).getItem_name());
        viewholder.price.setText(mList.get(position).getItem_price());
       // viewholder.num.setText(mList.get(position).getItem_num());

         String inum=mList.get(position).getItem_num();
        viewholder.num.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), DetailListActivity.class);
               // intent.putExtra("i_num", inum);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }




}