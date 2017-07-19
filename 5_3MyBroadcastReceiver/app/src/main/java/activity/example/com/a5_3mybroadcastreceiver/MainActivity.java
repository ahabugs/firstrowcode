package activity.example.com.a5_3mybroadcastreceiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button_send_broadcast);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send_broadcast:
                Intent intent1 = new Intent("activity.example.com.a5_3mybroadcastreceiver" +
                        ".MY_BROADCAST");
                sendBroadcast(intent1);
                break;
            default:
        }
    }
}
