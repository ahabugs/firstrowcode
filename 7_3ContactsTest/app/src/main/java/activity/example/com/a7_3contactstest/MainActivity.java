package activity.example.com.a7_3contactstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<String> contactsList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.list_view_contacts);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,*/
        adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, contactsList);
        listView.setAdapter(adapter);

        /*
        * ContextCompat.checkSelfPermission()-->check runtime permission
        * PackageManager.PERMISSION_GRANTED;
        * ActivityCompat.requestPermissions()--> request permission
        * onRequestPermissionsResult()--> pop permission request dialog to chose "OK" or "Cancel"
        * */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_CONTACTS}, 1);

        } else {
            readContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && permissions[0].equals(Manifest.permission.READ_CONTACTS)) {
                    readContacts();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    /*
    * ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    * ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
    * ContactsContract.CommonDataKinds.Phone.NUMBER
    * */
    void readContacts() {
        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.
                    Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                int count = cursor.getCount();
                Log.d(TAG, "cursor.getCount: " + count);
                cursor.moveToFirst();
                do {
                    String displayName = cursor.getString(cursor.getColumnIndex(
                       ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    ));

                    String number = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));

                    contactsList.add(displayName + "\n" + number);
                } while (cursor.moveToNext());

                adapter.notifyDataSetChanged();
            }

        } catch (SecurityException se) {
            se.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
