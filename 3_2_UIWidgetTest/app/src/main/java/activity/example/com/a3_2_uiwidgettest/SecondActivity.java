package activity.example.com.a3_2_uiwidgettest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button button1 = (Button) findViewById(R.id.button_AlertDialog);
        button1.setOnClickListener(this);

        Button button2 = (Button) findViewById(R.id.button_ProgressDialog);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_AlertDialog:
                AlertDialog.Builder dialog = new AlertDialog.Builder(SecondActivity.this);
                dialog.setTitle("This is AlertDialog");
                dialog.setCancelable(false);
                dialog.setMessage("Something Important!!!");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.show();
                break;

            case R.id.button_ProgressDialog:
                ProgressDialog progressDialog = new ProgressDialog(SecondActivity.this);
                progressDialog.setTitle("This's ProgressDialog");
                progressDialog.setCancelable(true);
                /*progressDialog.setCancelable(false);*/
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                /*
                progressDialog.setCancelable(false);
                progressDialog.dismiss();
                 */
                /*
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    Log.d(TAG, "onClick: button_ProgressDialog Thread.sleep Exception");
                }
                progressDialog.dismiss();
                */
                break;
            default:
                break;
        }
    }
}
