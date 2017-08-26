package com.example.administrator.a10_6_downloadservicebestpractice;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 17-8-14.
 */

public class DownloadTask extends AsyncTask<String, Integer, Integer> {
    private static final String TAG = "DownloadTask";

    public static final int TYPE_SUCCESS = 1;
    public static final int TYPE_FAILED = 2;
    public static final int TYPE_PAUSE = 3;
    public static final int TYPE_CANCELED = 4;

    private int lastProgress = 0;
    private boolean isPaused = false;
    private boolean isCaceled = false;
    private DownloadListener listener = null;
    // essential
    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        // new file to store the file downloaded
        long downloadLength = 0;
        InputStream inputStream = null;
        RandomAccessFile saveFile = null;
        File file = null;

        String directory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
        ).getPath();
        String url = params[0];
        int index = url.lastIndexOf("/");
        String fileName = url.substring(url.lastIndexOf("/"), index+10);
        try {
            // File(): Exception
            file = new File(directory + fileName);
            if (file.exists()) {
                downloadLength = file.length();
            }

            long contentLength = getContentLength(url);
            if (contentLength <= 0) {
                return TYPE_FAILED;
            } else if (contentLength == downloadLength) {
                return TYPE_SUCCESS;
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", downloadLength + "-")
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                inputStream = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                saveFile.seek(downloadLength);
                int total = 0;
                int len;
                byte[] bytes = new byte[1024*1024*2];
                while ((len = inputStream.read(bytes)) != -1) {
                    if (isCaceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSE;
                    } else {
                        total += len;
                        saveFile.write(bytes, 0, len);
                        int progress = (int)((total + downloadLength)*100/contentLength);
                        publishProgress(Integer.valueOf(progress));
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //不关是try还是catch, finally都会执行
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (saveFile != null) {
                    saveFile.close();
                }

                // if (isCaceled && file != null) {
                if (isCaceled) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // finally执行完后，如果try和catch, finally里没有返回才执行这里的return
        return TYPE_FAILED;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress =  values[0];
        if (progress >= lastProgress) {
            // send notification
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer) {
            case TYPE_SUCCESS:
                // send success
                listener.onSuccess();
                Log.d(TAG, "onPostExecute: send success notification to MainActivity");
                break;
            case TYPE_FAILED:
                // send failed
                listener.onFailed();
                Log.d(TAG, "onPostExecute: send failed notification to MainActivity");
                break;
            case TYPE_PAUSE:
                // pause
                listener.onPaused();
                Log.d(TAG, "onPostExecute: send puase notification to MainActivity");
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                Log.d(TAG, "onPostExecute: send cancel notification to MainActivity");
                break;
            default:
                break;
        }
    }

    void pauseDownload() {
        // 在doInBackground()被访问，谁解决这个变量互斥访问的问题？
        Log.d(TAG, "pauseDownload: " + Thread.currentThread().getId());
        isPaused = true;
    }

    void cancelDownload() {
        Log.d(TAG, "cancelDownload: " + Thread.currentThread().getId());
        isCaceled = true;
    }

    private long getContentLength(String url) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long length = response.body().contentLength();
            response.body().close();
            return length;
        }
        return 0;
    }
}
