package com.example.a14_coolweather.gson;

/**
 * Created by Administrator on 17-8-23.
 */

public class AQI {
    public AQICity city;
    public class AQICity {
        public String aqi;
        public String co;
        public String no2;
        public String o3;
        public String pm10;
        public String pm25;
        public String qlty;
        public String so2;
    }

}
