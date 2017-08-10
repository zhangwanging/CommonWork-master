package com.hzu.jpg.commonwork.HourWork.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.hzu.jpg.commonwork.HourWork.Presenter.HourWorkAddRecordPresenter;
import com.hzu.jpg.commonwork.HourWork.Presenter.HourWorkMonthPresenter;
import com.hzu.jpg.commonwork.Presenter.AddRecordPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.AddOverTimeRecordActivity;
import com.hzu.jpg.commonwork.fragment.OverTimeRecordFragment;
import com.hzu.jpg.commonwork.utils.NumberPickerHandler;
import com.hzu.jpg.commonwork.utils.TimeUtil;

import static com.baidu.location.h.j.v;

public class HourWorkAddRecordActivity extends AppCompatActivity {

    Button bt_other_date;
    Button bt_save;

    TextView tv_delete;

    LinearLayout ll_top;
    TextView tv_top_week_day_before;
    TextView tv_top_week_yesterday;
    TextView tv_top_week_today;

    TextView tv_date;
    TextView tv_week;

    EditText etRemark;

    HourWorkAddRecordPresenter presenter;

    NumberPicker picker_hour;
    NumberPicker picker_minute;
    TextView tvSalaryPerHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_work_add_record);
        bt_other_date= (Button) findViewById(R.id.bt_hour_work_add_record_top_other_date);

        tv_delete= (TextView) findViewById(R.id.tv_hour_work_add_record_delete);

        ll_top= (LinearLayout) findViewById(R.id.ll_hour_work_add_record_top);
        tv_top_week_day_before= (TextView) findViewById(R.id.tv_hour_work_add_record_top_before_yesterday);
        tv_top_week_yesterday= (TextView) findViewById(R.id.tv_hour_work_add_record_top_yesterday);
        tv_top_week_today= (TextView) findViewById(R.id.tv_hour_work_add_record_top_today);

        tv_date= (TextView) findViewById(R.id.tv_hour_work_add_record_date);
        tv_week= (TextView) findViewById(R.id.tv_hour_work_add_record_week);
        etRemark= (EditText) findViewById(R.id.et_hour_work_add_record_remark);

        bt_save= (Button) findViewById(R.id.bt_hour_work_add_record_save);

        picker_hour= (NumberPicker) findViewById(R.id.np_hour_work_add_record_hour);
        picker_minute= (NumberPicker) findViewById(R.id.np_hour_work_add_record_minute);

        tvSalaryPerHour= (TextView) findViewById(R.id.tv_hour_work_add_record_salary_per_hour);

        int type=getIntent().getIntExtra("type",AddOverTimeRecordActivity.FROM_SALARY_MONTH);
        initView();
        presenter=new HourWorkAddRecordPresenter(this);
        if(type==AddOverTimeRecordActivity.FROM_SALARY_MONTH){
            initTop();
            presenter.setData(TimeUtil.getDateYMD());
        }else{
            ll_top.setVisibility(View.GONE);
            presenter.setData(getIntent().getStringExtra("date"));
        }

    }
    public void initView(){
        tv_date.setText(TimeUtil.getDateYMD());
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.save();
                Intent intent=getIntent();
                intent.putExtra("date",presenter.getDate().substring(0,presenter.getDate().length()-3));
                setResult(OverTimeRecordFragment.CHANGE_OT_RECORD,intent);
                finish();
            }
        });
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.delete();
                Intent intent=getIntent();
                intent.putExtra("date",presenter.getDate().substring(0,presenter.getDate().length()-3));
                setResult(OverTimeRecordFragment.CHANGE_OT_RECORD,intent);
                finish();
            }
        });
        initNumberPicker();
    }

    public void initNumberPicker(){
        NumberPickerHandler.setNumberPickerDividerColor(picker_hour);
        picker_hour.setMaxValue(23);
        picker_hour.setMinValue(0);
        picker_hour.setValue(0);
        picker_hour.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        NumberPickerHandler.setNumberPickerDividerColor(picker_minute);
        picker_minute.setMaxValue(59);
        picker_minute.setMinValue(0);
        picker_minute.setValue(0);
        picker_minute.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

    }

    public void cleanTop(){
        tv_top_week_today.setBackgroundResource(R.drawable.button_ot_record_normal);
        tv_top_week_today.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_top_week_yesterday.setBackgroundResource(R.drawable.button_ot_record_normal);
        tv_top_week_yesterday.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv_top_week_day_before.setBackgroundResource(R.drawable.button_ot_record_normal);
        tv_top_week_day_before.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    public void setDate(String date){
        tv_date.setText(date);
    }

    public void setWeek(String week){
        if(week==null||week.isEmpty()){
            tv_week.setText(TimeUtil.getWeek(0));
        }else{
            tv_week.setText(week);
        }
    }
    public void initTop() {
        bt_other_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(AddOverTimeRecordActivity.TO_CALENDAR);
                finish();
            }
        });

        tv_top_week_day_before.setText("前天\n"+TimeUtil.getWeek(-2));
        tv_top_week_yesterday.setText("昨天\n"+TimeUtil.getWeek(-1));
        tv_top_week_today.setText("今天\n"+TimeUtil.getWeek(0));

        tv_top_week_today.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tv_top_week_today.setTextColor(getResources().getColor(R.color.colorWhite));

        tv_top_week_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanTop();
                tv_top_week_today.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tv_top_week_today.setTextColor(getResources().getColor(R.color.colorWhite));
                presenter.setData(TimeUtil.getDateYMD());
                setWeek(TimeUtil.getWeek(0));
            }
        });

        tv_top_week_yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanTop();
                tv_top_week_yesterday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tv_top_week_yesterday.setTextColor(getResources().getColor(R.color.colorWhite));
                presenter.setData(TimeUtil.getDateYMD(-1));
                setWeek(TimeUtil.getWeek(-1));
            }
        });

        tv_top_week_day_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanTop();
                tv_top_week_day_before.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tv_top_week_day_before.setTextColor(getResources().getColor(R.color.colorWhite));
                presenter.setData(TimeUtil.getDateYMD(-2));
                setWeek(TimeUtil.getWeek(-2));
            }
        });
    }

    public String getDate(){
        return tv_date.getText().toString();
    }

    public int getHours(){
        return picker_hour.getValue();
    }
    public int getMinutes(){
        return picker_minute.getValue();
    }

    public String getRemark(){
        return etRemark.getText().toString().trim();
    }

    public String getSalaryPerHour(){
        return tvSalaryPerHour.getText().toString();
    }

    public void setHour(int value){
        picker_hour.setValue(value);
    }

    public void setMinute(int value){
        picker_minute.setValue(value);
    }
    public void setRemark(String value){
        etRemark.setText(value);
    }
    public String getWeek(){ return tv_week.getText().toString();}

    public void setSalary(String data){
        tvSalaryPerHour.setText(data);
    }

    public void hideDelete(boolean hide){
        if (hide){
            tv_delete.setVisibility(View.GONE);
        }else {
            tv_delete.setVisibility(View.VISIBLE);
        }
    }


}
