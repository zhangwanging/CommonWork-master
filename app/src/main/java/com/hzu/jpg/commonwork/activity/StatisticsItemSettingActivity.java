package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hzu.jpg.commonwork.Presenter.StatisticsItemSettingPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.enity.Bean.AllowanceBean;
import com.hzu.jpg.commonwork.enity.Bean.CutBean;
import com.hzu.jpg.commonwork.enity.moudle.StatisticsModel;


public class StatisticsItemSettingActivity extends AppCompatActivity {

    EditText et;
    TextView tv_title;
    TextView tv_name;
    Button bt_save;

    int type;
    String name;
    double money=0;
    String date;

    StatisticsItemSettingPresenter presenter;

    public static final int CHANGE_ALLOWANCE_ITEM=7;
    public static final int CHANGE_CUT_ITEM=8;
    public static final int CHANGE_HELP_PAYMENT=9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_item_setting);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        init();

        setBack();
    }
    public void init(){
        et= (EditText) findViewById(R.id.et_statistics_item_setting);
        tv_name= (TextView) findViewById(R.id.tv_statistics_item_setting_name);
        tv_title= (TextView) findViewById(R.id.tv_statistics_item_setting_title);
        bt_save= (Button) findViewById(R.id.bt_statistics_item_save);

        if(presenter==null){
            presenter=new StatisticsItemSettingPresenter(this);
        }

        final Intent intent=getIntent();
        name=intent.getStringExtra("name");
        money=intent.getDoubleExtra("money",0.0);
        date=intent.getStringExtra("date");
        type=intent.getIntExtra("type",0);
        if(type==0){
            finish();
            return;
        }
        tv_name.setText(name);
        tv_title.setText(name+"设置");
        if(money!=0){
            et.setText(Double.toString(money));
        }
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value=0;
                String s_money=et.getText().toString().trim();
                if(!s_money.equals("")){
                    value=Double.parseDouble(et.getText().toString());
                    intent.putExtra("money",Double.parseDouble(s_money));
                }else{
                    value=-1;
                    intent.putExtra("money",0.0);
                }
                if(type==CHANGE_ALLOWANCE_ITEM){
                    presenter.save(date,name,value,money, StatisticsModel.TABLE_ALLOWANCE,"allowance_total", AllowanceBean.class);
                    setResult(CHANGE_ALLOWANCE_ITEM,intent);
                    finish();
                }else if(type==CHANGE_CUT_ITEM) {
                    presenter.save(date,name,value,money, StatisticsModel.TABLE_CUT,"cut_total", CutBean.class);
                    setResult(CHANGE_CUT_ITEM,intent);
                    finish();
                }else if(type==CHANGE_HELP_PAYMENT){
                    if(value==-1) value=0;
                    presenter.saveHelpPayment(date,name,value,money);
                    setResult(CHANGE_HELP_PAYMENT,intent);
                    finish();
                }
            }
        });
    }
    public void setBack(){
        ImageButton ib= (ImageButton) findViewById(R.id.ib_statistics_item_setting_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
