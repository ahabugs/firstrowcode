package activity.example.com.a3_3uilayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Button button1 = (Button) findViewById(R.id.button_ThirdLayout_1);
        button1.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.button_ThirdLayout_2);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ThirdLayout_1:
                Intent intent1 = new Intent(ThirdActivity.this, AnotherStyle_RelativeLayoutActivity.class);
                startActivity(intent1);
                break;
            case R.id.button_ThirdLayout_2:
                Intent intent2 = new Intent(ThirdActivity.this, PercentageRelativeLayoutActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
