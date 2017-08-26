package com.example.a14_coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 17-8-22.
 */

public class Town extends DataSupport {
    private int id;
    private String name;
    private int code;
    private String weatherId;
    private int cityCode;

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {

        this.code = code;
    }
    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }


    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }


    public String getWeatherId() {
        return weatherId;
    }

    public int getCityCode() {
        return cityCode;
    }

}
