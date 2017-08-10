package com.hzu.jpg.commonwork.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hzu.jpg.commonwork.Presenter.CalendarPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.OverTimeRecordActivity;
import com.hzu.jpg.commonwork.utils.TimeUtil;


/**
 * Created by Administrator on 2017/3/3.
 */

public class CalendarFragment extends Fragment {

    TextView tv_ot_salary;
    TextView tv_ot_hours;

    CalendarPresenter presenter;

    RecyclerView rv;
    ImageButton ibLast;
    ImageButton ibNext;
    TextView tvDate;

    TextView tvTitleSalary;
    TextView tvTitleHours;

    ImageButton ibBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView=inflater.inflate(R.layout.fragment_calendar,container,false);
        tv_ot_salary= (TextView) RootView.findViewById(R.id.tv_calendar_ot_salary);
        tv_ot_hours= (TextView) RootView.findViewById(R.id.tv_calendar_ot_hours);

        rv= (RecyclerView) RootView.findViewById(R.id.rv_calendar);
        ibLast= (ImageButton) RootView.findViewById(R.id.ib_calendar_last);
        ibNext= (ImageButton) RootView.findViewById(R.id.ib_calendar_next);
        tvDate= (TextView) RootView.findViewById(R.id.tv_calendar_date);
        tvTitleHours= (TextView) RootView.findViewById(R.id.tv_calendar_title_ot_hours);
        tvTitleSalary= (TextView) RootView.findViewById(R.id.tv_calendar_title_ot_salary);
        ibBack= (ImageButton) RootView.findViewById(R.id.ib_calendar_back);

        initView();
        presenter=new CalendarPresenter(this);
        presenter.setMonthSalary(TimeUtil.getDateYM());
        presenter.setCalendar(TimeUtil.getDateYM());
        return RootView;
    }

    public void initView(){
        ibLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLastClick();
            }
        });

        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onNextClick();
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OverTimeRecordActivity)getActivity()).toFirstItem();
            }
        });
    }

    public void setRv(RecyclerView.Adapter adapter){
        rv.setLayoutManager(new GridLayoutManager(getContext(),7));
        rv.setAdapter(adapter);
    }

    public void setOtSalary(String salary){
        tv_ot_salary.setText(salary);
    }

    public void setOtHours(String hours){
        tv_ot_hours.setText(hours);
    }

    public void setDate(String date){ tvDate.setText(date);}

    public void update(String date){
        presenter.setMonthSalary(date);
        presenter.setCalendar(date);
    }

    public String getDate(){
        return tvDate.getText().toString();
    }

    public void setTitle(String salary,String hours){
        tvTitleSalary.setText(salary);
        tvTitleHours.setText(hours);
    }


}
