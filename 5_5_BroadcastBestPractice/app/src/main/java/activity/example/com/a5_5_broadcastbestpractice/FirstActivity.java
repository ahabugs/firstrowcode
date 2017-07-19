package activity.example.com.a5_5_broadcastbestpractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button button1 = (Button)findViewById(R.id.button_offline);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent("activity.example.com.a5_5broadcastbestpractice" +
                        ".FORCE_OFFLINE");
                sendBroadcast(intent1);
            }
        });
    }
}
