package com.example.a13_5_alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class AlarmLongRunningService extends Service {

    private static final String TAG = "AlarmLongRunningService";
    private MyBinder myBinder =  new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return myBinder;
    }

    public class MyBinder extends Binder {
        public void showCurrentTime() {
            Log.d(TAG, "MainActivity showCurrentTime: \n" + "currentMillis= "
                    + System.currentTimeMillis() + "\nelapsedRealTime="
                    + SystemClock.elapsedRealtime());
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //
            }
        }).start();

        int seconds = 10 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + seconds;
        Intent myIntent = new Intent(this, AlarmLongRunningService.class);
        PendingIntent pend = PendingIntent.getService(this, 0, myIntent, 0);
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        manager.set(AlarmManager.ELAPSED_REALTIME, triggerAtTime, pend);
        myBinder.showCurrentTime();
        return super.onStartCommand(intent, flags, startId);
    }
}
