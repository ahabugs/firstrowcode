package com.example.a14_coolweather.gson;

/**
 * Created by Administrator on 17-8-23.
 */

public class Now {
    public COND cond;
    public String fl;
    public String hum;
    public String pcpn;
    public String pres;
    public String tmp;
    public String vis;
    public Wind wind;


    public class COND {
        public String code;
        public String txt;
    }

    public class Wind {
        public String deg;
        public String dir;
        public String sc;
        public String spd;
    }
}
