package com.hzu.jpg.commonwork.Presenter;

import android.content.Intent;


import com.hzu.jpg.commonwork.HourWork.Activity.HourWorkAddRecordActivity;
import com.hzu.jpg.commonwork.activity.AddOverTimeRecordActivity;
import com.hzu.jpg.commonwork.adapter.CalendarAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;
import com.hzu.jpg.commonwork.enity.moudle.CalendarModel;
import com.hzu.jpg.commonwork.fragment.CalendarFragment;
import com.hzu.jpg.commonwork.utils.TimeUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/3/12.
 */

public class CalendarPresenter {

    CalendarModel model;
    private CalendarFragment fragment;

    CalendarAdapter adapter;
    private Map<Integer,Double> map;

    private int position;
    private String date;



    public CalendarPresenter(CalendarFragment fragment){
        this.fragment= fragment;
        model=new CalendarModel(fragment.getContext());
        date= TimeUtil.getDateYM();
    }

    public void setMonthSalary(String date){
        fragment.setDate(date);
        OverTimeRecordMonthBean bean=model.getMonthSalary(date);
        if(Config.hourWork){
            fragment.setTitle("月考勤收入","月考勤時間");
        }
        if(bean==null){
            fragment.setOtHours("0.0");
            fragment.setOtSalary("0.0");
            return ;
        }
        fragment.setOtSalary(Double.toString(bean.getOt_salary()));
        fragment.setOtHours(Double.toString(bean.getOt_hours()));
    }

    public void setCalendar(String date){
        if(map==null){
            map=new HashMap<>();
        }else {
            map.clear();
        }
        if (Config.hourWork){
            model.getHourWorkBean(date,map);
        }else{
            model.getDayBeans(date,map);
        }
        int[] days=getDays(date);
        double[] hours=new double[42];

        for (Map.Entry entry:map.entrySet()){
            hours[position+(int)entry.getKey()-1]= (double) entry.getValue();
        }

        if(adapter==null){
            adapter=new CalendarAdapter(days,hours,fragment.getContext());
            fragment.setRv(adapter);
            adapter.setOnItemClickListener(new CalendarAdapter.OnDayChangeListener() {
                @Override
                public void onDayChange(String day) {
                    if(day.equals("")||Integer.parseInt(day)<=0){
                        return;
                    }
                    String date=fragment.getDate();
                    Intent intent;
                    if(Config.hourWork)
                        intent=new Intent(fragment.getActivity(), HourWorkAddRecordActivity.class);
                    else
                        intent=new Intent(fragment.getActivity(), AddOverTimeRecordActivity.class);
                    intent.putExtra("type",AddOverTimeRecordActivity.FROM_CALENDAR);
                    String date_ymd=date+"-"+day;
                    intent.putExtra("date",date_ymd);
                    fragment.getActivity().startActivityForResult(intent,0);
                }
            });
        }else{
            adapter.update(days,hours);
        }
    }

    public int[] getDays(String date){
        int[] days=new int[42];
        String month=date.substring(date.length()-2,date.length());
        String year=date.substring(0,4);
        position= TimeUtil.getWeek(year,month);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Integer.parseInt(year),Integer.parseInt(month)-1,1);
        days[position]=1;
        for (int i=position+1;i<days.length;i++){
            calendar.add(Calendar.DAY_OF_MONTH,1);
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            if(day==1){
                break;
            }else{
                days[i]=day;
            }
        }
        return days;
    }

    public void onLastClick(){
        date=TimeUtil.getDateYM(date,-1);
        setCalendar(date);
        fragment.setDate(date);
        setMonthSalary(date);
    }

    public void onNextClick(){
        date=TimeUtil.getDateYM(date,1);
        setCalendar(date);
        fragment.setDate(date);
        setMonthSalary(date);
    }
}
