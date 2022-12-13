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
import com.dongyang.gg.activity.Search_ListActivity;
import com.dongyang.gg.data.item.ItemData;

import java.util.ArrayList;

public class SlogAdapter extends RecyclerView.Adapter<SlogAdapter.CustomViewHolder> {

    private ArrayList<ItemData> mList = null;
    private Activity context = null;





    public SlogAdapter(Activity context, ArrayList<ItemData> list) {
        this.context = context;
        this.mList = list;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView name;
        protected ImageView del, search;






        public CustomViewHolder(View view) {
            super(view);




            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.del=(ImageView)view.findViewById(R.id.delkey);
            this.search=(ImageView)view.findViewById(R.id.searchkey);

            this.search.setOnClickListener(new View.OnClickListener() {
                String keyword=name.toString();

                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), Search_ListActivity.class);
                    intent.putExtra("key", keyword);

                    context.startActivity(intent);
                }
            });

        }
    }




    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_view, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        //viewholder.pic.setText(mList.get(position).getItem_pic());
        viewholder.name.setText(mList.get(position).getItem_name());


        viewholder.search.setOnClickListener(new View.OnClickListener() {
            String keyword=viewholder.name.toString();

            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), Search_ListActivity.class);
                intent.putExtra("key", keyword);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }




}