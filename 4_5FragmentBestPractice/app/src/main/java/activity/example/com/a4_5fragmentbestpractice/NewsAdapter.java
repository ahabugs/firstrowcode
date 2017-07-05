package activity.example.com.a4_5fragmentbestpractice;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bingo on 2017/7/1.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> newsList;
    NewsTittleFrag.NewsHandler newsHandler;

    public NewsAdapter(List<News> newsList, NewsTittleFrag.NewsHandler handler) {
    /* public NewsAdapter(List<News> newsList) { */
        this.newsList = newsList;
        this.newsHandler = handler;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView newTittleTextView;
        public ViewHolder(View v) {
            super(v);
            newTittleTextView = (TextView) v.findViewById(R.id.text_view_news_tittle_recycler_item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.news_tittle_recycler_item, parent, false);
        final ViewHolder holder = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                News news = newsList.get(holder.getAdapterPosition());
                newsHandler.handleMessage(news);
                /*
                if (isTowPanel) {
                    FragmentManager fragmentManager = getFragmentManager();
                    NewsContentFrag newsContentFrag = (NewsContentFrag)
                            fragmentManager.findFragmentById(R.id.news_content_frag);
                    newsContentFrag.refreshContentFrag(news.getTittle(),
                            news.getContent());
                } else {
                    NewsContentSinglePanelActivity.actionStart(getActivity(),
                            news.getTittle(), news.getContent());
                }
                */
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.newTittleTextView.setText(news.getTittle());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}