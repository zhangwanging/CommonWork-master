package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.JobMsgAdapter;
import com.hzu.jpg.commonwork.enity.moudle.JobMsg;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.widgit.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fangx.common.ui.activity.BaseAppCompatActivity;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.common.util.netstatus.NetUtils;

/**
 * Created by Administrator on 2017/2/26.
 */

public class SearchJobOrEmpActivity extends BaseAppCompatActivity {
    @Bind(R.id.ib_back)
    ImageButton ibBack;
    @Bind(R.id.et_search)
    AppCompatEditText etSearch;
    @Bind(R.id.myRecyclerView)
    MyRecyclerView myRecyclerView;


    public static final String TAG = "SearchJobOrEmpActivity";

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_search_job_emp;
    }

    @Override
    protected void initViewsAndEvents() {
        myRecyclerView.setVLinerLayoutManager();
        final List<JobMsg> jobs = new ArrayList<>();
        for (int i = 0; i<10; i++){
            jobs.add(new JobMsg());
        }
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event != null && (actionId == 0 || actionId == 3) ){
                    JobMsgAdapter adapter = new JobMsgAdapter(SearchJobOrEmpActivity.this,jobs);
                    myRecyclerView.setAdapter(adapter);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ib_back)
    public void onClick() {
        finish();

    }
}
