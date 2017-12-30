package com.example.hstransition;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DebugMainActivity";
    private static StringBuffer mStrBuffer = new StringBuffer();
    private TextView mTextView;
    private Button mBtn_clear_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn_clear_log = (Button)findViewById(R.id.btn_clear_log);
        mTextView = (TextView)findViewById(R.id.tv_text);
        Log.d(TAG, "onCreate: ");
        mStrBuffer.append("onCreate\n");
        mTextView.setText(mStrBuffer.toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
        mStrBuffer.append("onRestoreInstanceState\n");
        mTextView.setText(mStrBuffer.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        mStrBuffer.append("onStart\n");
        mTextView.setText(mStrBuffer.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        mStrBuffer.append("onResume\n");
        mTextView.setText(mStrBuffer.toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        mStrBuffer.append("onSaveInstanceState\n");
        mTextView.setText(mStrBuffer.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        mStrBuffer.append("onPause\n");
        mTextView.setText(mStrBuffer.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        mStrBuffer.append("onStop\n");
        mTextView.setText(mStrBuffer.toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
        mStrBuffer.append("onRestart\n");
        mTextView.setText(mStrBuffer.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        mStrBuffer.append("onDestroy\n");
        mTextView.setText(mStrBuffer.toString());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
        mStrBuffer.append("onConfigurationChanged\n");
        mTextView.setText(mStrBuffer.toString());
    }

    public void onClick(View v) {
        mStrBuffer.setLength(0);
        mTextView.setText("");
    }
}
