package com.example.a11_4_locationmap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQ_CODE_PERMISSIONS = 1;
    private LocationClient mLocationClient;
    private MyLocationListener listener = null;
    private MapView mapView = null;
    private BaiduMap baiduMap = null;
    private TextView textView;
    private boolean isFirstLocated = true;
    private MyLocationData.Builder locationBuilder = null;
    private MyLocationData locationData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mLocationClient = new LocationClient(getApplicationContext());
        listener = new MyLocationListener();
        mLocationClient.registerLocationListener(listener);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.text_view_loc);
        mapView = (MapView)findViewById(R.id.map_view_bd);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        locationBuilder = new MyLocationData.Builder();

        List<String> permissionList = new ArrayList<>();
        requestAllPermissions(permissionList);
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQ_CODE_PERMISSIONS);
        } else {
            doRequestLocation();
        }

        Log.d(TAG, "onCreate: lifetime");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: lifetime");
        super.onStart();
    }

    // 销毁顺序参考Demo中的IndoorLocationActivity
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: lifetime");
        // 恢复地图视图
        mapView.onResume();
        super.onResume();
    }


    // 销毁顺序参考Demo中的IndoorLocationActivity
    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        // 地图部分不可见
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: lifetime");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: lifetime");
        super.onRestart();
    }

    // 销毁顺序参考Demo中的IndoorLocationActivity
    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        // 取消监听
        mLocationClient.unRegisterLocationListener(listener);
        // 关闭定位SDK
        mLocationClient.stop();
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        // 关闭地图视图
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
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

    private void requestAllPermissions(List<String> permissionList) {

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
    }

    private void doRequestLocation() {
        setLocationOpt();
        mLocationClient.start();
    }

    private void setLocationOpt() {
        LocationClientOption option = new LocationClientOption();
        option.disableCache(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(3000);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setNeedDeviceDirect(true);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mapView == null) {
                Log.d(TAG, "onReceiveLocation: bdLocation or mapView is null ");
                return;
            }
            showLocationInfo(bdLocation);
            navigation(bdLocation);
        }
    }

    private void showLocationInfo(BDLocation bdLocation) {
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
        textView.setText(builder);
    }

    /*
    *
    * 如果将设置中心跟修改放大两步分开操作，将会导致坐标是正确的，地图还是显示天安门。
    * 因此写法错误。
    * LatLng ll = new LatLng(lac.getLatitude(), lac.getLongitude());
    * MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
    * baiduMap.animateMapStatus(update, 500);
    * update = MapStatusUpdateFactory.zoomTo(16f);
    * baiduMap.animateMapStatus(update, 500);
    * isFirstLocate = false;
    * */
    private void navigation(BDLocation bdLocation) {
        if (isFirstLocated) {
            // 此写法可以用
            /*
            LatLng ll = new LatLng(lac.getLatitude(), lac.getLongitude());
            MapStatus newMapStatus = new MapStatus.Builder().target(ll).zoom(16f).build();
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(newMapStatus);
            baiduMap.animateMapStatus(mapStatusUpdate, 350);
            isFirstLocate = false;
            */
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(ll, 16f);
            baiduMap.animateMapStatus(update);
            isFirstLocated = false;
        }

        /*
        * 手机状态栏不能显示定位的小图标，以及地图上当前位置的定位图标不动，或者跟着onReceiveLocation()时，
        * 发现onReceiveLocation()只调用一次，然后有setForFirst()等调用，之后程序就好像
        * 执行了mLocationClient.stop()或跑飞的现象等
        * 可能原因：
        * 1. AndroidManifest.xml的<service>服务设置到<application之外导致的>，
        * 2. 还有一种情况就是向百度申请的APIKEY跟应用不匹配，这种情况将真机联上Android Studio，运行APP，
        * 查看Android Monitor，启动阶段就会有错误提示。
        * */
        switch (bdLocation.getLocType()) {
            case BDLocation.TypeGpsLocation:
            case BDLocation.TypeNetWorkLocation:
            case BDLocation.TypeOffLineLocation:
                // 参考Demo中的IndoorLocationActivity
                locationData = locationBuilder.accuracy(bdLocation.getRadius())
                        .direction(100)
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .build();
                baiduMap.setMyLocationData(locationData);
                break;

            // 参考demo的LocationActivity
            case BDLocation.TypeServerError:
            case BDLocation.TypeNetWorkException:
            case BDLocation.TypeCriteriaException:
            default:
                break;
        }
    }
}
