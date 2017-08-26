package com.example.administrator.a9_5_1_httpurlconnectiontest;

import android.os.Looper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 17-8-10.
 */

public class HttpUtil {
    public static void sendRequestWithHttpURLConn(final String address,
                                                  final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    // setDoOutput parameter: the default is true
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    // from reader to buffer
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line;
                    StringBuilder  builder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        builder.append(line);
                    }

                    if (listener != null) {
                        //prepaer()跟loop()不行。没继续深入研究是不是跟传入的view有关。
//                        Looper.prepare();
                        listener.onFinish(builder.toString());
//                        Looper.loop();
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
//                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}
