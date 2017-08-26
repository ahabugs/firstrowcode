package com.example.a14_coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 17-8-23.
 */

public class Weather {
//    @SerializedName("alarms")
//    public List<Alarms> alarmsList;
    public AQI aqi;
    public Basic basic;

    @SerializedName("daily_forecast")
    public List<Forecast> dailyForecastList;

    @SerializedName("hourly_forecast")
    public List<HourlyForecast> hourlyForecastList;

    public Now now;
    public String status;
    public Suggestion suggestion;
}
