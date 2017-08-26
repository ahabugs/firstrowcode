package com.example.a13_5_alarm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 17-8-21.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
