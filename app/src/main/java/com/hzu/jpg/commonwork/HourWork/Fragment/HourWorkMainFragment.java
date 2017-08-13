package com.hzu.jpg.commonwork.HourWork.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hzu.jpg.commonwork.HourWork.Activity.HourWorkAddRecordActivity;
import com.hzu.jpg.commonwork.HourWork.Presenter.HourWorkMonthPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.OverTimeRecordSettingActivity;
import com.hzu.jpg.commonwork.adapter.HourWorkRvAdapter;
import com.hzu.jpg.commonwork.fragment.OverTimeRecordFragment;

/**
 * Created by Administrator on 2017/5/11.
 */

public class HourWorkMainFragment extends Fragment {

    Button btAddRecord;

    TextView tvTotalSalary;
    TextView tvBasicSalary;
    TextView tvHours;
    TextView tvDate;

    ImageButton ibSetting;
    ImageButton ibBack;
    ListView mListHourWork;

    HourWorkMonthPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hour_work_main, container, false);
        btAddRecord= (Button) rootView.findViewById(R.id.bt_hour_work_add_record);
        tvBasicSalary= (TextView) rootView.findViewById(R.id.tv_hour_work_record_basic_salary);
        tvDate= (TextView) rootView.findViewById(R.id.tv_hour_work_record_work_date);
        tvHours= (TextView) rootView.findViewById(R.id.tv_hour_work_record_hours);
        tvTotalSalary= (TextView) rootView.findViewById(R.id.tv_hour_work__record_total_salary);
        ibSetting= (ImageButton) rootView.findViewById(R.id.ib_hour_work_record_setting);
        ibBack= (ImageButton) rootView.findViewById(R.id.ib_hour_work_record_back);
        mListHourWork = (ListView) rootView.findViewById(R.id.listHourWork);

        btAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(HourWorkMainFragment.this.getActivity(), HourWorkAddRecordActivity.class),0);
            }
        });

        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), OverTimeRecordSettingActivity.class),OverTimeRecordFragment.OT_RECORD_SETTING);
            }
        });
        setBack();
        presenter=new HourWorkMonthPresenter(this);
        presenter.initData();
        return rootView;
    }

    public void setHours(String data){
        tvHours.setText(data);
    }
    public void setTotalSalary(String data){
        tvTotalSalary.setText(data);
    }

    public void setBasicSalary(String data){
        tvBasicSalary.setText(data);
    }

    public void setDate(String data){
        tvDate.setText(data);
    }

    public void update(){
        presenter.onUpdate();
    }

    public void setRv(HourWorkRvAdapter adapter) {
        mListHourWork.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case OverTimeRecordFragment.CHANGE_OT_RECORD:
                update();
                break;
            case OverTimeRecordFragment.CHANGE_OT_SETTING:
                update();
                break;

        }
    }

    public void setBack(){
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}
