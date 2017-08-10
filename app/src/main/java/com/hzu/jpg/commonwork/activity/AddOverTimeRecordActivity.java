package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.hzu.jpg.commonwork.Presenter.AddRecordPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.DialogRvListAdapter;
import com.hzu.jpg.commonwork.fragment.OverTimeRecordFragment;
import com.hzu.jpg.commonwork.utils.NumberPickerHandler;
import com.hzu.jpg.commonwork.utils.TimeUtil;
import com.hzu.jpg.commonwork.widgit.MyListDialog;
import com.hzu.jpg.commonwork.widgit.MyPickerDialog;


public class AddOverTimeRecordActivity extends AppCompatActivity {

    Button bt_other_date;
    Button bt_save;

    LinearLayout ll_basic_work_time;
    LinearLayout ll_ot_salary;

    TextView tv_delete;

    LinearLayout ll_top;
    TextView tv_top_week_day_before;
    TextView tv_top_week_yesterday;
    TextView tv_top_week_today;

    TextView tv_date;
    TextView tv_week;

    TextView tvBasicHours;
    TextView tvBasicMinutes;
    TextView tvOtSalaryPerHour;
    TextView tvWorkType;
    TextView tvMultiple;
    EditText tvRemark;

    AddRecordPresenter presenter;

    NumberPicker picker_hour;
    NumberPicker picker_minute;

    public static final int FROM_CALENDAR=10;
    public static final int FROM_SALARY_MONTH=11;
    public static final int TO_CALENDAR=13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ot_record);
        presenter=new AddRecordPresenter(this);
        findView();
        Intent intent=getIntent();
        int type=intent.getIntExtra("type",FROM_SALARY_MONTH);
        initView();
        if(type==FROM_SALARY_MONTH){
            initTop();
            presenter.setData(TimeUtil.getDateYMD());
        }else{
            ll_top.setVisibility(View.GONE);
            presenter.setData(intent.getStringExtra("date"));
        }

        setBack();

    }
    public void findView(){
        bt_other_date= (Button) findViewById(R.id.bt_add_ot_record_top_other_date);

        ll_basic_work_time=(LinearLayout) findViewById(R.id.ll_add_ot_record_basic_work_time);

        ll_ot_salary=(LinearLayout) findViewById(R.id.ll_add_ot_record_ot_salary);

        tv_delete= (TextView) findViewById(R.id.tv_add_ot_record_delete);

        ll_top= (LinearLayout) findViewById(R.id.ll_add_ot_record_top);
        tv_top_week_day_before= (TextView) findViewById(R.id.tv_add_ot_record_top_before_yesterday);
        tv_top_week_yesterday= (TextView) findViewById(R.id.tv_add_ot_record_top_yesterday);
        tv_top_week_today= (TextView) findViewById(R.id.tv_add_ot_record_top_today);

        tv_date= (TextView) findViewById(R.id.tv_add_ot_record_date);
        tv_week= (TextView) findViewById(R.id.tv_add_ot_record_week);
        tvBasicHours= (TextView) findViewById(R.id.tv_add_ot_record_basic_hours);
        tvBasicMinutes= (TextView) findViewById(R.id.tv_add_ot_record_basic_minutes);
        tvOtSalaryPerHour= (TextView) findViewById(R.id.tv_add_ot_record_salary_per_hour);
        tvWorkType= (TextView) findViewById(R.id.tv_add_ot_record_work_type);
        tvMultiple= (TextView) findViewById(R.id.tv_add_ot_record_multiply);

        tvRemark= (EditText) findViewById(R.id.tv_add_ot_record_remark);

        bt_save= (Button) findViewById(R.id.bt_add_ot_record_save);

        picker_hour= (NumberPicker) findViewById(R.id.np_add_ot_record_hour);
        picker_minute= (NumberPicker) findViewById(R.id.np_add_ot_record_minute);
    }

    public void initView(){

        ll_basic_work_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPickerDialog.Builder builder=new MyPickerDialog.Builder(AddOverTimeRecordActivity.this);
                builder.setTitle("请设置应出勤时间");
                builder.setTime(Integer.parseInt(tvBasicHours.getText().toString()),Integer.parseInt(tvBasicMinutes.getText().toString()));
                builder.setOnDialogClickListener(new MyPickerDialog.Builder.OnDialogClickListener() {
                    @Override
                    public void OnPositiveClick(MyPickerDialog dialog, int hours, int minutes) {
                        tvBasicHours.setText(""+hours);
                        tvBasicMinutes.setText(""+minutes);
                        dialog.dismiss();
                    }

                    @Override
                    public void OnCancelClick(MyPickerDialog dialog) {
                        dialog.dismiss();
                    }
                });
                Display display=AddOverTimeRecordActivity.this.getWindowManager().getDefaultDisplay();
                builder.setDisplay(display);
                builder.create();
            }
        });
        ll_ot_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] names=presenter.getSetting(TimeUtil.getDateYM());
                MyListDialog.Builder builder=new MyListDialog.Builder(AddOverTimeRecordActivity.this);
                builder.setTitle("请设置加班类型");
                builder.setNames(names);
                Display display=AddOverTimeRecordActivity.this.getWindowManager().getDefaultDisplay();
                builder.setDisplay(display);
                builder.create();
                builder.setOnItemClickListener(new DialogRvListAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(String data) {
                        String salary=data.substring(0,data.indexOf("元"));
                        String workType=data.substring(data.indexOf("(")+1,data.indexOf("日")+1);
                        String multiply=data.substring(data.indexOf("日")+1,data.indexOf("倍"));
                        Log.d("data",salary+workType+multiply);
                        setOT_Salary_per_hour(salary);
                        setWorkType(workType);
                        setMultiply(multiply);
                    }
                });
            }
        });

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

    public void initTop() {
        bt_other_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(TO_CALENDAR);
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

    public void setBasicTime(String sHours,String sMinutes){
        tvBasicHours.setText(sHours);
        tvBasicMinutes.setText(sMinutes);
    }

    public void setOverTimeHours(int hour){
        picker_hour.setValue(hour);
    }

    public void setOverTimeMinutes(int minutes){
        picker_minute.setValue(minutes);
    }

    public void setOT_Salary_per_hour(String salary_per_hour){
        tvOtSalaryPerHour.setText(salary_per_hour);
    }



    public String getWeek(){
        return tv_week.getText().toString();
    }

    public int getBasicHours(){ return Integer.parseInt(tvBasicHours.getText().toString());}

    public int getBasicMinutes(){ return Integer.parseInt(tvBasicMinutes.getText().toString());}

    public int getOverTimeHour(){
        return picker_hour.getValue();
    }

    public int getOverTimeMinute(){
        return picker_minute.getValue();
    }

    public  String getSalaryPerHour(){
        return tvOtSalaryPerHour.getText().toString();
    }

    public String getWorkType(){ return tvWorkType.getText().toString(); }

    public void setWorkType(String workType){ tvWorkType.setText(workType);}

    public String getMultiply(){ return tvMultiple.getText().toString();}

    public void setMultiply(String multiply){ tvMultiple.setText(multiply);}

    public String getRemark(){
        return tvRemark.getText().toString();
    }

    public void setRemark(String remark){
        tvRemark.setText(remark);
    }

    public void hideDelete(boolean hide){
        if (hide){
            tv_delete.setVisibility(View.GONE);
        }else {
            tv_delete.setVisibility(View.VISIBLE);
        }
    }

    public void setBack(){
        ImageButton ib= (ImageButton) findViewById(R.id.ib_add_ot_record_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
