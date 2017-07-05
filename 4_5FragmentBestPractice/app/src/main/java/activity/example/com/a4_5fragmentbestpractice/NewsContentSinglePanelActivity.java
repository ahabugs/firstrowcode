package activity.example.com.a4_5fragmentbestpractice;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewsContentSinglePanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content_single_panel);


        FragmentManager fragmentManager = getSupportFragmentManager();
        NewsContentFrag newsContentFrag = (NewsContentFrag)fragmentManager
                .findFragmentById(R.id.single_activity_news_content_frag);
        Intent intent = getIntent();
        String newsTittle = intent.getStringExtra("news_tittle");
        String newsContent = intent.getStringExtra("news_content");
        newsContentFrag.refreshContentFrag(newsTittle,
                newsContent);
    }

    /* called when clicking a recycler item when the main activity is in single page */
    public static void actionStart(Context context, String newsTittle, String newsContent) {
    /* public static void actionStart(Context context, News news) { */
        Intent intent = new Intent(context, NewsContentSinglePanelActivity.class);
        /**
         * intent.putExtra("news_tittle", news.getTittle());
         * intent.putExtra("news_content", news.getContent());
         */
        intent.putExtra("news_tittle", newsTittle);
        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
    }
}
