package com.example.administrator.a10_5_1_forgoundservicetest;

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
    private MyService.DownloadBinder downloadBinder;
    /*
    * 活动端写法模板
    * 1.新建服务链接实例
    * 2.重载服务链接的两个函数，在连接上的函数内执行服务的binder的成员函数
    * 3.bindService,unbindService
    * 4.服务(service)，服务链接(ServiceConnection)，活动(Activity)以及
    * 服务里的binder之间关系
    * 活动(Activity)通过链接(ServiceConnection)与服务(service)建立一个可控制服务的关系，
    * 这个可控制任务是由binder来负责的。binder就像是服务要干什么活以及怎样干活这样子的一个管家。
    * 我们可以在连接(ServiceConnection)成功的函数内执行管家要干的活*/
    /*
    * D/MyService: onCreate:
    * D/MyService: onBind:
    * D/MainActivity: onServiceConnected: (MyService)
    * D/MyService: startDownload:
    * D/MyService: getProgress:
    * */

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: (MyService)");
            Log.d(TAG, "onServiceConnected: (MyService) " + Thread.currentThread().getId());
            downloadBinder = (MyService.DownloadBinder)service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: (MyService)");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = (Button)findViewById(R.id.button_start);
        Button stop = (Button)findViewById(R.id.button_stop);
        Button bindService = (Button)findViewById(R.id.button_bind);
        Button unbindService = (Button)findViewById(R.id.button_unbind);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        Log.d(TAG, "onCreate: (MyService " + Thread.currentThread().getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                Intent startIntent = new Intent(MainActivity.this, MyService.class);
                startService(startIntent);
                break;
            case R.id.button_stop:
                Intent stopIntent = new Intent(MainActivity.this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.button_bind:
                Intent bindIntent = new Intent(MainActivity.this, MyService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.button_unbind:
                unbindService(connection);
                break;
            default:
                break;

        }
    }
}