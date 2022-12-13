package com.dongyang.gg.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongyang.gg.R;
import com.dongyang.gg.adapter.MyOItemsAdapter;
import com.dongyang.gg.data.item.MyOItemData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Bid_ListActivity extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE = 200;
    public static String IP_ADDRESS = "15.164.98.47";
    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextPrice;
    private TextView mTextViewResult;
    private ArrayList<MyOItemData> mArrayList;
    private MyOItemsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;
    Button registItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bid_list);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());
//
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/p_myo.php", "");
//
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
                        Intent intent3 = new Intent(getApplicationContext(), Login_Activity.class);
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



        mArrayList = new ArrayList<>();

        mAdapter = new MyOItemsAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);


//        Button button_all = (Button) findViewById(R.id.button_main_all);
//        button_all.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                mArrayList.clear();
//                mAdapter.notifyDataSetChanged();
//
//                GetData task = new GetData();
//                task.execute( "http://" + IP_ADDRESS + "/p_myo.php", "");
//            }
//        });

//        registItem=findViewById(R.id.registItem);
//
//        registItem.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), ItemRegist.class);
//                startActivity(intent);
//            }
//        });
    }




    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Bid_ListActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {//json 출력 or error 출력
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        String TAG_JSON="result";
        String TAG_PIC = "pic";
        String TAG_NAME = "name";
        String TAG_PRICE ="price";
        String TAG_NUM ="num";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String pic = item.getString(TAG_PIC);
                String name = item.getString(TAG_NAME);
                String price = item.getString(TAG_PRICE);
                String num = item.getString(TAG_NUM);

                MyOItemData itemData = new MyOItemData();

                itemData.setItem_pic(pic);
                itemData.setItem_name(name);
                itemData.setItem_price(price);
                itemData.setItem_num(num);

                mArrayList.add(itemData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}
