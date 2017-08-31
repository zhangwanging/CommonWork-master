package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyFindPagerAdapter;
import com.hzu.jpg.commonwork.fragment.MyListJobFragment;
import com.hzu.jpg.commonwork.fragment.MyOneKeyJobFragment;


public class MyFindActivity extends AppCompatActivity {


    private MyFindPagerAdapter myFindPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_find);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initFragment();
        initViewPage();
        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initFragment() {
        Fragment[] fragments = new Fragment[2];
        fragments[0] = new MyOneKeyJobFragment();
        fragments[1] = new MyListJobFragment();
        myFindPagerAdapter = new MyFindPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"一键求职记录", "投递记录"});
    }

    public void initViewPage() {
        mViewPager = (ViewPager) findViewById(R.id.vp_my_find);
        mViewPager.setAdapter(myFindPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_my_find);
        tabLayout.setupWithViewPager(mViewPager);
    }
}
