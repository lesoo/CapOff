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

public class MainActivity extends AppCompatActivity {
    public static String nowid="lee";
    ImageView search_btn;
    ImageView etc_item;
    ImageView clothes_item;
    ImageView elect_item;
     ImageView furniture_item;
     ImageView hobby_item;
     ImageView pet_item;
     ImageView sports_item;
     ImageView book_item;
     ImageView baby_item;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            search_btn = findViewById(R.id.search_btn);
            etc_item = findViewById(R.id.recent_item);
            clothes_item = findViewById(R.id.clothes_item);
            elect_item = findViewById(R.id.elect_item);
            furniture_item = findViewById(R.id.furniture_item);
            hobby_item = findViewById(R.id.hobby_item);
            pet_item = findViewById(R.id.pet_item);
            sports_item = findViewById(R.id.sports_item);
            book_item = findViewById(R.id.book_item);
            baby_item = findViewById(R.id.baby_item);

            BottomNavigationView BNV = findViewById(R.id.bottom_navigation);
            BNV.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.Category:
                            Intent intent = new Intent(getApplicationContext(), Item_ListActivity.class);
                            intent.putExtra("ctname", "all");
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


        search_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search_ListActivity.class);
                startActivity(intent);
            }
        });
        clothes_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Item_ListActivity.class);
                intent.putExtra("ctname", "의류");
                startActivity(intent);
            }
        });
        elect_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Item_ListActivity.class);
                intent.putExtra("ctname", "가전");
                startActivity(intent);
            }
        });
        sports_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Item_ListActivity.class);
                intent.putExtra("ctname", "스포츠");
                startActivity(intent);
            }
        });
        hobby_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Item_ListActivity.class);
                intent.putExtra("ctname", "취미");
                startActivity(intent);
            }
        });
        pet_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Item_ListActivity.class);
                intent.putExtra("ctname", "애완");
                startActivity(intent);
            }
        });
        book_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Item_ListActivity.class);
                intent.putExtra("ctname", "서적");
                startActivity(intent);
            }
        });
        furniture_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Item_ListActivity.class);
                intent.putExtra("ctname", "가구");
                startActivity(intent);
            }
        });
        baby_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Item_ListActivity.class);
                intent.putExtra("ctname", "육아");
                startActivity(intent);
            }
        });
        etc_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Item_ListActivity.class);
                intent.putExtra("ctname", "기타");
                startActivity(intent);
            }
        });

    }

    }




