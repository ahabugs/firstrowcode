package activity.example.com.a2_5_launchmode_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class OtherActivity extends AppCompatActivity {
    private static final String TAG = "OtherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Log.d(TAG, "onCreate: hello world"+"Task id is "+getTaskId());
        Button button1 = (Button)findViewById(R.id.button_backSecondactivity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button)findViewById(R.id.button_backSingleTaskActivity);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherActivity.this, SingleTaskActivity.class);
                startActivity(intent);
            }
        });

        Button button3 = (Button)findViewById(R.id.button_backSingleInstanceActivity);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherActivity.this, SingleInstanceActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed: hello world "+"Task id is "+getTaskId());
    }
}
