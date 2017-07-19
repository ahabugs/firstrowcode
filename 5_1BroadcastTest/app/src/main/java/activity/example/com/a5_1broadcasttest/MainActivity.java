package activity.example.com.a5_1broadcasttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button_activity_1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button_activity_2);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_activity_1:
                Intent intent1 = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent1);
                break;
            case R.id.button_activity_2:
                Intent intent2 = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent2);
                break;
            default:
        }
    }
}
