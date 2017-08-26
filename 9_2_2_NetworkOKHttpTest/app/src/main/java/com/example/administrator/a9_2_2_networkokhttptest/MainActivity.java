package com.example.administrator.a9_2_2_networkokhttptest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private TextView textViewGet;
    private TextView textViewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button get_request =  (Button)findViewById(R.id.get_request);
        Button post_request =  (Button)findViewById(R.id.post_request);
        get_request.setOnClickListener(this);
        post_request.setOnClickListener(this);

        textViewGet = (TextView)findViewById(R.id.text_view_get);
        /*textViewGet.setBackgroundColor(Color.LTGRAY);*/
        textViewPost = (TextView)findViewById(R.id.text_view_post);
        /*textViewPost.setBackgroundColor(Color.BLUE);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_request:
                getRequestWithOkHttp();
                break;
            case R.id.post_request:
                postRequestWithOkHttp();
                break;
            default:
                break;
        }
    }

    private void getRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://raw.github.com/square/okhttp/master/README.md")
                            .build();
                    Response response = client.newCall(request).execute();
                    String stringData = response.body().string();
                    showString(stringData, textViewGet);
                    Log.d(TAG, "run: textViewGet " + stringData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void postRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String json = bowlingJson("Jesse", "Jake");
                OkHttpClient client = new OkHttpClient();
//                /*
//                RequestBody requestBody = new FormBody().Builder()
//                        .add("username", "admin")
//                        .add("password", "123456")
//                        .build();
//                */
                try {

                    // url NullPointerException
                    // response.body().string() IOException
                    RequestBody requestBody = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                            .url("http://www.roundsapp.com/post")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String stringData = response.body().string();
                    showString(stringData, textViewPost);
                    Log.d(TAG, "postRequestWithOkHttp: " + stringData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }

    private void showString(final String str, final TextView textView) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(str);
            }
        });
    }
}
