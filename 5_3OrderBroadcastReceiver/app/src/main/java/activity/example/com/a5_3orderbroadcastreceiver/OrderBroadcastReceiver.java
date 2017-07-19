package activity.example.com.a5_3orderbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class OrderBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        /*throw new UnsupportedOperationException("Not yet implemented");*/
        Toast.makeText(context, "received in OrderBroadcastReceiver",
                Toast.LENGTH_LONG).show();
        abortBroadcast();
    }
}
