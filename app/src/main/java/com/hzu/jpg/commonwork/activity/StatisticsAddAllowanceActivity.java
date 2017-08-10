package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hzu.jpg.commonwork.Presenter.StatisticsAddAllowancePresenter;
import com.hzu.jpg.commonwork.R;


public class StatisticsAddAllowanceActivity extends AppCompatActivity {

    TextView tv_title;
    RecyclerView rv;
    Button bt_save;

    String date;

    StatisticsAddAllowancePresenter presenter;

    public final static int ADD_ALLOWANCE=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_add);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if (presenter == null) {
            presenter=new StatisticsAddAllowancePresenter(this);
        }
        Intent intent=getIntent();
        date=intent.getStringExtra("date");
        init();

        setBack();
    }

    public void init(){
        tv_title= (TextView) findViewById(R.id.tv_statistics_add);
        rv= (RecyclerView) findViewById(R.id.rv_statistics_add);
        bt_save= (Button) findViewById(R.id.bt_statistics_add_save);

        tv_title.setText("添加收入项");
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSave();
                setResult(ADD_ALLOWANCE,getIntent());
                finish();
            }
        });
        presenter.init(date);
    }
    public void setRv(RecyclerView.Adapter adapter){
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    public void setBack(){
        ImageButton ib= (ImageButton) findViewById(R.id.ib_statistics_add_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
