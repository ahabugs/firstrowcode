package activitytest.com.example.activitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        Button button1 = (Button)findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Toast.makeText(FirstActivity.this, "You clicked Button1",
                        Toast.LENGTH_SHORT).show();
                 */
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button)findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button button3 = (Button)findViewById(R.id.button_3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("activitytest.com.example.activitytest.ACTION_START");
                intent.addCategory("activitytest.com.example.activitytest.MY_CATEGORY");
                startActivity(intent);
            }
        });
    }

    /*onCreateOptionsMenu(), onOptionsItemSelected() in android.qpp.activity
    * inflate() in android.view.Inflater*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*return super.onCreateOptionsMenu(menu);*/
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(FirstActivity.this, "You selected Add",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(FirstActivity.this, "You selected remove",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
