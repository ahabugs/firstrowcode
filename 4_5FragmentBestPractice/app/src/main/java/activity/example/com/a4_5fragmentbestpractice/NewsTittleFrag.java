package activity.example.com.a4_5fragmentbestpractice;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bingo on 2017/6/27.
 */

public class NewsTittleFrag extends Fragment{
    private NewsContentActivity newsContentActivity;

    private boolean isTwoPanel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsContentActivity = (NewsContentActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news_titile_frag, container, false);

        RecyclerView newsTittleRecyclerView = (RecyclerView) v.findViewById(
                R.id.news_tittle_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsTittleRecyclerView.setLayoutManager(layoutManager);

        NewsAdapter adapter = new NewsAdapter(newsContentActivity.getNewsList(),
                new NewsHandler());
        /*
        NewsAdapter adapter = new NewsAdapter(newsContentActivity.getNewsList());
        */
        newsTittleRecyclerView.setAdapter(adapter);

        return v;
    }

    /* mainActivity include R.layout.activity_news_content
     * in activity_news_content.xml, only NewsTittleFrag will be included if in
     * single page mode; if in double page mode, both NewsTittleFrag and
     * NewsContentFrag will be included.
     * if in double page mode, news_content_frame_layout will be used. so to judge by id
     * "news_content_frame_layout", we can determine whether it is in double mode
     *
     * */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_frame_layout) != null) {
            isTwoPanel = true;
        } else {
            isTwoPanel = false;
        }
        /*isTwoPanel = getActivity().findViewById(R.id.news_content_frame_layout) != null;*/
    }

    class NewsHandler {
        public void handleMessage(News news) {
            if (isTwoPanel) {
                FragmentManager fragmentManager = getFragmentManager();
                NewsContentFrag newsContentFrag = (NewsContentFrag)
                        fragmentManager.findFragmentById(R.id.news_content_frag);
                newsContentFrag.refreshContentFrag(news.getTittle(),
                        news.getContent());
            } else {
                String newsTittle = news.getTittle();
                String newsContent = news.getContent();
                NewsContentSinglePanelActivity.actionStart(getActivity(),
                        newsTittle, newsContent);
            }
        }

    }


}
