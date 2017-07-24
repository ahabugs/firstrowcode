package activity.example.com.a6_3_1sharedpreferencestest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_save = (Button)findViewById(R.id.button_save);
        Button button_restore = (Button)findViewById(R.id.button_restore);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("name", "Tom");
                editor.putInt("age", 28);
                editor.putBoolean("married", false);
                editor.apply();
                Toast.makeText(v.getContext().getApplicationContext(),
                        "save data", Toast.LENGTH_LONG).show();

            }
        });

        button_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("name", "");
                int age = pref.getInt("age", 28);
                boolean married = pref.getBoolean("married", false);
                Log.d(TAG, "onClick: name is " + name);
                Log.d(TAG, "onClick: age is " + age);
                Log.d(TAG, "onClick: married is " + married);
            }
        });
    }
}
