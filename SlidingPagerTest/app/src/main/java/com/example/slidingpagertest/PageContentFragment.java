package com.example.slidingpagertest;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageContentFragment extends Fragment {

    private static final String TAG = "PageContentFragment";
    private View mView = null;

    public PageContentFragment() {
        // Required empty public constructor
    }

    /*
     * 当定义带参数的构造函数时
     * 提示利用Bundle来传递参数，setArguments(bundle), bundle = getArguments()
     */
    /*public PageContentFragment(String title) {

    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_content, container, false);
        mView = view;
        if (mView == null) {
            Log.d(TAG, "onCreateView: " + "view is null" );
        } else {
            Log.d(TAG, "onCreateView: view isn't null" );
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (mView == null) {
            Log.d(TAG, "onCreateView: " + "view is null" );
        } else {
            Log.d(TAG, "onCreateView: view isn't null" );
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        if (mView == null) {
            Log.d(TAG, "onCreateView: " + "view is null" );
        } else {
            Log.d(TAG, "onCreateView: view isn't null" );
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String string = bundle.getString("title", "default");
            TextView textView = (TextView) (mView.findViewById(R.id.text_view_title));
            textView.setText(string);
        }


        super.onStart();
    }

    @Override
    public void onResume() {
        if (mView == null) {
            Log.d(TAG, "onCreateView: " + "view is null" );
        } else {
            Log.d(TAG, "onCreateView: view isn't null" );
        }
        super.onResume();
    }

    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }*/
}
