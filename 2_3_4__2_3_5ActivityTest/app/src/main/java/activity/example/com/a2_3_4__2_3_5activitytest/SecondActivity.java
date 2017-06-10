package activity.example.com.a2_3_4__2_3_5activitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
        Intent intent = new Intent();
        intent.putExtra("data_return", "back from SecondActivity to FirstActivity");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String data = intent.getStringExtra("extra_data");
        Log.d("SeccondActivity", data);

        Button button1 = (Button)findViewById(R.id.button_2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.putExtra("data_return", "Return from the 2nd Activity to the 1st's");
                setResult(RESULT_OK, intent2);
                finish();
            }
        });


    }
}
