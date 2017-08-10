package com.hzu.jpg.commonwork.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hzu.jpg.commonwork.Presenter.OverTimeRecordPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.AddOverTimeRecordActivity;
import com.hzu.jpg.commonwork.activity.OverTimeRecordActivity;
import com.hzu.jpg.commonwork.activity.OverTimeRecordSettingActivity;


/**
 * Created by Administrator on 2017/3/3.
 */

public class OverTimeRecordFragment extends Fragment {

    ImageButton ib_setting;
    ImageButton ib_back;
    TextView tv_salary;
    TextView tv_date;
    TextView tv_ot_hours;
    TextView tv_ot_salary;
    RecyclerView rv;
    Button button;

    OverTimeRecordPresenter presenter;

    public final static int ADD_OT_RECORD=1;
    public final static int OT_RECORD_SETTING=2;

    public final static int CHANGE_OT_RECORD=3;
    public final static int CHANGE_OT_SETTING=4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ot, container, false);
        ib_setting= (ImageButton) rootView.findViewById(R.id.ib_ot_record_setting);
        tv_date= (TextView) rootView.findViewById(R.id.tv_ot_record_work_date);
        tv_ot_hours= (TextView) rootView.findViewById(R.id.tv_ot_record_ot_hours);
        tv_salary= (TextView) rootView.findViewById(R.id.tv_ot_record_this_month_salary);
        tv_ot_salary= (TextView) rootView.findViewById(R.id.tv_ot_record_ot_salary);
        rv= (RecyclerView) rootView.findViewById(R.id.rv_ot_record);
        button= (Button) rootView.findViewById(R.id.bt_ot_record);
        ib_back= (ImageButton) rootView.findViewById(R.id.ib_ot_record_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddOverTimeRecordActivity.class);
                intent.putExtra("type",AddOverTimeRecordActivity.FROM_SALARY_MONTH);
                getActivity().startActivityForResult(intent,ADD_OT_RECORD);
            }
        });

        ib_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), OverTimeRecordSettingActivity.class),OT_RECORD_SETTING);
            }
        });
        presenter=new OverTimeRecordPresenter(this);
        presenter.init();
        setBack();
        return rootView;
    }

    public void update(){
        presenter.onUpdate();
    }

    public void setSalary(String salary){
        tv_salary.setText(salary);
    }

    public void setOT_Salary(String ot_salary){
        tv_ot_salary.setText(ot_salary);
    }

    public void setDate(String date){
        tv_date.setText(date);
    }

    public void setOT_Hours(String hours){
        tv_ot_hours.setText(hours);
    }

    public void setRv(RecyclerView.Adapter adapter){
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case CHANGE_OT_RECORD:
                presenter.onUpdate();
                break;
            case CHANGE_OT_SETTING:
                presenter.onUpdate();
                break;

        }
    }

    public void setBack(){
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}
