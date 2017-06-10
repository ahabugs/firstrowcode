package activity.example.com.a3_2_uiwidgettest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "FirstActivity";
    private EditText editText;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button button1 = (Button)findViewById(R.id.button_EditText);
        button1.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.edit_text1);

        imageView = (ImageView) findViewById(R.id.image_view1);
        Button button2 = (Button) findViewById(R.id.button_ImageView);
        button2.setOnClickListener(this);

        Button button3 = (Button) findViewById(R.id.button_ProgressBar);
        button3.setOnClickListener(this);
        Button button4 = (Button) findViewById(R.id.button_PROBar_Inc);
        button4.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        Button button5 = (Button) findViewById(R.id.button_SecondActivity);
        button5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_EditText:
                String string1 = editText.getText().toString();
                Toast.makeText(FirstActivity.this, string1, Toast.LENGTH_LONG).show();
                break;

            case R.id.button_ImageView:
                imageView.setImageResource(R.drawable.img101);
                break;

            case R.id.button_ProgressBar:
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
                break;

            case R.id.button_PROBar_Inc:
                int progress = progressBar.getProgress();
                progress += 10;
                if (progress > 100)
                    progress = 10;
                progressBar.setProgress(progress);
                break;

            case R.id.button_SecondActivity:
                /*Log.d(TAG, "onClick: Is SecondActivity started?");*/
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

}
