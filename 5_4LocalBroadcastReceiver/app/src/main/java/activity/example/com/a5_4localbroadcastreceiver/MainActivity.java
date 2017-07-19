package activity.example.com.a5_4localbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private LocalBroadcastManager localBroadcastManager;
    private LocalBroadcastReceiver localReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("activity.example.com.a5_3mybroadcastreceiver.MY_BROADCAST");
        localReceiver = new LocalBroadcastReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);

        Button button1 = (Button)findViewById(R.id.button_send_local_broadcast);
        button1.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    class LocalBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "received local broadcast", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send_local_broadcast:
                Intent intent1 = new Intent("activity.example.com.a5_3mybroadcastreceiver" +
                        ".MY_BROADCAST");
                localBroadcastManager.sendBroadcast(intent1);
                break;
            default:
        }
    }
}
