package com.hzu.jpg.commonwork.HourWork.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.OverTimeRecordActivity;
import com.hzu.jpg.commonwork.app.Config;

public class ChooseOverTimeRecordActivity extends AppCompatActivity {

    LinearLayout llHourWork;
    LinearLayout llMonthWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        llHourWork= (LinearLayout) findViewById(R.id.ll_choose_ot_hour_work);
        llMonthWork= (LinearLayout) findViewById(R.id.ll_choose_ot_month_work);
        llMonthWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.hourWork=false;
                startActivity(new Intent(ChooseOverTimeRecordActivity.this, OverTimeRecordActivity.class));
            }
        });
        llHourWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.hourWork=true;
                startActivity(new Intent(ChooseOverTimeRecordActivity.this, OverTimeRecordActivity.class));
            }
        });
        ImageButton ibBack= (ImageButton) findViewById(R.id.ib_choose_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
