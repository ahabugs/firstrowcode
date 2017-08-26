package com.example.administrator.a10_6_downloadservicebestpractice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class DownloadService extends Service {
    private static final String TAG = "DownloadService";
    private static final int NOTIFICATION_ID_1 = 1;
    private String downloadUrl = null;
    private DownloadTask task = null;
    private DownloadBinder binder = new DownloadBinder();

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");
        return  binder;
    }

    class DownloadBinder extends Binder {

        public void startDownload(String url) {

            if (task == null) {
                startForeground(1, getNotification("Download...", 0));
                Log.d(TAG, "startDownload: Downloading...");
                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_LONG).show();

                // start a download task
                downloadUrl = url;
                // ...
                task = new DownloadTask(listener);
                task.execute(downloadUrl);
                Log.d(TAG, "startDownload: Downloading...");
            }
        }

        public void pauseDownload() {
            if (task != null) {
                task.pauseDownload();
            }
            Log.d(TAG, "pauseDownload: Pause...");
        }

        // stop the download task and delete the file that already downloaded
        public void cancelDownload() {
            if (task == null) {
                return;
            }
            // task is running
            task.cancelDownload();
            stopForeground(true);
            Log.d(TAG, "cancelDownload: Canceled...");
            Toast.makeText(DownloadService.this, "Canceled...", Toast.LENGTH_LONG).show();

            // stop the download task
            // ...
//            if (downloadUrl == null) {
//                return;
//            }
//            // delete the file that already downloaded
//            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
//            String directory = Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_DOWNLOADS).getPath();
//            File file = new File(directory+fileName);
//            if (file.exists()) {
//                file.delete();
//            }

            // getNotificationManager().cancel(NOTIFICATION_ID_1);
        }
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String tittle, int progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(tittle);
        if (progress >= 0) {
            mBuilder.setContentText(progress + "%");
            mBuilder.setProgress(100, progress, false);
        }

        if (progress < 0) {
            mBuilder.setAutoCancel(true);
        }
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher));
        mBuilder.setContentIntent(pi);
        return mBuilder.build();
    }

    /*
    * 将listener放在service里方便统一管理。比如后期添加同时下载多个任务时，我们把UI提示统一
    * 放在service里，每一个任务都可以调用。
    * */
    private DownloadListener listener = new DownloadListener() {

        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(NOTIFICATION_ID_1,
                    getNotification("Downloading...", progress));
        }

        // send a notification only when download successfully or failed. pause or cancel
        // option doesn't do it.
        @Override
        public void onSuccess() {
            task = null;
            stopForeground(true);
            getNotificationManager().notify(NOTIFICATION_ID_1,
                    getNotification("Download Success", -1));
//            getNotificationManager().cancel(NOTIFICATION_ID_1);
            Log.d(TAG, "onSuccess: Download Success...");
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailed() {
            task = null;
            stopForeground(true);
            getNotificationManager().notify(NOTIFICATION_ID_1,
                    getNotification("Download Failed", -1));
            Log.d(TAG, "onSuccess: Download Failed...");
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPaused() {
            task = null;
            Log.d(TAG, "onSuccess: Download Paused...");
            Toast.makeText(DownloadService.this, "Download Paused", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCanceled() {
            task = null;
            stopForeground(true);
            Log.d(TAG, "onSuccess: Download Canceled...");
            Toast.makeText(DownloadService.this, "Download Canceled", Toast.LENGTH_LONG).show();
        }
    };
}
