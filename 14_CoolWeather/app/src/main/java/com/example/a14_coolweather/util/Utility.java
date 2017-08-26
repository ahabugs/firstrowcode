package com.example.a14_coolweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.a14_coolweather.db.City;
import com.example.a14_coolweather.db.Province;
import com.example.a14_coolweather.db.Town;
import com.example.a14_coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 17-8-22.
 */

public class Utility {

    public interface DownloadCallBack {
        void onResponse(String fileLocalUrl);
    }

    public static boolean handleProvinceResp(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {

                JSONArray allProvince = new JSONArray(response);
                for (int i = 0; i < allProvince.length(); i++) {
                    JSONObject object = allProvince.getJSONObject(i);
                    Province province = new Province();
                    province.setName(object.getString("name"));
                    province.setCode(object.getInt("id"));
                    province.save();
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean handleCityResp(String response, int provinceCode) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject object = allCities.getJSONObject(i);
                    City city = new City();
                    city.setName(object.getString("name"));
                    city.setCode(object.getInt("id"));
                    city.setProvinceCode(provinceCode);
                    city.save();
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean handleTownResp(String response, int cityCode) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allTowns = new JSONArray(response);
                for (int i = 0; i < allTowns.length(); i++) {
                    JSONObject object = allTowns.getJSONObject(i);
                    Town town = new Town();
                    town.setName(object.getString("name"));
                    town.setCode(object.getInt("id"));
                    town.setWeatherId(object.getString("weather_id"));
                    town.setCityCode(cityCode);
                    town.save();
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static Weather handleWeatherResp(String response) {
        try {
            JSONObject object = new JSONObject(response);
            /*
             * object.getString("name")，这里getString就相当于下边的getJSONArray()
             * 天气返回一个对象。
             * 对象与数组区别：对象格式为“"名称":值”，数组格式为“[元素，元素...]”
             * 天气返回了一个对象，对象名是“HeWeather，对象值是一个数组，这才有下边的
             * object.getJSONArray("HeWeather")去读取HeWeather的值。而所有值仅仅是
             * 数组中的第一个元素，这才有了jsonArray.getJSONObject(0)，获取第一个元素
             * */
            JSONArray array = object.getJSONArray("HeWeather5");
            String data = array.getJSONObject(0).toString();

            // 返回Weather对象
            return new Gson().fromJson(data, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void downloadFileInBackground(final Context context, final DownloadCallBack callBack,
                                                String... params) {
        final String urlGuoLin = params[0];
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url;
                Response response = null;
                {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(urlGuoLin)
                                .build();
                        response = client.newCall(request).execute();
                        if (response != null && response.isSuccessful()) {
                            url = response.body().string();
                            if ("".equals(url)) {
                                return;
                            }
                        } else {
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    } finally {
                        if (response != null) {
                            response.body().close();
                            response = null;
                        }
                    }
                }

                File file = null;
                String directory;
                // String fileName;
                // String tmp;
                // String fileSuffix;
                String fullPath;
                long downloadedLength = 0;
                long contentLength;

                InputStream inputStream = null;
                RandomAccessFile saveFile = null;

                // 处理真实的图片链接。这个链接的“/”后是图片文件名
                /*int index = url.lastIndexOf("/");*/
                /*fileName = url.substring(url.lastIndexOf("/"), index + 20);*/
                /*
                * 在文件名中多加了20位，让Download目录下背景图片文件名形成差异，尽量避免由文件同名
                * 导致背景图片不能及时更新的情况发生。 */
                /*int index = url.lastIndexOf("/") + 1;
                tmp = url.substring(index, index + 5);
                fileSuffix = url.substring(url.lastIndexOf("."));*/

                int index = url.lastIndexOf("/") + 1;
                // fileName = "pic_background" + fileSuffix;
                directory = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS).getPath();
                fullPath = directory + "/pic_background_" + url.substring(index);

                /*
                 * 创建目录跟创建文件不一样
                 * fullPath = directory + "/tmp/" + "pic_background_" + tmp + fileSuffix;
                 * File document = new File(directory + "/tmp")
                 * if (!document.exists()) {
                 *     document.mkdirs(); // mkdirs可创建多级目录，mkdir只能创建一级目录
                 * }
                 * file = new File(fullPath);
                 * if (file.exists()) {
                 *     downloadedLength = file.length();
                 * } else {
                 *    try {
                 *       file.createNewFile();
                 *    } catch (Exception e) {
                 *       e.printStackTrace();
                 *    }
                 *
                 * }
                 * */
                // fullPath = directory + "/pic_background_" + tmp + fileSuffix;

                try {

                    file = new File(fullPath);
                    if (file.exists()) {
                        downloadedLength = file.length();
                    }

                    contentLength = HttpUtil.getContentLength(url);
                    if (contentLength <= 0) {
                        return;
                    }

                    // 支持断点
                    if (contentLength == downloadedLength) {
                        callBack.onResponse(fullPath);
                        /*
                        * 程序卸载后，之前下载的完整文件未删除。但新程序的SharedPreferences里没有保存
                        * 这张图片的路径*/
                        SharedPreferences pref = PreferenceManager.
                                getDefaultSharedPreferences(context);
                        String pic_dir = pref.getString("pic_background", null);
                        if (pic_dir == null || !pic_dir.equals(fullPath)) {
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("pic_background", fullPath);
                            editor.apply();
                        }
                        return;
                    } else if (downloadedLength > contentLength) {
                        file.delete();
                        downloadedLength = 0;
                    }

                    /*file = new File(fullPath);
                    if (file.exists()) {
                        downloadedLength = file.length();
                    }

                    long contentLength = HttpUtil.getContentLength(url);
                    if (contentLength <= 0) {
                        return;
                    } else if (contentLength == downloadedLength) {
                      // 程序卸载后，之前下载的完整文件未删除。但新程序的SharedPreferences里没有保存
                      // 这张图片的路径
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(context)
                                .edit();
                        editor.putString("pic_background", fullPath);
                        editor.apply();
                        callBack.onResponse(fullPath);
                        return;
                    }
                    */



                    saveFile = new RandomAccessFile(file, "rw");
                    saveFile.seek(downloadedLength); // 打开了saveFile文件

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .addHeader("RANGE", downloadedLength + "-")
                            .url(url)
                            .build();
                    response = client.newCall(request).execute();
                    if (response != null && response.isSuccessful()) {
                        inputStream = response.body().byteStream();

                        // int total = 0;
                        int len;
                        byte[] bytes = new byte[1024*1024*2];
                        while ((len = inputStream.read(bytes)) != -1) {
                            saveFile.write(bytes, 0, len);
                            // total += len;
                        }

                        response.body().close();
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(context)
                                .edit();
                        editor.putString("pic_background", fullPath);
                        editor.apply();
                        callBack.onResponse(fullPath);
                        // return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    try {
//                        if (saveFile != null) {
//                            saveFile.close();
//                        }
//                        if (file != null) {
//                            file.delete();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                } finally {

                    // 处理调用close()函数的异常
                    if (response != null) {
                        response.body().close();
                    }
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }

                        if (saveFile != null) {
                            saveFile.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            // end of run()
        }).start();
    }

}
