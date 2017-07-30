package activity.example.com.a7_2runtimepermissiontest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button makeCall = (Button)findViewById(R.id.button_make_call);
        makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{ Manifest.permission.CALL_PHONE }, 1);
                    /*
                    * ActivityCompat.checkSelfPermission(MainActivity.this,
                    * Manifest.permission.CALL_PHONE);
                    * */
                } else {
                    call();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        /*super.onRequestPermissionsResult(requestCode, permissions, grantResults);*/
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && permissions[0].equals(Manifest.permission.CALL_PHONE)) {
                    call();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    private void call () {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"));
            /*
            * Intent intent = new Intent(Intent.ACTION_CALL);
            * intent.setData(Uri.parse("tel:10086"));
            * */
            startActivity(intent);
        } catch (SecurityException se) {
            /* not request runtime permission in application, permission denied throws
            * SecurityException
            * */
            se.printStackTrace();
        }


    }
}
