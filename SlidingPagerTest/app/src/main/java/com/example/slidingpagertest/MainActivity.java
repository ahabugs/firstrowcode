package com.example.slidingpagertest;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener{

    private static final String TAG = "MainActivity";
    // ViewPager控件
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    // ViewPager的适配器
    private MyPagerAdapter pagerAdapter;
    // 适配器内容
    private List<Fragment> fragmentList = new ArrayList<>();
    private PageContentFragment pageContentFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        // 添加一个fragment
        PageContentFragment pageContentFrag = new PageContentFragment();
        fragmentList.add(pageContentFrag);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(1);



        Button buttonAddPage = (Button)findViewById(R.id.button_add_page);
        Button buttonDeletePage = (Button)findViewById(R.id.button_delete_page);
        buttonAddPage.setOnClickListener(this);
        buttonDeletePage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_page:
                addPage();
                break;
            case R.id.button_delete_page:
                deletePage();
                break;
            default:
                break;
        }
    }


    private void addPage() {
        generatePage();
    }
    private void generatePage() {
        PageContentFragment pageContentFrag = new PageContentFragment();

        /*
         * getView一直返回null，跟踪发现这里的getView()返回后，Fragment的onCreateView()
         * 才调用
         */
        /*
        View view = pageContentFrag.getView();
        if (view != null) {
            TextView textView = (TextView) (view.findViewById(R.id.text_view_title));
            String text = "Hello World" + fragmentList.size();
            textView.setText(text);
        } else {
            Log.d(TAG, "generatePage: a nullable fragment view");
        }*/

        String titleString = "Hello World ==_== " +  + fragmentList.size();
        Bundle bundle = new Bundle();
        bundle.putString("title", titleString);
        bundle.putInt("current_position", fragmentList.size());
        pageContentFrag.setArguments(bundle);

        fragmentList.add(pageContentFrag);
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(fragmentList.size());
        // pageContentFrag.onHiddenChanged(true);
    }

    private void deletePage() {
        int position = viewPager.getCurrentItem();
        fragmentList.remove(position);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled: " + position);
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged: " + state);
    }
}
