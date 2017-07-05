package activity.example.com.a4_5fragmentbestpractice;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsContentActivity extends AppCompatActivity {
    private List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        initNewsList();
    }


    /* generate fixed news messages */
    private void initNewsList() {
        for (int i = 0; i < 50; i++) {
            News news = new News();
            news.setTittle("News tittle " + i);
            news.setContent(getRandomLengthContent("News tittle " + i + ". "));
            this.newsList.add(news);
        }
    }

    private String getRandomLengthContent(String content) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(content);
        }

        return builder.toString();
    }


    public List<News> getNewsList() {
        return this.newsList;
    }

    /*
    public interface Handler {
         void handleMessage(News news);


        public void handleMessage(News news) {
            if (isTwoPanel) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                NewsContentFrag newsContentFrag = (NewsContentFrag)
                        fragmentManager.findFragmentById(R.id.news_content_frag);
                newsContentFrag.refreshContentFrag(news.getTittle(),
                        news.getContent());
            } else {
                NewsContentSinglePanelActivity.actionStart(getActivity(),
                        news.getTittle(), news.getContent());
            }
        }

    }
    */

    /*
    if (getActivity().findViewById(R.id.news_content_frame_layout) != null) {
        isTwoPanel = true;
        this.handler = new NewsContentActivity.Handler() {
            @Override
            public void handleMessage(News news) {
                FragmentManager fragmentManager =
                        newsContentActivity.getSupportFragmentManager();
                NewsContentFrag newsContentFrag = (NewsContentFrag)
                        fragmentManager.findFragmentById(R.id.news_content_frag);
                newsContentFrag.refreshContentFrag(news.getTittle(),
                        news.getContent());
            }
        };
    } else {
        isTwoPanel = false;
        this.handler = new NewsContentActivity.Handler() {
            @Override
            public void handleMessage(News news) {
                NewsContentSinglePanelActivity.actionStart(newsContentActivity,
                        news.getTittle(), news.getContent());
            }
        };
    }
    */
}
