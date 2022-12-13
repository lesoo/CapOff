package com.dongyang.gg.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.dongyang.gg.R;
import com.dongyang.gg.util.Variables;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ItemRegistActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "15.164.98.47";
    private static String TAG = "phptest";//사진은 올라갔는데 insert 됐는지 확인 불가 안된거 같음

    //private ImageView pic;
    //private EditText pic;
    ImageView imageview;
    private EditText name;
    private EditText stprice;
    //private EditText price;
    private Spinner type;
    private RadioGroup state;
    private Button registBtn;
    private EditText intro;
    private String sstate;
    private TextView mTextViewResult;
    String iitype;
    Uri selImg;
    String thePath;
    String filename;
    String[] items = {"가전", "스포츠", "애완", "육아", "서적", "의류", "취미",
            "문화", "기타"};
    ProgressDialog dialog = null;
    String upLoadServerUri = null;
    int serverResponseCode = 0;


    private final int GET_GALLERY_IMAGE = 200;
    //private ImageView imageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_regist);

        upLoadServerUri = "http://" + IP_ADDRESS + "/upload_p.php";//서버컴퓨터의 ip주소
        verifyStoragePermissions(ItemRegistActivity.this);



        imageview = (ImageView) findViewById(R.id.image_insert);
        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(intent, GET_GALLERY_IMAGE);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });


        //pic=(EditText) findViewById(R.id.image_insert);
        name = (EditText) findViewById(R.id.i_name);
        stprice = (EditText) findViewById(R.id.i_stprice);
        //price = (EditText) findViewById(R.id.i_price);
        type = (Spinner) findViewById(R.id.i_type);
        state = (RadioGroup) findViewById(R.id.i_state);
        intro = (EditText) findViewById(R.id.i_intro);
        registBtn = (Button) findViewById(R.id.registButton);
        mTextViewResult = (TextView) findViewById(R.id.mresult);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                iitype = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //  mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        state.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.i_state1:
                        sstate = "신품";
                        break;
                    case R.id.i_state2:
                        sstate = "상";
                        break;
                    case R.id.i_state3:
                        sstate = "중";
                        break;
                    case R.id.i_state4:
                        sstate = "하";
                        break;
                }
            }
        });


        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String ipic = imageview.getImageUri();
                String ipic = filename;
                String iname = name.getText().toString();
                String istprice = stprice.getText().toString();
//                String iprice = price.getText().toString();
                String itype = type.getSelectedItem().toString();
                String istate = sstate;
                String iintro = intro.getText().toString();
                String iid = "lee";

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert_i.php", ipic, iid, iname,  istprice, itype, istate, iintro);




                name.setText("");
                stprice.setText("");
                //price.setText("");
                intro.setText("");

                Log.d(TAG, "testdata - " + ipic + iid + iname  + istprice +  itype + istate + iintro);
                dialog = ProgressDialog.show(ItemRegistActivity.this, "", "Uploading file...", true);

                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                //messageText.setText("uploading started.....");
                            }
                        });

                        uploadFile(thePath);
                        Log.e("what - ", thePath);

                        //uploadFile(uploadFilePath + ""+uploadFileName);
                    }
                }).start();
            }

        });

    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri selectedImageUri = data.getData();
//            imageview.setImageURI(selectedImageUri);
//            //ItemListActivity.IMAGE_URI = selectedImageUri;
//        }
//    }
    public int uploadFile(String sourceFileUri) {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;

        int maxBufferSize = 1 * 1024 * 1024;

        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :" + thePath);

            runOnUiThread(new Runnable() {

                public void run() {

                    //messageText.setText("Source File not exist :" +thePath);

                }

            });


            return 0;
        } else {

            try {
                // open a URL connection to the Servlet

                FileInputStream fileInputStream = new FileInputStream(sourceFile);

                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL

                conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true); // Allow Inputs

                conn.setDoOutput(true); // Allow Outputs

                conn.setUseCaches(false); // Don't use a Cached Copy

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Connection", "Keep-Alive");

                conn.setRequestProperty("ENCTYPE", "multipart/form-data");

                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                conn.setRequestProperty("uploaded_file", filename);

                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + filename + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);

                buffer = new byte[bufferSize];

                // read file and write it into form...

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }
                // send multipart form data necesssary after file data...

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)

                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();


                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);


                if (serverResponseCode == 200) {

                    runOnUiThread(new Runnable() {

                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    + filename;
                            //messageText.setText(msg);



                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();
                runOnUiThread(new Runnable() {

                    public void run() {
                        // messageText.setText("MalformedURLException Exception : check script url.");

                        Toast.makeText(ItemRegistActivity.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();

                    }

                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);

            } catch (Exception e) {

                dialog.dismiss();

                e.printStackTrace();

                runOnUiThread(new Runnable() {

                    public void run() {

                        //messageText.setText("Got Exception : see logcat ");

                        Toast.makeText(ItemRegistActivity.this, "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();

                    }

                });

                Log.e("server Exception", "Exception : " + e.getMessage(), e);

            }

            dialog.dismiss();

            return serverResponseCode;
        } // End else block
    }


    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
       String errorString ;
               //= null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ItemRegistActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response - rrr " + result);
            Toast.makeText(ItemRegistActivity.this, mTextViewResult.getText(),
                    Toast.LENGTH_SHORT).show();

            mTextViewResult.setText(errorString);
            Log.d(TAG, "error message - " + errorString);
            Toast.makeText(ItemRegistActivity.this, mTextViewResult.getText(),
                    Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... params) {
            String ipic = (String) params[1];
            String iname = (String) params[2];
            String iid = (String) params[3];
            String istprice = (String) params[4];
            String itype = (String) params[5];
            String istate = (String) params[6];
            String iintro = (String) params[7];

            String serverURL = (String) params[0];
            String postparameters = "&ipic=" + ipic + "&iname=" + iname + "&iid" + iid + "&istprice=" + istprice
                    + "&itype=" + itype + "&istate" + istate + "&iintro" + iintro;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postparameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code-bbb" + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error", e);
                errorString = e.toString();

                return new String("Error:" + e.getMessage());
            }

        }
    }

    public String getPathFromUri(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToNext();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();

        return path;
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
            selImg = selectedImageUri;
            Log.d("uri", selImg.toString());
            thePath = getPathFromUri(selectedImageUri);
            System.out.println("thePath " + thePath);
            //ItemListActivity.IMAGE_URI = selectedImageUri;
            File f = new File(thePath);
            filename = f.getName();
        }
    }
}

