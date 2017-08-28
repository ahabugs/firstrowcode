package com.example.a14_coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a14_coolweather.gson.Forecast;
import com.example.a14_coolweather.gson.Weather;
import com.example.a14_coolweather.service.AutoUpdateService;
import com.example.a14_coolweather.util.HttpUtil;
import com.example.a14_coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";
    private ScrollView scrollViewWeatherLayout;
    private TextView titleCity;
    private TextView updateTime;
    private TextView currentTemperature;
    private TextView currentWeatherOverview;
    private TextView currentAirQuality; // 优，良

    // 横向滑动每小时天气，标题显示星期。功能待补充
    private TextView currentDate;
    private RecyclerView hourlyWeatherOverview;

    private LinearLayout dailyForecastLayout;

    private TextView airQualityVal;
    private TextView pm25TextView;
    private TextView suggestionComfort;
    private TextView suggestionWC;
    private TextView suggestionSport;

    private DrawerLayout drawerLayoutWeather;
    private Button button_drawer_nav;

    private ImageView pic_background;

    private SwipeRefreshLayout swipeRefreshLayout;
    private String swipeRefreshWeatherId;

    private Intent intentAutoUpdateSer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*设置背景与状态栏融合*/

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_weather);

        scrollViewWeatherLayout = (ScrollView)findViewById(R.id.scroll_view_weather_layout);
        titleCity = (TextView)findViewById(R.id.text_view_city_title);
        updateTime = (TextView)findViewById(R.id.text_view_update_time);
        currentTemperature = (TextView)findViewById(R.id.text_view_degree);
        currentWeatherOverview = (TextView)findViewById(R.id.text_view_now_txt);
        currentAirQuality = (TextView)findViewById(R.id.text_view_aqi_qlty);


        /*
         * 功能待补充*/
        currentDate = (TextView)findViewById(R.id.text_view_current_date);
        hourlyWeatherOverview = (RecyclerView)findViewById(R.id.recycler_view_hourly);
        currentDate.setVisibility(View.GONE);
        hourlyWeatherOverview.setVisibility(View.GONE);

        // 后边会填充子项
        dailyForecastLayout = (LinearLayout)findViewById(R.id.linear_layout_forecast);
        airQualityVal = (TextView)findViewById(R.id.text_view_aqi_aqi);
        pm25TextView = (TextView)findViewById(R.id.text_view_aqi_pm25);

        suggestionComfort = (TextView)findViewById(R.id.text_view_comfort);
        suggestionWC = (TextView)findViewById(R.id.text_view_car_wash);
        suggestionSport = (TextView)findViewById(R.id.text_view_sport);

        drawerLayoutWeather = (DrawerLayout)findViewById(R.id.drawer_layout_weather_activity);
        button_drawer_nav = (Button)findViewById(R.id.button_drawer_nav);
        button_drawer_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutWeather.openDrawer(GravityCompat.START);
            }
        });

        setWeatherInfo();
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_home_page);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeatherData();
                setPicBackground();
            }
        });

        pic_background = (ImageView)findViewById(R.id.image_view_background);
        setPicBackground();
        // 启动服务
        intentAutoUpdateSer = new Intent(this, AutoUpdateService.class);
        startService(intentAutoUpdateSer);
    }

    public void requestWeatherData() {
        final String weatherId = this.swipeRefreshWeatherId;
        if (weatherId == null) {
            return;
        }
        String address = "https://free-api.heweather.com/v5/weather?city="
                + weatherId + "&key=" + "6e2537d9553f478393500fdab60245b7";

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败",
                                Toast.LENGTH_LONG).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // String tmpString = response.body().string();
                // final String weatherData = tmpString;
                final String weatherData = response.body().string();
                response.body().close();
                final Weather weather = Utility.handleWeatherResp(weatherData);
                // Weather tmpWeather = Utility.handleWeatherResp(tmpString);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {

                            // 保存查询结果, key = weather
                            SharedPreferences.Editor editor = PreferenceManager
                                    .getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", weatherData);
                            editor.putString("weather_id", weatherId);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "处理天气信息失败",
                                    Toast.LENGTH_LONG).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

        });
    }

    /*private void showWeatherDefaultInfo() {

    }*/

    private void showWeatherInfo(Weather weather) {

        try {
            titleCity.setText(weather.basic.city);
            String date = weather.basic.update.updateTime;
            updateTime.setText(date.split(" ")[1]);

            String temperature = weather.now.tmp + "℃";
            currentTemperature.setText(temperature);
            currentWeatherOverview.setText(weather.now.cond.txt);

            // 小城镇，和风有的情况下没有质量参数返回
            if (weather.aqi != null) {
                currentAirQuality.setText(weather.aqi.city.qlty);
            }


            dailyForecastLayout.removeAllViews();
            for (Forecast forecast : weather.dailyForecastList) {
                for (int i = 0; i < 3; i++) {
                    View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.forecast_item,
                            dailyForecastLayout, false);

                    /*
                     * 注意这里要使用view.findViewById()的调用模式。虽然WeatherActivity引用了
                     * forecast.xml布局，但这个布局内只有一个LinearLayout，并没有添加
                     * text_view_date，text_view_info_txt，text_view_tmp_min，text_view_tmp_max
                     * 这四个控件，这四个控件只在forecast_item.xml布局里才定义了，所以要通过
                     * forecast_item这个布局才可以引用这四个控件。
                     *
                     * 不加view去调用findViewById()会导致返回的控件为null
                     * */
                    TextView dateText = (TextView)view.findViewById(R.id.text_view_date);
                    dateText.setPadding(0, 10, 0, 0);
                    dateText.setText(forecast.date);
                    TextView wtOverview = (TextView)view.findViewById(R.id.text_view_info_txt);
                    wtOverview.setPadding(0, 10, 0, 0);
                    wtOverview.setText(forecast.cond.txt_d); // 白天, n表示夜晚
                    TextView minTmp = (TextView)view.findViewById(R.id.text_view_tmp_min);
                    minTmp.setPadding(0, 10, 0, 0);
                    minTmp.setText(forecast.temperature.min);
                    TextView maxTmp = (TextView)view.findViewById(R.id.text_view_tmp_max);
                    minTmp.setPadding(0, 10, 0, 0);
                    maxTmp.setText(forecast.temperature.max);
                    dailyForecastLayout.addView(view);
                }

            }
            if (weather.aqi != null) {
                airQualityVal.setText(weather.aqi.city.aqi);
                pm25TextView.setText(weather.aqi.city.pm25);
            }

            String comfort = "舒适度: " + weather.suggestion.comf.brf; // 较舒适
            String carWash = "洗车指数: " + weather.suggestion.cw.brf; // 较不适宜
            String sport = "运动: " + weather.suggestion.sport.brf;
            suggestionComfort.setText(comfort);
            suggestionWC.setText(carWash);
            suggestionSport.setText(sport);

            // 显示整个滚动布局内容
            scrollViewWeatherLayout.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setPicBackground() {
 /*       SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String pic_dir = pref.getString("pic_background", null);
        if (pic_dir != null) {
            File file = new File(pic_dir);
            if (file.exists()) {
                Glide.with(this).load(pic_dir).into(pic_background);
            } else {
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("pic_background");
                editor.apply();
            }
        } else {
            String string = "http://guolin.tech/api/bing_pic";
            Utility.downloadFileInBackground(WeatherActivity.this,
                    new Utility.DownloadCallBack() {

                        @Override
                        public void onResponse(String fileLocalUrl) {
                            final String url = fileLocalUrl;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(WeatherActivity.this).load(url).into(pic_background);
                                }
                            });
                        }
                    }, string);
        }*/


        String string = "http://guolin.tech/api/bing_pic";
        Utility.downloadFileInBackground(WeatherActivity.this,
                new Utility.DownloadCallBack() {

                    @Override
                    public void onResponse(String fileLocalUrl) {
                        final String url = fileLocalUrl;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(WeatherActivity.this).load(url).into(pic_background);
                            }
                        });
                    }
                }, string);

    }

    private void setWeatherInfo() {
        /*final String weatherId;*/
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherData = pref.getString("weather", null);
        if (weatherData != null) {
            Weather weather = Utility.handleWeatherResp(weatherData);
//            if (weather != null) {
//                showWeatherInfo(weather);
//            } else {
//                SharedPreferences.Editor editor = pref.edit();
//                editor.remove("weather");
//                editor.apply();
//            }
            // 能运行到这个活动，说明保存的weather信息一定是成功解析过的，并且
            // 一定有城市weatherId，所以以上的判断就不要了
            showWeatherInfo(weather);
            swipeRefreshWeatherId = pref.getString("weather_id", null);

        } else {
            // 经验，无数据时候不要显示ScrollView，否则界面显示效果不好。
            scrollViewWeatherLayout.setVisibility(View.INVISIBLE);
            swipeRefreshWeatherId = getIntent().getStringExtra("weather_id");
            requestWeatherData();
        }
    }

    public void setSwipeRefreshWeatherId(String swipeRefreshWeatherId) {
        this.swipeRefreshWeatherId = swipeRefreshWeatherId;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public DrawerLayout getDrawerLayoutWeather() {
        return drawerLayoutWeather;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 活动推出后不想让服务结束，因此不调用stopService()
        if (intentAutoUpdateSer != null) {
            stopService(intentAutoUpdateSer);
        }
    }
}
