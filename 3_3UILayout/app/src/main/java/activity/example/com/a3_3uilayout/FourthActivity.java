package activity.example.com.a3_3uilayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FourthActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        Button button1 = (Button) findViewById(R.id.button_FrameLayout1);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_FrameLayout1:
                Intent intent1 = new Intent(FourthActivity.this,
                        PercentageFrameActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
