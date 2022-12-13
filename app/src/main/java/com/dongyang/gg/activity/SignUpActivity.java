package com.dongyang.gg.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dongyang.gg.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpActivity  extends AppCompatActivity {
    private static String IP_ADDRESS="15.164.98.47";
    private static String TAG="phptest";

    private EditText mEditTextName;
    private EditText mEditTextID;
    private EditText mEditTextPW;
    private EditText mEditTextEmail;
    private EditText mEditTextPn;
    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mEditTextName=(EditText)findViewById(R.id.name);
        mEditTextID=(EditText)findViewById(R.id.id);
        mEditTextPW=(EditText)findViewById(R.id.pw);
        mEditTextEmail=(EditText)findViewById(R.id.email);
        mEditTextPn=(EditText)findViewById(R.id.pnum);

        mTextViewResult=(TextView)findViewById(R.id.textView_main_result);

//        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        Button buttonInsert=(Button)findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = mEditTextName.getText().toString();
                String id=mEditTextID.getText().toString();
                String pw=mEditTextPW.getText().toString();
                String email=mEditTextEmail.getText().toString();
                String pnum=mEditTextPn.getText().toString();

                InsertData task= new InsertData();
                task.execute("http://"+IP_ADDRESS+"/insert_c.php", name, id, pw, email, pnum);

                mEditTextName.setText("");
                mEditTextID.setText("");
                mEditTextPW.setText("");
                mEditTextEmail.setText("");
                mEditTextPn.setText("");

            }

        });

    }

    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progressDialog=ProgressDialog.show(SignUpActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();
           // mTextViewResult.setText(result);
            Log.d(TAG, "POST response - "+result);
        }

        @Override
        protected String doInBackground(String... params){
            String name = (String)params[1];
            String id=(String)params[2];
            String pw=(String)params[3];
            String email=(String)params[4];
            String pnum=(String)params[5];

            String serverURL=(String)params[0];
            String postparameters="&name="+name+"&id="+id+"&pw="+pw+"&email="+email
                    +"&pnum="+pnum;

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
}
