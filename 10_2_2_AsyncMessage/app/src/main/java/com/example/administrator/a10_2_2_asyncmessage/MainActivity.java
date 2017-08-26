package com.example.administrator.a10_2_2_asyncmessage;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private static final int UPDATE_TEXT = 1;
    private static final int RESET_TEXT = 2;
    /* 无static就成了成员变量。成员变量必须在类对象有了才可以调用。onCreate里表示创建活动，
    具体的活动对象是否已经创建我没研究过，所以不敢确定程序运行到onCreate里是否已经有活动对象了，
    因此，就把这两变量设置成了static。以后再研究活动对象在哪里创建了，以及这里能不能不用static。
    private final int UPDATE_TEXT = 1;
    private final int RESET_TEXT = 2;
    */
    private TextView textView;
    private MyHandler handler;

    /*
    *
    * 内存溢出bug
    * This Handler class should be static or leaks might occur
    * 参考http://www.cnblogs.com/zoejiaen/p/4580572.html
    * http://blog.csdn.net/moon_nife/article/details/54975983
    *
    // anonymous subclass extends Handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_TEXT:
                    textView.setText("Nice to meet you!");
                    Log.d(TAG, "handleMessage: Nice to meet you!");
                    break;
                case RESET_TEXT:
                    textView.setText("Hello world!");
                    Log.d(TAG, "handleMessage: Hello world!");
                    break;
                default:
                    break;
            }
        }
    };
    */
    private static class MyHandler extends Handler {
        private MainActivity inner_activity;

        MyHandler(WeakReference<MainActivity> activity) {
            inner_activity = activity.get();
        }

        @Override
        public void handleMessage(Message msg) {
            // super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_TEXT:
                    inner_activity.getTextView().setText(String.valueOf("Nice to meet you!"));
                    Log.d(TAG, "handleMessage: Nice to meet you!");
                    break;
                case RESET_TEXT:
                    inner_activity.getTextView().setText(String.valueOf("Hello world!"));
                    Log.d(TAG, "handleMessage: Hello world!");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button change = (Button)findViewById(R.id.button_change_text);
        Button reset = (Button)findViewById(R.id.button_reset_text);
        change.setOnClickListener(this);
        reset.setOnClickListener(this);
        textView = (TextView)findViewById(R.id.text_view);

        WeakReference<MainActivity> mActivity = new WeakReference<>(MainActivity.this);
        handler = new MyHandler(mActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_change_text:
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = UPDATE_TEXT;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
                break;
            case R.id.button_reset_text:
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = RESET_TEXT;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
                break;
            default:
                break;
        }
    }

    public TextView getTextView() {
        return textView;
    }
}
