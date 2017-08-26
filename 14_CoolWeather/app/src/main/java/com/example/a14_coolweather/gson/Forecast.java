package com.example.a14_coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 17-8-23.
 */

public class Forecast {
    public ASTRO astro;
    public COND cond;
    public String date;
    public String hum;
    public String pcpn;
    public String pop;
    public String pres;


    @SerializedName("tmp")
    public Temperature temperature;
    public String uv;
    public String vis;
    public Wind wind;

    public class ASTRO {
        public String mr;
        public String ms;
        public String sr;
        public String ss;

    }

    public class COND {
        public String code_d;
        public String code_n;
        public String txt_d;
        public String txt_n;
    }

    public class Temperature {
        public String max;
        public String min;
    }

    public class Wind {
        public String deg;
        public String dir;
        public String sc;
        public String spd;
    }
}
