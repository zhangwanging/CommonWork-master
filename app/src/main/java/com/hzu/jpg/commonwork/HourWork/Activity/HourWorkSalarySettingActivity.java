package com.hzu.jpg.commonwork.HourWork.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.utils.SharedPreferencesUtil;

public class HourWorkSalarySettingActivity extends AppCompatActivity {

    EditText etSalary;
    ImageButton ibBack;
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_work_salary_setting);
        etSalary= (EditText) findViewById(R.id.et_hour_work_salary_setting);
        ibBack= (ImageButton) findViewById(R.id.ib_hour_work_salary_setting_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btSave= (Button) findViewById(R.id.bt_hour_work_salary_setting_save);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.setSalarySetting(etSalary.getText().toString());
                finish();
            }
        });

    }
}
