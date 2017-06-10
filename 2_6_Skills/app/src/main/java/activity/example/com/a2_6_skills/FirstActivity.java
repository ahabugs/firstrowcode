package activity.example.com.a2_6_skills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends BaseActivity {
    private static final String TAG = "FirstActivity";
    static final int MY_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_first);

        Button button1 = (Button)findViewById(R.id.button_FirstActivity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* test starndard launchmode */
                Intent intent = new Intent(FirstActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button)findViewById(R.id.button_SecondActivity);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
                */
                SecondActivity.actionStart(FirstActivity.this,"test_param1", "test_param2");
            }
        });

        Button button3 = (Button)findViewById(R.id.button_Finish);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.finishAll();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*super.onActivityResult(requestCode, resultCode, data);*/
        switch (requestCode) {
            case MY_REQ_CODE:
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("data_return");
                    Log.d(TAG, "onActivityResult: "+returnData);
                }
                break;
            default:
        }
    }
}
