package com.example.administrator.a10_3_3_downloadbinderservicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private DownloadBinder downloadBinder = new DownloadBinder();
    /*
    * service端写法模板
    * 1.新建binder实例
    * 2.在onBinder里返回之前新建的binder实例
    * 3.编写binder的成员函数，表示这个服务要怎样去干活
    * */
    public MyService() {
    }

    class DownloadBinder extends Binder {
        public DownloadBinder() {
            super();
        }

        public void startDownload() {
            Log.d(TAG, "startDownload: ");
        }

        public int getProgress() {
            Log.d(TAG, "getProgress: ");
            return 0;
        }
    }

    /*
    * 返回clients可调用的binder给服务*/
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "onBind: ");
        return downloadBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }
/*
    @Override
    public int onStartCommand(Intent intent, @IntDef(value = {Service.START_FLAG_REDELIVERY,
            Service.START_FLAG_RETRY}, flag = true) int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
