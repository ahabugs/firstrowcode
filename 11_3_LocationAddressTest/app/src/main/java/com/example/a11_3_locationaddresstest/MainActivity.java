package com.example.a11_3_locationaddresstest;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQ_CODE_PERMISSIONS = 1;
    private LocationClient mLocationClient;
    private MyLocationListener listener = null;
    private TextView textViewPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());
        listener = new MyLocationListener();
        mLocationClient.registerLocationListener(listener);
        setContentView(R.layout.activity_main);

        textViewPosition = (TextView)findViewById(R.id.text_view_position);
        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQ_CODE_PERMISSIONS);
        } else {
            // doRequestLocation
            Log.d(TAG, "onCreate: ");
            doRequestLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE_PERMISSIONS:
                if (grantResults.length > 0) {
                    for (int res: grantResults) {
                        if (res != PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "onRequestPermissionsResult: You denied the permissions");
                            Toast.makeText(this, "You denied the permissions",
                                    Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }

                    doRequestLocation();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: unknown errors");
                    Toast.makeText(this, "onRequestPermissionsResult: unknown errors",
                            Toast.LENGTH_LONG).show();
                    finish();
                }

                break;
            default:
                break;
        }
    }

    private void setLocationClientOption() {
        LocationClientOption option;

        option = new LocationClientOption();
        // 默认低功耗模式。getLocType返回结果网络模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
//        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");
        option.disableCache(true);
        option.setScanSpan(3000);
        mLocationClient.setLocOption(option);
    }

    private void doRequestLocation() {
        setLocationClientOption();
        mLocationClient.start();
    }


    //    public class MyLocationListener extends BDAbstractLocationListener {
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder builder = new StringBuilder();

            builder.append("维度(Latitude)：").append(bdLocation.getLatitude())
                    .append("\n");
            builder.append("经度(Longitude)：").append(bdLocation.getLongitude())
                    .append("\n");
            builder.append("国家：").append(bdLocation.getCountry())
                    .append("\n");
            builder.append("省：").append(bdLocation.getProvince())
                    .append("\n");
            builder.append("市：").append(bdLocation.getCity())
                    .append("\n");
            builder.append("区：").append(bdLocation.getDistrict())
                    .append("\n");
            builder.append("街道：").append(bdLocation.getStreet())
                    .append(bdLocation.getStreetNumber())
                    .append("\n");
            builder.append("定位方式：");

            switch (bdLocation.getLocType()) {
                case BDLocation.TypeGpsLocation:
                    builder.append("GPS");
                    break;
                case BDLocation.TypeNetWorkLocation:
                    builder.append("网络");
                    break;
                case BDLocation.TypeOffLineLocation:
                    builder.append("离线");
                    break;

                // 参考demo的LocationActivity
                case BDLocation.TypeServerError:
                    builder.append("服务端网络定位失败\n");
                    break;
                case BDLocation.TypeNetWorkException:
                    builder.setLength(0);
                    builder.append("网络不同导致定位失败\n");
                    break;
                case BDLocation.TypeCriteriaException:
                    builder.setLength(0);
                    builder.append("无法获取有效定位依据导致定位失败\n");
                    break;
                default:
                    builder.setLength(0);
                    builder.append("unknown type: " + bdLocation.getLocType());
                    break;
            }

            Log.d(TAG, "onReceiveLocation: " + builder);
            textViewPosition.setText(builder);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        mLocationClient.unRegisterLocationListener(listener);
        mLocationClient.stop();
        super.onDestroy();
    }
}