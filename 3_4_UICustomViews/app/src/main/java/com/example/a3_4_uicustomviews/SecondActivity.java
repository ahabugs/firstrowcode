package com.example.a3_4_uicustomviews;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Button button1 = (Button) findViewById(R.id.button_TitleLayout_Back);
        Button button2 = (Button) findViewById(R.id.button_TitleLayout_Edit);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_TitleLayout_Back:
                finish();
                break;
            case R.id.button_TitleLayout_Edit:
                Toast.makeText(SecondActivity.this, "You clicked Edit Button in SecondActivy", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}

