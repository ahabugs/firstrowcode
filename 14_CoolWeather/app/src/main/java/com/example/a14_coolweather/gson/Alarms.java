package com.example.a14_coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 17-8-23.
 */

public class Alarms {
    public String level;
    public String state;
    public String title;
    @SerializedName("txt")
    public String context;
    public String type;
}
