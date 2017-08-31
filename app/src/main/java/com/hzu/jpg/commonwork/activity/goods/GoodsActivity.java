package com.hzu.jpg.commonwork.activity.goods;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.action.RequestAction;
import com.hzu.jpg.commonwork.adapter.goods.GoodsAdapter;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.goods.GoodsVo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class GoodsActivity extends AppCompatActivity {

    private RecyclerView recycleListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler uiHandler = null;
    private final int INIT_DATA_VIEW = 1001;
    private Handler handler = new Handler();
    private RequestAction action;
    private int startPage = 1;
    private ArrayList<GoodsVo> goodsVos;
    private GoodsAdapter adapter;
    private ArrayList<GoodsVo> data = new ArrayList<>();
    private boolean isLoading;
    private TextView tv_point;
    private int point = 0; //默认积分

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initHandler();
        getXmlView();
        initData();
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
        new getShopDataThread().startThread();
    }

    private void getXmlView() {

        this.findViewById(R.id.goods_recording_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoodsActivity.this, GoodsRecordingActivity.class));
            }
        });

        action = new RequestAction(this);
        this.findViewById(R.id.back_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_point = (TextView) this.findViewById(R.id.tv_point);
        if (MyApplication.user != null && !MyApplication.user.equals("")) {
            tv_point.setText(MyApplication.user.getPoint().toString());
            point = Integer.parseInt(MyApplication.user.getPoint().toString());
        } else
            tv_point.setText(point + "");
        adapter = new GoodsAdapter(GoodsActivity.this, data, point);
        recycleListView = (RecyclerView) this.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipeRefreshLayout);
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
        final LinearLayoutManager layoutManager = new LinearLayoutManager(GoodsActivity.this);
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
        //给List添加点击事件
        adapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClickListener(View view, int position) {
                //长按
            }
        });
    }

    class getShopDataThread implements Runnable {
        private Thread rthread = null;// 监听线程.

        @Override
        public void run() {
            NameValuePair method_app = new BasicNameValuePair("method", "android");
            NameValuePair startPage_app = new BasicNameValuePair("startPage", startPage + "");
            NameValuePair pageSize_app = new BasicNameValuePair("pageSize", "10");
            List<NameValuePair> params = new ArrayList<>();
            params.add(method_app);
            params.add(startPage_app);
            params.add(pageSize_app);
            goodsVos = action.getShopDataAction(params);
            uiHandler.sendEmptyMessage(INIT_DATA_VIEW);
        }

        public void startThread() {
            if (rthread == null) {
                rthread = new Thread(this);
                rthread.start();
            }
        }
    }

    private void initHandler() {
        uiHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case INIT_DATA_VIEW:
                        if (goodsVos != null && !goodsVos.equals("")) {
                            for (int i = 0; i < goodsVos.size(); i++)
                                data.add(goodsVos.get(i));
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                            adapter.notifyItemRemoved(adapter.getItemCount());
                            adapter.setNotMoreData(true);
                        }
                        break;
                }
            }
        };
    }
}
