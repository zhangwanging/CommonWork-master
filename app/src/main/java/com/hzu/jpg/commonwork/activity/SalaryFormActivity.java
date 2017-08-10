package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.hzu.jpg.commonwork.Presenter.SalaryFormPresenter;
import com.hzu.jpg.commonwork.R;


public class SalaryFormActivity extends AppCompatActivity {

    RecyclerView rv;
    SalaryFormPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_form);
        setBack();
        rv= (RecyclerView) findViewById(R.id.rv_salary_form);
        presenter=new SalaryFormPresenter(this);
        presenter.setData();
    }

    public void setRv(RecyclerView.Adapter adapter){
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }
    public void setBack(){
        ImageButton ib= (ImageButton) findViewById(R.id.ib_salary_form_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
