package com.example.administrator.a8_3_1cameralalbumtest;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Uri imageUir;
    private ImageView picture;
    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    // private static final int CROP_PHOTO = 3;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button takePhoto = (Button)findViewById(R.id.button_take_photo);

        Button choosePhoto = (Button)findViewById(R.id.button_choose_photo);
        picture = (ImageView)findViewById(R.id.image_view_picture);

        takePhoto.setOnClickListener(this);
        choosePhoto.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_take_photo:
                /*to store image taken*/
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*
                 * generate uri for image file to be store. this uri will be used by camera
                 * the camera will store the image under the directory of this uri.
                 * 将拍照的照片保存到uri路径下
                 * */
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUir = FileProvider.getUriForFile(MainActivity.this, "com.example" +
                            ".administrator.a8_3_1cameralalbumtest.fileprovider", outputImage);
                } else {
                    imageUir = Uri.fromFile(outputImage);
                }


/*
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
*/
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                /*
                 * EXTRA_OUTPUT是uri对应的key，拍照摄像头用到
                 * */
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUir);
                startActivityForResult(intent, TAKE_PHOTO);
                break;

            case R.id.button_choose_photo:
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                    Toast.makeText(this, "request READ_EXTERNAL_STORE", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "handleImage onClick: " + "request READ_EXTERNAL_STORE");
                } else {
                    openAlbum();
                }

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 480);
        intent.putExtra("outputY", 360);
        intent.putExtra("scale", true);
        //intent.putExtra("output", Uri.parse("file:/" + mFile.getAbsolutePath()));
 //       intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        intent.putExtra("return-data", true);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                Log.d(TAG, "handleImage MainActivity Debug: " + requestCode);
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(imageUir));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                Log.d(TAG, "handleImage" + "onActivityResult: " + requestCode);
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        /* >= android 4.4*/
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        String path = null;
        Log.d(TAG, "handleImage: " + "OnKitKat" + uri.toString());

        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            Log.d(TAG, "handleImage: " + "docId" + docId);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                // 将docId以":"分成多个string
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(
                    uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse(
                        "content://downloads/public_downloads"), Long.valueOf(docId));
                path = getImagePath(contentUri, null);
            }

//      } else if ("content".equalsIgnoreCase(uri.getAuthority())) {
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            path = getImagePath(uri, null);

//      } else if ("file".equalsIgnoreCase(uri.getAuthority())) {
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }

        if (path != null) {
            displayImage(path);
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        Log.d(TAG, "handleImage: " + "BeforeKitKat" + uri.toString());
        String path = getImagePath(uri, null);
        displayImage(path);
    }

    private String getImagePath(Uri uri, String selection) {
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

    private void displayImage(String path) {
        Log.d(TAG, "handleImage" + "displayImage: " + path);
        if (path != null) {
            Bitmap bitmap = compressImage(BitmapFactory.decodeFile(path));
            // picture.setImageBitmap(bitmap);
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            picture.setImageDrawable(drawable);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap compressImage(Bitmap image) {
//        int options = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 30, baos);
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        while ((baos.toByteArray().length >> 12) > 100 && options >= 0) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            options -= 10;//每次都减少10
//        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(isBm);
    }

 /*   private void cropPicture(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 480);
        intent.putExtra("outputY", 360);
//        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_PHOTO);
    }

    private void displayCropImage(Intent data) {
        // get the map of all extras previously added with putExtra(), or null
        // if none have been added.
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            picture.setImageDrawable(drawable);
        } else {
            Toast.makeText(this, "failed to get extras", Toast.LENGTH_LONG);
        }
    }
*/
}
