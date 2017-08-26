package com.example.administrator.a9_5_1_httpurlconnectiontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private TextView textView;
    private StringBuilder myStringBuilder = new StringBuilder();

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
                HttpUtil.sendRequestWithHttpURLConn("http://www.baidu.com/",
                        new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        if (response != null && !"".equals(response)) {
                            // 不可以直接在子线程中显示UI
//                            textView.setText(response);
                            showResponse(response);
                            testOuterObj(response);
                            Log.d(TAG, "onFinish: " + response);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        // 不可以在子线程中显示UI
//                        Toast.makeText(MainActivity.this,
//                                "Connection Exception", Toast.LENGTH_LONG).show();
                        showResponse("onError: Exception");
                        Log.d(TAG, "onError: Exception");
                        e.printStackTrace();
                    }
                });
                break;
            case R.id.button_response_request:
                break;
            default:
                break;
        }
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(response);
            }
        });
    }

    /*
    * JAVA代码中匿名内部类如何改变外部的变量呢？
    *
    * 1.如果外部变量是外部类的本地(即局部)变量（如在方法中定义的变量），必须声明成final才能在内部类中使用，
    * 正常是不可以修改这个变量的，但如果变量是对象，可以修改对象内的属性
    * 2.如果外部变量是外部类的成员变量或类变量，内部类可以直接修改
    * */
    private void testOuterObj(String response) {
        myStringBuilder.append(response);
        Log.d(TAG, "testOuterObj: " + myStringBuilder.toString());
        myStringBuilder.setLength(0);
    }
}
