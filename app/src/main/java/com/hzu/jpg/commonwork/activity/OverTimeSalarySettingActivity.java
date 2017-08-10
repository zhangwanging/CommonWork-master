package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hzu.jpg.commonwork.Presenter.OverTimeSalarySettingPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.fragment.OverTimeRecordFragment;
import com.hzu.jpg.commonwork.widgit.MyTextWatcher;


public class OverTimeSalarySettingActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    EditText etBasicSalary;
    EditText etSalaryPerHour;
    TextView etNormalMultiply;
    TextView etNormalSalary;
    TextView etWeekendMultiply;
    TextView etWeekendSalary;
    TextView etFestivalMultiply;
    TextView etFestivalSalary;

    Button btSave;

    int onFocusID = -1;

    OverTimeSalarySettingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_time_salary_setting);
        etBasicSalary = (EditText) findViewById(R.id.et_ot_setting_basic_salary);

        etSalaryPerHour = (EditText) findViewById(R.id.et_ot_setting_salary_per_hour);
        etFestivalMultiply = (TextView) findViewById(R.id.et_ot_setting_multiply_festival);
        etFestivalSalary = (TextView) findViewById(R.id.et_ot_setting_salary_festival);
        etNormalMultiply = (TextView) findViewById(R.id.et_ot_setting_multiply_normal);
        etNormalSalary = (TextView) findViewById(R.id.et_ot_setting_salary_normal);
        etWeekendMultiply = (TextView) findViewById(R.id.et_ot_setting_multiply_weekend);
        etWeekendSalary = (TextView) findViewById(R.id.et_ot_setting_salary_weekend);


        btSave = (Button) findViewById(R.id.bt_ot_salary_setting_save);

        presenter = new OverTimeSalarySettingPresenter(this);

        initData();

        initView();

        setBack();
    }

    public void initData() {
        presenter.setData();
    }

    public void initView() {
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveData();
                setResult(OverTimeRecordFragment.CHANGE_OT_SETTING);
                finish();
            }
        });


        etBasicSalary.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onFocusID == etBasicSalary.getId()) {
                    double d=0;
                    if(!s.toString().equals("")){
                        d = Double.parseDouble(s.toString());
                    }else{
                        etBasicSalary.setText("0.0");
                    }
                    presenter.onBasicSalaryChange(d);
                }
            }
        });
        etSalaryPerHour.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onFocusID == etSalaryPerHour.getId()) {
                    double d=0;
                    if(!s.toString().equals("")){
                        d = Double.parseDouble(s.toString());
                    }else{
                        etSalaryPerHour.setText("0.0");
                    }
                    presenter.onSalaryPerHourChange(d);
                }
            }
        });
        etNormalMultiply.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onFocusID == etNormalMultiply.getId()) {
                    double d=0;
                    if(!s.toString().equals("")){
                        d = Double.parseDouble(s.toString());
                    }else{
                        etNormalMultiply.setText("0.0");
                    }
                    setNormal_salary(presenter.calculateByMultiply(d));
                }
            }
        });
        etFestivalSalary.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onFocusID == etFestivalSalary.getId()) {
                    double d=0;
                    if(!s.toString().equals("")){
                        d = Double.parseDouble(s.toString());
                    }else{
                        etFestivalSalary.setText("0.0");
                    }

                    setFestival_multiply(presenter.calculateBySalary(d));
                }
            }
        });
        etFestivalMultiply.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onFocusID == etFestivalMultiply.getId()) {
                    double d=0;
                    if(!s.toString().equals("")){
                        d = Double.parseDouble(s.toString());
                    }else{
                        etFestivalMultiply.setText("0.0");
                    }
                    setFestival_salary(presenter.calculateByMultiply(d));
                }
            }
        });
        etNormalSalary.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onFocusID == etNormalSalary.getId()) {
                    double d=0;
                    if(!s.toString().equals("")){
                        d = Double.parseDouble(s.toString());
                    }else{
                        etNormalSalary.setText("0.0");
                    }
                    setNormal_multiply(presenter.calculateBySalary(d));
                }
            }
        });
        etWeekendMultiply.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onFocusID == etWeekendMultiply.getId()) {
                    double d=0;
                    if(!s.toString().equals("")){
                        d = Double.parseDouble(s.toString());
                    }else{
                        etWeekendMultiply.setText("0.0");
                    }
                    setWeekend_salary(presenter.calculateByMultiply(d));
                }
            }
        });
        etWeekendSalary.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onFocusID == etWeekendSalary.getId()) {
                    double d=0;
                    if(!s.toString().equals("")){
                        d = Double.parseDouble(s.toString());
                    }else{
                        etWeekendSalary.setText("0.0");
                    }
                    setWeekend_multiply(presenter.calculateBySalary(d));
                }
            }
        });
