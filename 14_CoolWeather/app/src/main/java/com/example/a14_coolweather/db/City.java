package com.example.a14_coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 17-8-22.
 */

public class City extends DataSupport {

    private int id;
    private String name;
    private int code;
    // 省份的数据库id
    private int provinceCode;

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public int getProvinceCode() {
        return provinceCode;
    }
}
