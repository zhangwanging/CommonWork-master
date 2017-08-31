package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hzu.jpg.commonwork.HourWork.Fragment.HourWorkMainFragment;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.OverTimeRecordPageAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.fragment.CalendarFragment;
import com.hzu.jpg.commonwork.fragment.OverTimeRecordFragment;
import com.hzu.jpg.commonwork.fragment.StatisticsFragment;


public class OverTimeRecordActivity extends AppCompatActivity {

    TextView tv_ot;
    TextView tv_calendar;
    TextView tv_statistics;
    ViewPager viewPager;
    OverTimeRecordFragment overTimeRecordFragment;
    HourWorkMainFragment hourWorkMainFragment;
    CalendarFragment calendarFragment;
    StatisticsFragment statisticsFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ot_record);
        init();
    }

    private void init(){
        tv_ot= (TextView) findViewById(R.id.tab_tv_ot_record_ot);
        tv_calendar= (TextView) findViewById(R.id.tab_tv_ot_record_calendar);
        tv_statistics= (TextView) findViewById(R.id.tab_tv_ot_record_statistics);

        Drawable drawable=getResources().getDrawable(R.mipmap.ot_money_selected);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tv_ot.setCompoundDrawables(null,drawable,null,null);
        tv_ot.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        viewPager= (ViewPager) findViewById(R.id.vp_ot_record);
        setViewPagerData();

        tv_ot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setCurrentItem(0);
                viewPager.setCurrentItem(0);

            }
        });
        tv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentItem(1);
                viewPager.setCurrentItem(1);
            }
        });

        tv_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentItem(2);viewPager.setCurrentItem(2);
            }
        });
    }
    private void setViewPagerData(){
        calendarFragment=new CalendarFragment();
        statisticsFragment=new StatisticsFragment();
        Fragment[] fragments=new Fragment[3];
        if(Config.hourWork){
            hourWorkMainFragment=new HourWorkMainFragment();
            fragments[0]=hourWorkMainFragment;
        }else{
            overTimeRecordFragment=new OverTimeRecordFragment();
            fragments[0]= overTimeRecordFragment;
        }
        fragments[1]= calendarFragment;
        fragments[2]= statisticsFragment;
        OverTimeRecordPageAdapter adapter=new OverTimeRecordPageAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int currentPosition=-1;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(currentPosition==-1){
                    currentPosition=position;
                    return;
                }
                if(currentPosition!=position){
                    setCurrentItem(position);
                }
                currentPosition=position;

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void cleanTab(){
        Drawable drawable_ot=getResources().getDrawable(R.mipmap.ot_money);
        drawable_ot.setBounds(0,0,drawable_ot.getMinimumWidth(),drawable_ot.getMinimumHeight());
        tv_ot.setCompoundDrawables(null,drawable_ot,null,null);
        tv_ot.setTextColor(getResources().getColor(R.color.colorDeepHui));

        Drawable drawable_calendar=getResources().getDrawable(R.mipmap.calendar);
        drawable_calendar.setBounds(0,0,drawable_calendar.getMinimumWidth(),drawable_calendar.getMinimumHeight());
        tv_calendar.setCompoundDrawables(null,drawable_calendar,null,null);
        tv_calendar.setTextColor(getResources().getColor(R.color.colorDeepHui));

        Drawable drawable_statistics=getResources().getDrawable(R.mipmap.statistics);
        drawable_statistics.setBounds(0,0,drawable_statistics.getMinimumWidth(),drawable_statistics.getMinimumHeight());
        tv_statistics.setCompoundDrawables(null,drawable_statistics,null,null);
        tv_statistics.setTextColor(getResources().getColor(R.color.colorDeepHui));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== OverTimeRecordFragment.CHANGE_OT_RECORD){
            setCurrentItem(0);
            viewPager.setCurrentItem(0);

            String date=data.getStringExtra("date");
            if(Config.hourWork){
                hourWorkMainFragment.update();
            }else{
                overTimeRecordFragment.update();
            }
            calendarFragment.update(date);
        }else if(resultCode==AddOverTimeRecordActivity.TO_CALENDAR){
            setCurrentItem(1);
            viewPager.setCurrentItem(1);
        }
    }

    public void setCurrentItem(int i){
        cleanTab();
        switch (i){
            case 0:
                Drawable drawable=getResources().getDrawable(R.mipmap.ot_money_selected);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                tv_ot.setCompoundDrawables(null,drawable,null,null);
                tv_ot.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case 1:
                drawable=getResources().getDrawable(R.mipmap.canlendar_selected);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                tv_calendar.setCompoundDrawables(null,drawable,null,null);
                tv_calendar.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case 2:
                drawable=getResources().getDrawable(R.mipmap.statistics_selected);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                tv_statistics.setCompoundDrawables(null,drawable,null,null);
                tv_statistics.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
        }

    }
    public void toFirstItem(){
        setCurrentItem(0);
        viewPager.setCurrentItem(0);
    }
}