//        LlWorkPeriod.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyListDialog.Builder builder=new MyListDialog.Builder(OverTimeSalarySettingActivity.this);
//                String[] strings=new String[28];
//                for(int i=0;i<28;i++){
//
//                    if(i==0){
//                        strings[i]="1号-本月底";
//                    }else{
//                        StringBuffer sb=new StringBuffer();
//                        sb.append(i+1).append("号-下月").append(i).append("号");
//                        strings[i]=(sb.toString());
//                    }
//                }
//                builder.setTitle("选择月考勤周期");
//                builder.setNames(strings);
//                builder.setDisplay(OverTimeSalarySettingActivity.this.getWindowManager().getDefaultDisplay());
//                builder.create();
//                builder.setOnItemClickListener(new DialogRvListAdapter.onItemClickListener() {
//                    @Override
//                    public void onItemClick(String data) {
//                        LlWorkPeriod.setContent(data);
//                    }
//                });
//            }
//        });

        etBasicSalary.setOnFocusChangeListener(this);
        etSalaryPerHour.setOnFocusChangeListener(this);

        etNormalMultiply.setOnFocusChangeListener(this);
        etWeekendMultiply.setOnFocusChangeListener(this);
        etFestivalMultiply.setOnFocusChangeListener(this);
        etFestivalSalary.setOnFocusChangeListener(this);
        etNormalSalary.setOnFocusChangeListener(this);
        etWeekendSalary.setOnFocusChangeListener(this);

    }

    public void setBasic_salary(String s) {
        etBasicSalary.setText(s);
    }

    public void setFestival_salary(String s) {
        etFestivalSalary.setText(s);
    }

    public void setFestival_multiply(String s) {
        etFestivalMultiply.setText(s);
    }

    public void setNormal_multiply(String s) {
        etNormalMultiply.setText(s);
    }

    public void setNormal_salary(String s) {
        etNormalSalary.setText(s);
    }

    public void setWeekend_multiply(String s) {
        etWeekendMultiply.setText(s);
    }

    public void setWeekend_salary(String s) {
        etWeekendSalary.setText(s);
    }

    public void setSalary_per_hour(String s) {
        etSalaryPerHour.setText(s);
    }

    public String getBasic_salary() {
        //return "0";
        return etBasicSalary.getText().toString();
    }

    public String getFestival_salary() {
        return etFestivalSalary.getText().toString();
    }

    public String getFestival_multiply() {
        return etFestivalMultiply.getText().toString();
    }

    public String getNormal_multiply() {
        return etNormalMultiply.getText().toString();
    }

    public String getNormal_salary() {
        return etNormalSalary.getText().toString();
    }

    public String getWeekend_multiply() {
        return etWeekendMultiply.getText().toString();
    }

    public String getWeekend_salary() {
        return etWeekendSalary.getText().toString();
    }

    public String getSalary_per_hour() {
        return etSalaryPerHour.getText().toString();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        synchronized (this){
            if (hasFocus) {
                onFocusID = v.getId();
            }
        }
    }

    public void setBack(){
        ImageButton ib= (ImageButton) findViewById(R.id.ib_over_time_salary_setting_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
