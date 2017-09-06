package com.example.a14_coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.a14_coolweather.gson.Weather;
import com.example.a14_coolweather.util.HttpUtil;
import com.example.a14_coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    private static final String TAG = "AutoUpdateService";
    private Intent it;
    // private MBinder binder = new MBinder();

    public AutoUpdateService() {
    }

    /*public class MBinder extends Binder {
        public void startUpdate() {
            Log.d(TAG, "startUpdate: current thread " + Thread.currentThread().getId());
        }
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();*/

        updateWeatherInfo();
        updatePicBackground();

        // 管理器 + 触发时间 + PendingIntent + 取消PI + 重新设置PI-->定时时间到-->onStartCommand()
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int timeout = 8*60 * 60 * 1000;
        long trigger = SystemClock.elapsedRealtime() + timeout;
        it = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, it, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, trigger, pi);

        String prompts = "time: " + SystemClock.elapsedRealtime() + "thread id"
                + Thread.currentThread().getId();
        Log.d(TAG, "onStartCommand: " + SystemClock.elapsedRealtime());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onDestroy() {
        stopService(it);
        super.onDestroy();
    }

    /**
     * 这个服务性质：活动结束后，服务不退出。因此启动服务的活动(WeatherActivity)
     * 的onDestroy()中不调用stopService()。因为WeatherActivity会退出，因此这里使用
     * SharedPreferences下的weatherId
     * 另外，“weather”和“weather_id”都是以WeatherActivity的Context来保存的。
     */
    private void updateWeatherInfo() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherId = prefs.getString("weather_id", null);
        if (weatherId != null) {
            String address = "https://free-api.heweather.com/v5/weather?city="
                    + weatherId + "&key=" + "6e2537d9553f478393500fdab60245b7";
            HttpUtil.sendOkHttpRequest(address, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String weatherData = response.body().string();
                    response.body().close();
                    Weather weather = Utility.handleWeatherResp(weatherData);
                    if (weather != null && "ok".equals(weather.status)) {

                        // 保存查询结果, key = weather
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather", weatherData);
                        // 默认为北京天气Id
                        String weatherIdDef = "CN101010100";
                        if (weather.basic.weatherId != null) {
                            weatherIdDef = weather.basic.weatherId;
                        }
                        editor.putString("weather_id", weatherIdDef);
                        editor.apply();
                    }
                }
            });
        }
    }

    private void updatePicBackground() {
        String string = "http://guolin.tech/api/bing_pic";
        Utility.downloadFileInBackground(AutoUpdateService.this,
                new Utility.DownloadCallBack() {
                    @Override
                    public void onResponse(String fileLocalUrl) {}
                }, string);
    }
}
