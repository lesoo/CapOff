package com.dongyang.gg.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongyang.gg.R;
import com.dongyang.gg.adapter.DetailAdapter;
import com.dongyang.gg.data.dto.DetailData;
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


public class SlogListActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    public static String IP_ADDRESS = "15.164.98.47";
    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextPrice;
    private TextView mTextViewResult;
    private ArrayList<DetailData> mArrayList;
    private DetailAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;
public String nowid="lee";
private SearchView searchkey;
private ImageView search_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchkey=(SearchView)findViewById(R.id.search_insert);
        search_btn=(ImageView)findViewById(R.id.search_btn);

//        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/search.php", nowid);//수정 아이템 상세 출력으로
        // inum 안넘어가서 php에 9값 넣어놓음

        BottomNavigationView BNV = findViewById(R.id.bottom_navigation);
        BNV.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Category:
                        Intent intent = new Intent(getApplicationContext(), SlogListActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.Chatting:
                        Intent intent1 = new Intent(getApplicationContext(), Chat_ListviewActivity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.WishList:
                        Intent intent2 = new Intent(getApplicationContext(), SlogListActivity.class);
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

        mAdapter = new DetailAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        searchkey.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/search_i.php", query);
                Intent intents = new Intent(getApplicationContext(), Search_ListActivity.class);
                intents.putExtra("keyword", query);
                startActivity(intents);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword=searchkey.toString();
                    InsertData task = new InsertData();
                    task.execute("http://" + IP_ADDRESS + "/search_i.php", keyword);
                    Intent intents = new Intent(getApplicationContext(), Search_ListActivity.class);
                    intents.putExtra("keyword", keyword);
                    startActivity(intents);

            }
        });



    }

    class InsertData extends AsyncTask<String, Void, String> {//or intent로 keyword 넘겨서 출력
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progressDialog=ProgressDialog.show(SlogListActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "POST response - "+result);
        }

        @Override
        protected String doInBackground(String... params){
            String keyword = (String)params[1];


            String serverURL=(String)params[0];
            String postparameters="&keyword="+keyword;

            try{
                URL url=new URL(serverURL);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream=httpURLConnection.getOutputStream();
                outputStream.write(postparameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode=httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code-"+responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode==HttpURLConnection.HTTP_OK){
                    inputStream=httpURLConnection.getInputStream();
                }
                else{
                    inputStream=httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                StringBuilder sb=new StringBuilder();
                String line=null;

                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();
            }catch(Exception e ){
                Log.d(TAG, "InsertData: Error", e);

                return new String("Error:"+e.getMessage());
            }

        }
    }



    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SlogListActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {//json 출력 or error 출력
            super.onPostExecute(result);

            progressDialog.dismiss();
           // mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

              //  mTextViewResult.setText(errorString);
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
        String TAG_INTRO ="intro";
        String TAG_NUM ="num";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String pic = item.getString(TAG_PIC);
                String name = item.getString(TAG_NAME);
                String price = item.getString(TAG_PRICE);
                String intro = item.getString(TAG_INTRO);
                String num = item.getString(TAG_NUM);

                DetailData itemData = new DetailData();

                itemData.setItem_pic(pic);
                itemData.setItem_name(name);
                itemData.setItem_price(price);
                itemData.setItem_intro(intro);
                itemData.setItem_num(num);

                mArrayList.add(itemData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}
