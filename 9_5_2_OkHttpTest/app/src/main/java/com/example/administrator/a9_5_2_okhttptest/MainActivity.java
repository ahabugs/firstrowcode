package com.example.administrator.a9_5_2_okhttptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
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
                HttpUtil.sendRequestWithOkHttp("https://raw.github.com/square/okhttp/master" +
                        "/README.md", new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.toString());
                        showData("onFailure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Log.d(TAG, "onResponse: " + string);
                        showData(string);
                    }
                });
                break;
            case R.id.post_request:
                break;
            default:
                break;
        }
    }

    private void showData(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewGet.setText(data);
            }
        });
    }
}
