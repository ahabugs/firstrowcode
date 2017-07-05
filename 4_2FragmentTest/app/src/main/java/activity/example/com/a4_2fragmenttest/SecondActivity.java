package activity.example.com.a4_2fragmenttest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(this);
        replaceFragment(new RightFragment1());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                replaceFragment(new AnotherRightFragment());
                break;
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        /* it seems addToBackStack(null) could be called before transaction.replace()*/
        /*transaction.addToBackStack(null);*/
        transaction.replace(R.id.right_layout1, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
