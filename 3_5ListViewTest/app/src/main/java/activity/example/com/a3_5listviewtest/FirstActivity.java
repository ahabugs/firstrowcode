package activity.example.com.a3_5listviewtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button button1 = (Button) findViewById(R.id.button_listview1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FirstActivity.this, TextOnlyActivity.class);
                startActivity(intent1);
            }
        });

        EditText editText;
        ViewGroup viewGroup;
        Button button2 = (Button) findViewById(R.id.button_listview2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FirstActivity.this, CustomizedListViewActivity.class);
                startActivity(intent1);
            }
        });

        Button button3 = (Button) findViewById(R.id.button_listview3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FirstActivity.this, RecyclerViewActivityVERT.class);
                startActivity(intent1);
            }
        });

        Button button4 = (Button) findViewById(R.id.button_listview4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FirstActivity.this, RecyclerViewActivityHORI.class);
                startActivity(intent1);
            }
        });

        Button button5 = (Button) findViewById(R.id.button_listview5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FirstActivity.this, FruitStaggerLayoutActivity.class);
                startActivity(intent1);
            }
        });
    }
}
