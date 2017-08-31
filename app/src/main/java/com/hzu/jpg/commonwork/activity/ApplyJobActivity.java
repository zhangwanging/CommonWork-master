package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.JobMsgAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.base.BaseRvAdapter;
import com.hzu.jpg.commonwork.callback.JobMsgCallback;
import com.hzu.jpg.commonwork.enity.FilterData;
import com.hzu.jpg.commonwork.enity.FilterEntity;
import com.hzu.jpg.commonwork.enity.moudle.JobMsg;
import com.hzu.jpg.commonwork.utils.DataUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.widgit.FilterView;
import com.hzu.jpg.commonwork.widgit.MyRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fangx.common.ui.activity.BaseAppCompatActivity;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.common.util.netstatus.NetUtils;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/16.
 */

public class ApplyJobActivity extends BaseAppCompatActivity {
    private static final String TAG = "ApplyJobActivity";
    @Bind(R.id.filterView)
    FilterView filterView;
    @Bind(R.id.iv_back)
    TextView ivBack;
    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.search_et)
    EditText search_text;

    private String classify = "";
    private String lowPrice = "";
    private String highPrice = "";
    private String label = "";
    private int page = 0;
    private String region = "";
    private String search = "";

    private List<JobMsg> jobMsg = new ArrayList<>();
    private JobMsgAdapter adapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                swipeLayout.setRefreshing(false);
            } else if (msg.what == 1) {
                Log.e(TAG, "handleMessage: " + adapter.getItemCount(), null);
                adapter.notifyDataSetChanged();
            } else {
                recyclerView.setLoadingMoreEnabled(false);
            }
        }
    };
    @Bind(R.id.btn_oneApplyJob)
    FloatingActionButton btnOneApplyJob;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_apply_job;
    }


    @Override
    protected void initViewsAndEvents() {

        classify = this.getIntent().getStringExtra("classify"); //主要跳过来

        search = this.getIntent().getStringExtra("search"); //搜索页面跳过来
        if (search != null && !search.equals("")) {
            search_text.setText(search);
            classify = search;
        } else if (!classify.equals("")) {
            search_text.setText(classify);
        }

        //键盘搜索事件
        search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    classify = search_text.getText().toString().trim();
                    searchJobSmg();
                    return true;
                }
                return false;
            }
        });

        FilterData filterData = new FilterData();
        filterData.setFirstOne(DataUtil.getOneFirst());
        filterData.setSecond(DataUtil.regions);
        filterData.setThird(DataUtil.getSalary());
        filterData.setFourth(DataUtil.getWelfare());
        filterView.setFilterData(this, filterData);

        filterView.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterView.show(position);
            }
        });

        filterView.setOnItemOnlyFirstClickListener(new FilterView.OnItemOnlyFirstClickListener() {
            @Override
            public void onItemOnlyFirstClick(FilterEntity entity) {
                Log.e(TAG, "onItemFirstClick: " + entity, null);
                if (entity.getValue().equals("1")) {
                    classify = "";
                } else
                    classify = entity.getKey();
                searchJobSmg();
            }
        });

        filterView.setOnItemSecondClickListener(new FilterView.OnItemSecondClickListener() {
            @Override
            public void onItemSecondClick(FilterEntity entity) {
                Log.e(TAG, "onItemSecondClick: " + entity, null);
                if (entity.getValue().equals("1")) {
                    region = "";
                } else {
                    region = entity.getKey();
                }
                searchJobSmg();
            }
        });

        filterView.setOnItemThirdClickListener(new FilterView.OnItemThirdClickListener() {
            @Override
            public void onItemThirdClick(FilterEntity entity) {
                switch (entity.getValue()) {
                    case "1":
                        lowPrice = "0";
                        highPrice = "1000";
                        break;
                    case "8":
                        lowPrice = "6000";
                        highPrice = "20000";
                        break;
                    default:
                        String temp[] = entity.getKey().split("-");
                        lowPrice = temp[0];
                        highPrice = temp[1].replace("元", "");
                        Log.e(TAG, "onItemThirdClick: " + lowPrice + "--" + highPrice, null);
                        break;
                }
                searchJobSmg();
            }
        });

        filterView.setOnItemFourthClickListener(new FilterView.OnItemFourthClickListener() {
            @Override
            public void onItemFourthClick(FilterEntity entity) {
                Log.e(TAG, "onItemFourthClick: " + entity, null);
                if (entity.getValue().equals("1")) {
                    label = "";
                } else {
                    label = entity.getKey();
                }
                searchJobSmg();
            }
        });


        swipeLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                searchJobSmg();
            }
        });

        recyclerView.setVLinerLayoutManager();
        recyclerView.setLoadingListener(new MyRecyclerView.LoadingListener() {
            @Override
            public void onLoadMore() {
                page++;
                searchJobSmg();
            }
        });
        adapter = new JobMsgAdapter(this, jobMsg);
        adapter.setOnRecyclerViewItemClickListener(new BaseRvAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ApplyJobActivity.this, JobMsgActivity.class);
                Log.d(TAG, "onclick id:" + position);
                Log.e(TAG, "onItemClick: " + adapter.getItem(position).getId(), null);
                intent.putExtra(Config.ID, Integer.parseInt(adapter.getItem(position).getId()));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        searchJobSmg();
    }

    private void searchJobSmg() {

        String classify = this.classify;

        Log.e(TAG, "searchJobSmg: " + Config.SELECTED_CITY, null);
        OkHttpUtils.post()
                .url(Config.URL_JOB_MSG_SIMPLE)
                .addParams(Config.KEY_CITY, Config.SELECTED_CITY == null ? "" : Config.SELECTED_CITY)
                .addParams(Config.KEY_CLASSIFY, classify == null ? "" : classify)
                .addParams(Config.KEY_PAGE, String.valueOf(page))
                .tag(TAG)
                .build().execute(new JobMsgCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showNetError();
                Log.e(TAG, "onError: " + e.getMessage());
                if (page == 0)
                    swipeLayout.setRefreshing(false);
                else
                    recyclerView.setLoadingMoreEnabled(false);
            }

            @Override
            public void onResponse(List<JobMsg> response, int id) {
                if (response == null || response.isEmpty()) {
                    if (page == 0) {
                        if (swipeLayout != null) {
                            swipeLayout.setRefreshing(false);
                        }
                        adapter.clearData();
                    } else {
                        recyclerView.noMoreLoading();
                    }
                } else {
                    if (page == 0) {
                        if (swipeLayout != null) {
                            swipeLayout.setRefreshing(false);
                        }
                        adapter.reSetData(response);
                    } else {
                        adapter.addData(response);
                        recyclerView.loadMoreComplete();
                    }
                }

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
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.SCALE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.iv_back, R.id.btn_oneApplyJob})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                Log.e(TAG, "onClick: back", null);
                this.finish();
                break;
            case R.id.btn_oneApplyJob:
                if (filterView.isShowing())
                    filterView.hide();
                if (MyApplication.user == null)
                    startActivity(new Intent(this, LoginActivity.class));
                else if (MyApplication.role == 0) {
                    if (MyApplication.user.getIs_supplement() == 0) {
                        startActivity(new Intent(this, MyInfoEditActivity.class));
                    } else
                        startActivity(new Intent(this, ResumeActivity.class));
                } else {
                    ToastUtil.showToast("企业无法发布一键求职");
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getErrorMsg(String msg) {
        ToastUtil.showToast(msg, Gravity.CENTER);
        jobMsg.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
