package com.example.administrator.a10_6_downloadservicebestpractice;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_ID_1 = 1;
    private Intent intent = null;
    private DownloadService.DownloadBinder binder = null;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder= (DownloadService.DownloadBinder)service;
            Log.d(TAG, "onServiceConnected: service connected...");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startDownload = (Button)findViewById(R.id.button_start_download);
        Button pauseDownload = (Button)findViewById(R.id.button_pause_download);
        Button cancelDownload = (Button)findViewById(R.id.button_cancel_download);

        startDownload.setOnClickListener(this);
        pauseDownload.setOnClickListener(this);
        cancelDownload.setOnClickListener(this);

        intent = new Intent(MainActivity.this, DownloadService.class);
        startService(intent);
        bindService(intent, conn, BIND_AUTO_CREATE);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ID_1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start_download:
                Log.d(TAG, "onClick: thread id = " + Thread.currentThread().getId());
                String url = "https://raw.githubusercontent.com/guolindev/eclipse/master" +
                        "/eclipse-inst-win64.exe";
                //String url = "http://p.gdown.baidu.com/09db84568b1c3dfc8a06cc1d4483158f2834f64b214a0bd712bbe15e8708de02d4107ec290958046277f8b19b7ede3921c4974a2d552f398c4bc779cb06bfba24eab46c939488eb29716d772029add12aeb4c08cddbeff99141423f5e83f70f199934141153057389b20e24dcb1d7952ce63e11e06a5e8489b6a1199ad5490481f93528333779fa9c58f29b5df2eeb110c4fc32710c7b782082cae64d99c26ed3fce03561978926a2e924d372fa418ec5f303224254593118f63134a8d02b96d402fda30e89331f4d4ef6f0712617863804faf0694d6066bda1c9b34f55d4795fd6c47c842143207";
                binder.startDownload(url);
                break;
            case R.id.button_pause_download:
                binder.pauseDownload();
                break;
            case R.id.button_cancel_download:
                binder.cancelDownload();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ID_1:
                if (grantResults.length > 0 &&
                        grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        stopService(intent);
    }
}
