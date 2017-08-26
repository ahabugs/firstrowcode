package com.example.a14_coolweather.gson;

/**
 * Created by Administrator on 17-8-23.
 */

public class HourlyForecast {
    public COND cond;
    public String date;
    public String hum;
    public String pcpn;
    public String pop;
    public String pres;
    public String tmp;
    public Wind wind;

    public class COND {
        String code;
        String txt;
    }
    public class Wind {
        public String deg;
        public String dir;
        public String sc;
        public String spd;
    }
}
