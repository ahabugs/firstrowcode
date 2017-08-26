package com.example.a14_coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 17-8-22.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /*public static void sendOkHttpDownloadRequest(String address, long length,
                                                 okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("RANGE", length + "-")
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }*/

    public static long getContentLength(String url) throws Exception {
        long length = 0;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            length = response.body().contentLength();

            // 调用过body()后，必须关闭
            response.body().close();
        }
        return length;
    }
}
