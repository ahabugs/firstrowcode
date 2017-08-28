package com.example.slidingpagertest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 17-8-26.
 */

/*
public class MyPagerAdapter extends FragmentPagerAdapter {
*/
    public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;

    /*public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }*/

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        fragmentList =  fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
