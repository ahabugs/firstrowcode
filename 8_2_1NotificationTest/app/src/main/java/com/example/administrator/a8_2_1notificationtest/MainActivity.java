package com.example.administrator.a8_2_1notificationtest;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
/*import android.graphics.Bitmap;*/
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendNotice = (Button)findViewById(R.id.button_send_notice);
        Button sendNoticeVibrate = (Button)findViewById(R.id.button_send_notice_vibrate);
        Button sendNoticeLight = (Button)findViewById(R.id.button_send_notice_light);
        Button sendNoticeDefault = (Button)findViewById(R.id.button_send_notice_default);
        Button sendNoticeBigText = (Button)findViewById(R.id.button_send_notice_bitText);
        Button sendNoticeBigPicture = (Button)findViewById(R.id.button_send_notice_bigPicture);

        sendNotice.setOnClickListener(this);
        sendNoticeVibrate.setOnClickListener(this);
        sendNoticeLight.setOnClickListener(this);
        sendNoticeDefault.setOnClickListener(this);
        sendNoticeBigText.setOnClickListener(this);
        sendNoticeBigPicture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*
         * Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
         * R.mipmap.ic_launcher);
         */
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        android.support.v4.app.NotificationCompat.Builder builder;

        switch (v.getId()) {
            case R.id.button_send_notice:
                notification = new NotificationCompat.Builder(this)
                        .setContentTitle("This is content title")
                        .setContentText("This is content text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setSound(Uri.fromFile(new File(
                                "/system/media/audio/ringtones/ringtone_001.ogg")))
                        .build();
                manager.notify(NOTIFICATION_ID, notification);
                //finish();
                /*manager.cancel(NOTIFICATION_ID);*/
                break;
            case R.id.button_send_notice_vibrate:
                notification = new NotificationCompat.Builder(this)
                        .setContentTitle("This is content title")
                        //.setContentText("This is vibrate content text")
                        .setContentText("This is vibrate content text " +
                                "This is vibrate content text " +
                                "This is vibrate content text " +
                                "This is vibrate content text " +
                                "This is vibrate content text " +
                                "This is vibrate content text")

                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setVibrate(new long[] {0, 1000, 1000, 1000})
                        .build();
                manager.notify(NOTIFICATION_ID, notification);
                break;

            case R.id.button_send_notice_light:
                notification = new NotificationCompat.Builder(this)
                        .setContentTitle("This is content title")
                        .setContentText("This is light content text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setLights(Color.GREEN, 5000, 5000)
                        .build();
                manager.notify(NOTIFICATION_ID, notification);
                break;

            case R.id.button_send_notice_default:
                notification = new NotificationCompat.Builder(this)
                        .setContentTitle("This is content title")
                        .setContentText("This is default content text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .build();
                manager.notify(NOTIFICATION_ID, notification);
                break;

            case R.id.button_send_notice_bitText:
                String bigString = new String("This is a big string big string big string" +
                        "big string big string big string big string big string big string" +
                        "big string big string big string big string big string big string" +
                        "big string");
                /**/
                notification = new NotificationCompat.Builder(this)
                        .setContentTitle("This is big text title")
                        .setContentText("This is big text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.mipmap.ic_launcher))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(bigString))
                        .setContentIntent(pendingIntent)
                        //.setAutoCancel(true)
                        .build();
                manager.notify(NOTIFICATION_ID, notification);
                break;

            /**/
            case R.id.button_send_notice_bigPicture:
                notification = new NotificationCompat.Builder(this)
                        .setContentTitle("This is bit picture title")
                        .setContentText("This is bit picture")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .setBigContentTitle("BigContentTitle")
                                .setSummaryText("SummaryText")
                                .bigPicture(BitmapFactory.decodeResource(getResources(),
                                        R.drawable.big_image))
                        )
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                        //.setAutoCancel(true)
                manager.notify(NOTIFICATION_ID, notification);
                break;

            default:
                break;
        }
    }
}