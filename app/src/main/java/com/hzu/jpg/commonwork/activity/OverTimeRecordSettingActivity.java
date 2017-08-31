package com.hzu.jpg.commonwork.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.hzu.jpg.commonwork.HourWork.Activity.HourWorkSalarySettingActivity;
import com.hzu.jpg.commonwork.HourWork.Model.HourWorkAddRecordModel;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.moudle.AddOverTimeRecordModel;
import com.hzu.jpg.commonwork.fragment.OverTimeRecordFragment;
import com.hzu.jpg.commonwork.widgit.MyLinearLayout;


public class OverTimeRecordSettingActivity extends AppCompatActivity {

    MyLinearLayout llOverTimeSalarySetting;
    MyLinearLayout llAllowanceSetting;
    MyLinearLayout llCutSetting;
    MyLinearLayout llHelpPaymentSetting;
    MyLinearLayout llHourWorkSalarySetting;
    MyLinearLayout chongzhijiabantime;

    HourWorkAddRecordModel model;
    AddOverTimeRecordModel addModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ot_record_setting);
        model = new HourWorkAddRecordModel(this);
        addModel = new AddOverTimeRecordModel(this);
        llOverTimeSalarySetting = (MyLinearLayout) findViewById(R.id.ll_ot_record_setting_salary);
        llAllowanceSetting = (MyLinearLayout) findViewById(R.id.ll_ot_record_setting_allowance);
        llCutSetting = (MyLinearLayout) findViewById(R.id.ll_ot_record_setting_cut);
        llHelpPaymentSetting = (MyLinearLayout) findViewById(R.id.ll_ot_record_setting_help_pay);
        llHourWorkSalarySetting = (MyLinearLayout) findViewById(R.id.ll_ot_record_setting_hour_work);
        chongzhijiabantime = (MyLinearLayout) findViewById(R.id.chongzhijiaban_time_layout);

        if (Config.hourWork) {
            llHourWorkSalarySetting.setVisibility(View.VISIBLE);
            llOverTimeSalarySetting.setVisibility(View.GONE);
        } else {
            llHourWorkSalarySetting.setVisibility(View.GONE);
            llOverTimeSalarySetting.setVisibility(View.VISIBLE);
        }
        initView();
        setBack();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(OverTimeRecordFragment.OT_RECORD_SETTING);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initView() {
        llOverTimeSalarySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(OverTimeRecordSettingActivity.this, OverTimeSalarySettingActivity.class), 0);
            }
        });
        llAllowanceSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OverTimeRecordSettingActivity.this, OverTimeItemSettingActivity.class);
                intent.putExtra("title", "补贴设置");
                startActivityForResult(intent, 0);
            }
        });
        llCutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OverTimeRecordSettingActivity.this, OverTimeItemSettingActivity.class);
                intent.putExtra("title", "扣款设置");
                startActivityForResult(intent, 0);
            }
        });
        llHelpPaymentSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OverTimeRecordSettingActivity.this, OverTimeItemSettingActivity.class);
                intent.putExtra("title", "代缴设置");
                startActivityForResult(intent, 0);
            }
        });

        llHourWorkSalarySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverTimeRecordSettingActivity.this, HourWorkSalarySettingActivity.class));
            }
        });
        chongzhijiabantime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(OverTimeRecordSettingActivity.this)
                        .setTitle("提示")
                        .setMessage("确认重置加班时间")
                        .setCancelable(false)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                model.delete();
                                addModel.delete();
                            }
                        })
                        .setNegativeButton("取消", null).create().show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == OverTimeRecordFragment.CHANGE_OT_SETTING) {
            setResult(OverTimeRecordFragment.CHANGE_OT_SETTING);
            finish();
        }
    }

    public void setBack() {
        ImageButton ib = (ImageButton) findViewById(R.id.ib_ot_record_setting_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //系统返回时间也要加
                setResult(OverTimeRecordFragment.OT_RECORD_SETTING);
                finish();
            }
        });
    }
}
