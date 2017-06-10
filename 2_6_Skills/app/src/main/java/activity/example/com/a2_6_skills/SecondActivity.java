package activity.example.com.a2_6_skills;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends BaseActivity {
    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String data1 = intent.getStringExtra("param1");
        String data2 = intent.getStringExtra("param2");
        Log.d(TAG, "onCreate: get data "+data1+" and "+data2);

        Button button1 = (Button)findViewById(R.id.button_ThirdActivity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        Intent intent = new Intent();
        intent.putExtra("data_return", "Hello FirstActiviy, I'm back");
        setResult(RESULT_OK, intent);
        finish();
    }

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("param1", data1);
        intent.putExtra("param2", data2);
        /*context.startActivity(intent);*/
        FragmentActivity fragmentActivity = (FragmentActivity)context;
        fragmentActivity.startActivityForResult(intent, FirstActivity.MY_REQ_CODE);
    }
}
