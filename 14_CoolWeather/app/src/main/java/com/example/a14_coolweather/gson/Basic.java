package com.example.a14_coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 17-8-23.
 */

public class Basic {
    public String city;
    public String cnty;
    @SerializedName("id")
    public String weatherId;

    @SerializedName("lat")
    public String latitude;
    @SerializedName("lon")
    public String longitude;
//    public String prov;
    public Update update;
    public class Update {
        @SerializedName("loc")
        public String updateTime;
        @SerializedName("utc")
        public String updateTimeInUTC;
    }
}
