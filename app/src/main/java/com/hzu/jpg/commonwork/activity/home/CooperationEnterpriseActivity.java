package com.hzu.jpg.commonwork.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.action.RequestAction;
import com.hzu.jpg.commonwork.activity.CompanyMsgActivity;
import com.hzu.jpg.commonwork.adapter.home.CooperationAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.moudle.Enterprise;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by zhutao on 2017/8/8.
 */

public class CooperationEnterpriseActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recycleListView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Handler uiHandler = null;
    private final int INIT_DATA_VIEW = 1001;
    private Handler handler = new Handler();
    private RequestAction action;
    private int startPage = 1;
    private boolean isLoading;
    private Enterprise enterprise = new Enterprise();
    private ArrayList<Enterprise.Data> data = new ArrayList<>();
    private CooperationAdapter adapter;
    private String keyWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_enterprise);
        ButterKnife.bind(this);
        initHandler();
        initView();
        initData();
    }

    private void initView() {
        keyWord = getIntent().getStringExtra("keyWord");
        action = new RequestAction(this);
        adapter = new CooperationAdapter(this, data);
        swipeRefreshLayout.setColorSchemeResources(R.color.blueStatus);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //下拉刷新
                        data.clear(); //清除数据
                        startPage = 1;
                        getData(); //获取数据
                    }
                }, 1000);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(CooperationEnterpriseActivity.this);
        recycleListView.setLayoutManager(layoutManager);
        recycleListView.setAdapter(adapter);
        recycleListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //上拉加载
                                if (!adapter.isNotMoreData()) {
                                    startPage++;
                                    getData();//添加数据
                                }
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
        adapter.setOnItemClickListener(new CooperationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(CooperationEnterpriseActivity.this, CompanyMsgActivity.class);
                intent.putExtra(Config.KEY_ID, data.get(position).getId() + "");
                startActivity(intent);
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
    }

    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 1000);
    }

    /**
     * 获取企业列表数据
     */
    private void getData() {
        new getCommentThread().startThread();
    }

    private void initHandler() {
        uiHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case INIT_DATA_VIEW:
                        if (enterprise != null) {
                            for (int i = 0; i < enterprise.getData().size(); i++)
                                data.add(enterprise.getData().get(i));
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                            adapter.notifyItemRemoved(adapter.getItemCount());
                            int currentPage = (int) Math.ceil((double) (adapter.getItemCount() - 1) / 10);
                            if (currentPage >= enterprise.getTotalPage())
                                adapter.setNotMoreData(true);
                            else
                                adapter.setNotMoreData(false);
                        }
                        break;
                }
            }
        };
    }

    class getCommentThread implements Runnable {
        private Thread rthread = null;// 监听线程.

        @Override
        public void run() {
            String url = "https://www.jiongzhiw.com/HRM/company/gtCooCompany.html";
            OkHttpUtils.post().url(url)
                    .addParams("startPage", startPage + "")
                    .addParams("pageSize", "10")
                    .addParams("kyeWord", keyWord + "")
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    if (response != null && !"".equals(response)) {
                        Gson gson = new Gson();
                        enterprise = gson.fromJson(response, Enterprise.class);
                        uiHandler.sendEmptyMessage(INIT_DATA_VIEW);
                    }
                }
            });
        }

        public void startThread() {
            if (rthread == null) {
                rthread = new Thread(this);
                rthread.start();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.ib_setting_back)
    public void onAtBack() {
        finish();
    }
}
