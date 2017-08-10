package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hzu.jpg.commonwork.Presenter.AgentPresenter;
import com.hzu.jpg.commonwork.R;

public class AgentActivity extends AppCompatActivity {

    RecyclerView rv;
    TextView tvInvite;

    AgentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_agent);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout
                = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_agent);
        collapsingToolbarLayout.setTitle("邀请人员列表");
        rv= (RecyclerView) findViewById(R.id.rv_agent);
        tvInvite= (TextView) findViewById(R.id.tv_agent_invite);

        presenter=new AgentPresenter(this);
        presenter.initData();
    }

    public void setRvData(RecyclerView.Adapter adapter){
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    public void setInvite(String s){
        tvInvite.setText(s);
    }
}
