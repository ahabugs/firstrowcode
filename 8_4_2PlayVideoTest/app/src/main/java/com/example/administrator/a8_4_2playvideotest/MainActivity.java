package com.example.administrator.a8_4_2playvideotest;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private static final int REQ_CODE_WRITE = 1;
    private static final int REQ_CODE_DIRECTORY = 2;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView)findViewById(R.id.video_view);
        Button play = (Button)findViewById(R.id.button_play);
        Button pause = (Button)findViewById(R.id.button_pause);
        Button replay = (Button)findViewById(R.id.button_resume);
        Button seek_to = (Button)findViewById(R.id.button_seek_to);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);
        seek_to.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_CODE_WRITE);
        } else {
            //
            Log.d(TAG, "onCreate: initVideoPath");
            initVideoPath();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //
                    initVideoPath();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: You denied the permission");
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_play:
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
                break;
            case R.id.button_pause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                break;
            case R.id.button_resume:
//                if (videoView.isPlaying()) {
                    videoView.resume();
//                }
                break;
            case R.id.button_seek_to:
                //
                openFileManager();
                break;
            default:
                break;
        }
    }

    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(), "/Download/movie.mp4");
        Log.d(TAG, "initVideoPath: " + file);
        if (file.exists()) {
            videoView.setVideoPath(file.getPath());
        }
    }

    private void initVideoPath(String path) {
        if (path == null)
            return;
        File file = new File(path);
        Log.d(TAG, "initVideoPath: path = " + file);
        videoView.setVideoPath(path);
    }

    private void openFileManager() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQ_CODE_DIRECTORY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_DIRECTORY:
                Log.d(TAG, "handleVideo" + "onActivityResult: " + requestCode);
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        /* >= android 4.4*/
                        handleVideoOnKitKat(data);
                    } else {
                        handleVideoBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    private void handleVideoOnKitKat(Intent data) {
        Uri uri = data.getData();
        String path = null;
        Log.d(TAG, "handleVideo: " + "OnKitKat" + uri.toString());

        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            Log.d(TAG, "handleVideo: " + "docId" + docId);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                // 将docId以":"分成多个string
                String id = docId.split(":")[1];
                String type = docId.split(":")[0];
                /*
                * check video type, if it is not a video, just return;
                * all types include: image, video, audio
                * */
                if (!"video".equals(type)) {
                    Log.d(TAG, "handleVideo: type is" + "image");
                    Toast.makeText(this, "this is a type of image, not video. " +
                            "Please select a video", Toast.LENGTH_LONG).show();
                    return;
                }
                String selection = MediaStore.Video.Media._ID + "=" + id;
                path = getVideoPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, selection);
                /*
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getVideoPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);*/
            } else if ("com.android.providers.downloads.documents".equals(
                    uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse(
                        "content://downloads/public_downloads"), Long.valueOf(docId));
                path = getVideoPath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            path = getVideoPath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }

        if (path != null) {
            if (isValidVideoPath(path)) {
                initVideoPath(path);
            } else {
                Toast.makeText(this, "You selected an invalid video file",
                        Toast.LENGTH_LONG).show();
                Log.d(TAG, "handleVideo: isValidVideoPath = it is invalid path");
            }
        }
    }

    private void handleVideoBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        Log.d(TAG, "handleVideo: " + "BeforeKitKat" + uri.toString());
        String path = getVideoPath(uri, null);
        if (isValidVideoPath(path)) {
            initVideoPath(path);
        }
    }


    private String getVideoPath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media
                        .DATA));
            }
            cursor.close();
        }
        return path;
    }

    private boolean isValidVideoPath(String path) {
        int dotIndex = path.lastIndexOf(".");
        if (dotIndex < 0) {
            Log.d(TAG, "handleVideo: incorrect type of file. path = " + path);
            return false;
        }

        String suffix = path.substring(dotIndex, path.length()).toLowerCase();
        return checkSuffix(suffix);
    }

    private boolean checkSuffix(String suffix) {
        return ((suffix.equals(".rmvb") || suffix.equals(".wmv")
                || suffix.equals(".mp4") || suffix.equals(".avi"))? true : false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }


}

//
//    private final String[][] MIME_MapTable = {
//            //{后缀名，    MIME类型}
//            {".3gp", "video/3gpp"},
//            {".apk", "application/vnd.android.package-archive"},
//            {".asf", "video/x-ms-asf"},
//            {".avi", "video/x-msvideo"},
//            {".bin", "application/octet-stream"},
//            {".bmp", "image/bmp"},
//            {".c", "text/plain"},
//            {".class", "application/octet-stream"},
//            {".conf", "text/plain"},
//            {".cpp", "text/plain"},
//            {".doc", "application/msword"},
//            {".exe", "application/octet-stream"},
//            {".gif", "image/gif"},
//            {".gtar", "application/x-gtar"},
//            {".gz", "application/x-gzip"},
//            {".h", "text/plain"},
//            {".htm", "text/html"},
//            {".html", "text/html"},
//            {".jar", "application/java-archive"},
//            {".java", "text/plain"},
//            {".jpeg", "image/jpeg"},
//            {".jpg", "image/jpeg"},
//            {".js", "application/x-javascript"},
//            {".log", "text/plain"},
//            {".m3u", "audio/x-mpegurl"},
//            {".m4a", "audio/mp4a-latm"},
//            {".m4b", "audio/mp4a-latm"},
//            {".m4p", "audio/mp4a-latm"},
//            {".m4u", "video/vnd.mpegurl"},
//            {".m4v", "video/x-m4v"},
//            {".mov", "video/quicktime"},
//            {".mp2", "audio/x-mpeg"},
//            {".mp3", "audio/x-mpeg"},
//            {".mp4", "video/mp4"},
//            {".mpc", "application/vnd.mpohun.certificate"},
//            {".mpe", "video/mpeg"},
//            {".mpeg", "video/mpeg"},
//            {".mpg", "video/mpeg"},
//            {".mpg4", "video/mp4"},
//            {".mpga", "audio/mpeg"},
//            {".msg", "application/vnd.ms-outlook"},
//            {".ogg", "audio/ogg"},
//            {".pdf", "application/pdf"},
//            {".png", "image/png"},
//            {".pps", "application/vnd.ms-powerpoint"},
//            {".ppt", "application/vnd.ms-powerpoint"},
//            {".prop", "text/plain"},
//            {".rar", "application/x-rar-compressed"},
//            {".rc", "text/plain"},
//            {".rmvb", "audio/x-pn-realaudio"},
//            {".rtf", "application/rtf"},
//            {".sh", "text/plain"},
//            {".tar", "application/x-tar"},
//            {".tgz", "application/x-compressed"},
//            {".txt", "text/plain"},
//            {".wav", "audio/x-wav"},
//            {".wma", "audio/x-ms-wma"},
//            {".wmv", "audio/x-ms-wmv"},
//            {".wps", "application/vnd.ms-works"},
//            //{".xml",    "text/xml"},
//            {".xml", "text/plain"},
//            {".z", "application/x-compress"},
//            {".zip", "application/zip"},
//            {"", "*/*"}
//    };