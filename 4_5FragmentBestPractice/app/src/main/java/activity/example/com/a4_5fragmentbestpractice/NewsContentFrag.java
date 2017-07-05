package activity.example.com.a4_5fragmentbestpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bingo on 2017/6/27.
 */

public class NewsContentFrag extends Fragment {

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.news_content_frag, container, false);
        return this.view;
    }

    public void refreshContentFrag(String newsTittle, String newsContent) {
        View v = view.findViewById(R.id.visibility_layout);
        v.setVisibility(View.VISIBLE);
        TextView newsTittleTextView = (TextView)view.findViewById(R.id.news_tittle);
        TextView newsContentTextView = (TextView)view.findViewById(R.id.news_content);

        newsTittleTextView.setText(newsTittle);
        newsContentTextView.setText(newsContent);
    }
}
