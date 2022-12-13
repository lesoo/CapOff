package com.dongyang.gg.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.constraintlayout.widget.ConstraintSet;

import com.dongyang.gg.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class My_profileActivity extends Activity{

    LinearLayout bid_list;
    LinearLayout selling_list;
    LinearLayout log_out;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        bid_list = findViewById(R.id.bid_list_btn);
        selling_list = findViewById(R.id.selling_list_btn);
        log_out = findViewById(R.id.logout_btn);

        bid_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Bid_ListActivity.class);
                startActivity(intent);
            }
        });
        selling_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Selling_ListActivity.class);
                startActivity(intent);
            }
        });
        log_out.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
            }
        });

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
                        Intent intent2 = new Intent(getApplicationContext(), Wish_ListActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.MyPage:
                        Intent intent3 = new Intent(getApplicationContext(), My_profileActivity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.home:
                        Intent intent4 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent4);
                        return true;
                }
                return false;
            }
        });
    }
}