package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyFindPagerAdapter;
import com.hzu.jpg.commonwork.fragment.CompanyJobsFragment;
import com.hzu.jpg.commonwork.fragment.CompanyOneKeyJobFragment;


public class CompanyPublishActivity extends AppCompatActivity {


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_job);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.vp_company_job);

        CompanyOneKeyJobFragment companyOneKeyJobFragment=new CompanyOneKeyJobFragment();
        CompanyJobsFragment companyJobsFragment=new CompanyJobsFragment();

        Fragment[] fragments=new Fragment[2];
        fragments[0]=companyOneKeyJobFragment;
        fragments[1]=companyJobsFragment;

        MyFindPagerAdapter adapter=new MyFindPagerAdapter(getSupportFragmentManager(),fragments,new String[]{"一键招聘记录","发布招聘记录"});
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_company_job);
        tabLayout.setupWithViewPager(mViewPager);

    }
}
