package com.example.administrator.a9_5_2_okhttptest;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 17-8-10.
 */

public class HttpUtil {
    public static void sendRequestWithOkHttp(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
