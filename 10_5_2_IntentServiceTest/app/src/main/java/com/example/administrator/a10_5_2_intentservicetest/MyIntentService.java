package com.example.administrator.a10_5_2_intentservicetest;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by Administrator on 17-8-13.
 */

public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: " + Thread.currentThread().getId());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: " +  Thread.currentThread().getId());
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: " + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: "+ Thread.currentThread().getId());
        super.onDestroy();
    }
}
