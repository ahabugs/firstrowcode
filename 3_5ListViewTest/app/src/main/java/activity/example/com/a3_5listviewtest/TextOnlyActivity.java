package activity.example.com.a3_5listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TextOnlyActivity extends AppCompatActivity {

    private String[] data = {"Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
            "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
            "Pineapple", "Strawberry", "Cherry", "Mango"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_only);

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(TextOnlyActivity.this,
                android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView) findViewById(R.id.listview_textonly1);
        listView.setAdapter(adapter);
    }
}
