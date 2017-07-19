package activity.example.com.a6_2filepersistencetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private FileReadWriteOpt fileReadWriteOpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.edit_text_type_in);
        /*FileOutputStream fileOutputStream = openFileOutput("hello", Context.MODE_PRIVATE);*/
        fileReadWriteOpt = new FileReadWriteOpt(this, "hello");
        String string = fileReadWriteOpt.load();
        if (!TextUtils.isEmpty(string)) {
            editText.setText(string);
            editText.setSelection(string.length());
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputString = editText.getText().toString();
        fileReadWriteOpt.save(inputString);
    }
}
