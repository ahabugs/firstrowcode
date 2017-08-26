package com.example.a14_coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 17-8-22.
 */

public class Province extends DataSupport {
    // 数据库Id
    private int id;
    private String name;
    // 省份编号
    private int code;

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
