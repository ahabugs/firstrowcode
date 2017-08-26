package com.example.administrator.a9_4_2_parsejsonwithgsontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView textViewGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button get_request =  (Button)findViewById(R.id.get_request);
        //Button post_request =  (Button)findViewById(R.id.post_request);
        get_request.setOnClickListener(this);
        //post_request.setOnClickListener(this);

        textViewGet = (TextView)findViewById(R.id.text_view_get);
        //textViewPost = (TextView)findViewById(R.id.text_view_post);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_request:
                getRequestWithOkHttp();
                break;
            case R.id.post_request:
                postRequestWithOkHttp();
                break;
            default:
                break;
        }
    }



    private void getRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.1.101/get_data.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    parseJSONWithGSON(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void postRequestWithOkHttp() {

    }

    private void parseJSONWithGSON(String data) {
        Gson gson = new Gson();
        StringBuilder builder = new StringBuilder();
        List<App> apps = gson.fromJson(data, new TypeToken<List<App>>(){}.getType());
        for (App app : apps) {
            Log.d(TAG, "parseJSONWithGSON: id is " + app.getId());
            Log.d(TAG, "parseJSONWithGSON: name is " + app.getName());
            Log.d(TAG, "parseJSONWithGSON: version is " + app.getVersion());

            builder.append("id = ");
            builder.append(app.getId());
            builder.append("name = ");
            builder.append(app.getName());
            builder.append("version = ");
            builder.append(app.getVersion());
        }
        showString(builder.toString(), textViewGet);
        builder.setLength(0);
    }

    private void showString(final String str, final TextView textView) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(str);
            }
        });
    }

    private class App {
        private String id;
        private String name;
        private String version;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
