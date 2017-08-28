package com.example.a14_coolweather;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePalApplication;
import org.litepal.LitePal;
/**
 * Created by Administrator on 17-8-22.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
//        LitePalApplication.initialize(context);
        LitePal.initialize(context);
        //CrashReport.initCrashReport(getApplicationContext(), "826f3c9519", true);
    }

    public static Context getContext() {
        return context;
    }
}