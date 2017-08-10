package com.hzu.jpg.commonwork.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/3/3.
 */

public class OverTimeRecordPageAdapter extends FragmentPagerAdapter {

    Fragment[] fragments;

    public OverTimeRecordPageAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
