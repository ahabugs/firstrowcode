package com.example.administrator.a9_2_1networktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView textView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button send_request = (Button)findViewById(R.id.button_send_request);
        Button response_request = (Button)findViewById(R.id.button_response_request);
        send_request.setOnClickListener(this);
        response_request.setOnClickListener(this);

        textView = (TextView)findViewById(R.id.response_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send_request:
                sendRequestWithHttpURLConnection();
                break;
            case R.id.button_response_request:
                break;
            default:
                break;
        }
    }

    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection conn = null;
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL("https://www.baidu.com");
                    conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(8000);

                    DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
                    outputStream.writeBytes("username=admin&password=123456");
                    showResponse("username=admin&password=123456");
                    Log.d(TAG, "run: " + "username=admin&password=123456");

                    conn.setRequestMethod("GET");
//                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);

                    InputStream in = conn.getInputStream();
                    InputStreamReader streamReader = new InputStreamReader(in);
                    // streamReader.read(); read a char
                    bufferedReader = new BufferedReader(streamReader);

                    StringBuilder responseBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    showResponse(responseBuilder.toString());
                    responseBuilder.delete(0, responseBuilder.length()-1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (conn != null) {
                        conn.disconnect();
                    }
                }

            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(response);
            }
        });
    }
}
