package com.example.a12_3_2_navigationviewtest;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FruitActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "FruitActivity";
    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";
    private FruitComment fruitComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        Intent intent = getIntent();

        // image in  collapse toolbar layout
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        int fruitId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
        ImageView imageView = (ImageView)findViewById(R.id.image_view_fruit_activity);
        Glide.with(FruitActivity.this).load(fruitId).into(imageView);

        // toolbar in collapse toolbar layout
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_fruit_activity);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar_fruit_activity);
        collapsingToolbarLayout.setTitle(fruitName);

        String fruitContent = generateFruitContent(fruitName);
        TextView textView = (TextView)findViewById(R.id.text_view_fruit_content);
        textView.setText(fruitContent);

        FloatingActionButton fab_comment = (FloatingActionButton)
                findViewById(R.id.fab_fruit_comment);
        fab_comment.setOnClickListener(this);
        fruitComment = new FruitComment(FruitActivity.this);
    }

    private String generateFruitContent(String string) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            builder.append(string);
        }
        return builder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_fruit_comment:
                if (fruitComment != null) {
                    fruitComment.show();
                    Log.d(TAG, "onClick: click the fruit comment fab");
                }
                break;
            default:
                break;
        }
    }
}
