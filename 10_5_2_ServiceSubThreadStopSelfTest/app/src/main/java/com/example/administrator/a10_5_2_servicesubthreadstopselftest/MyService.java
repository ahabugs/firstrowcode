package com.example.administrator.a10_5_2_servicesubthreadstopselftest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
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
            Log.d(TAG, "startDownload: " + Thread.currentThread().getId());
        }

        public int getProgress() {
            Log.d(TAG, "getProgress: ");
            Log.d(TAG, "getProgress: " + Thread.currentThread().getId());
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
        Log.d(TAG, "onBind: " + Thread.currentThread().getId());
        return downloadBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        Log.d(TAG, "onCreate: " + Thread.currentThread().getId());
        Log.d(TAG, "onCreate: this=" + this.getClass());
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("this is title foreground")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: " + Thread.currentThread().getId());
                stopSelf();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}