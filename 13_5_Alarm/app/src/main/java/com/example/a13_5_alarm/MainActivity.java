package com.example.a13_5_alarm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_bind_ser:
                Intent intent = new Intent(this, AlarmLongRunningService.class);
                bindService(intent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.button_unbind_ser:
                unbindService(connection);
                break;
            case R.id.button_start_ser:
                Intent intent1 = new Intent(this, AlarmLongRunningService.class);
                startService(intent1);
                break;
            case R.id.button_stop_ser:
                Intent intent2 = new Intent(this, AlarmLongRunningService.class);
                stopService(intent2);
                break;
            case R.id.button_unbind_stop_ser:
                Intent intent3 = new Intent(this, AlarmLongRunningService.class);
                stopService(intent3);
                unbindService(connection);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        Button buttonBind = (Button)findViewById(R.id.button_bind_ser);
        Button buttonUnbind = (Button)findViewById(R.id.button_unbind_ser);
        Button buttonStart = (Button)findViewById(R.id.button_start_ser);
        Button buttonStop = (Button)findViewById(R.id.button_stop_ser);
        Button buttonStopUnbind = (Button)findViewById(R.id.button_unbind_stop_ser);
        buttonBind.setOnClickListener(this);
        buttonUnbind.setOnClickListener(this);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonStopUnbind.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
