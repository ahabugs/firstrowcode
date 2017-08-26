package com.example.administrator.a9_5_1_httpurlconnectiontest;

/**
 * Created by Administrator on 17-8-10.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
