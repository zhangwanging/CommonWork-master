package com.hzu.jpg.commonwork.activity.service;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.action.RequestAction;
import com.hzu.jpg.commonwork.adapter.goods.GoodsAdapter;
import com.hzu.jpg.commonwork.adapter.service.NewsAdapter;
import com.hzu.jpg.commonwork.adapter.service.PostsAdapter;
import com.hzu.jpg.commonwork.enity.service.NewsVo;
import com.hzu.jpg.commonwork.enity.service.PostsVo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class PostsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img1, img2, img3, img4, img5;
    private int classfy = 1;
    private RecyclerView recycleListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler uiHandler = null;
    private final int INIT_DATA_VIEW = 1001;
    private Handler handler = new Handler();
    private RequestAction action;
    private int startPage = 1;
    private boolean isLoading;
    private PostsAdapter adapter;
    private ArrayList<PostsVo.Posts> data = new ArrayList<>();
    private PostsVo postsVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        initHandler();
        getXmlView();
        initData();
    }

    //刷新数据
    private void updateData() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        //清除数据
        this.data.clear();
        isLoading = false;
        getData(); //获取数据
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

    private void getXmlView() {
        action = new RequestAction(this);
        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.findViewById(R.id.layout_view_1).setOnClickListener(this);
        this.findViewById(R.id.layout_view_2).setOnClickListener(this);
        this.findViewById(R.id.layout_view_3).setOnClickListener(this);
        this.findViewById(R.id.layout_view_4).setOnClickListener(this);
        this.findViewById(R.id.layout_view_5).setOnClickListener(this);
        img1 = (ImageView) this.findViewById(R.id.image_view_1);
        img2 = (ImageView) this.findViewById(R.id.image_view_2);
        img3 = (ImageView) this.findViewById(R.id.image_view_3);
        img4 = (ImageView) this.findViewById(R.id.image_view_4);
        img5 = (ImageView) this.findViewById(R.id.image_view_5);
        this.findViewById(R.id.right_tv).setOnClickListener(this);

        adapter = new PostsAdapter(PostsActivity.this, data);
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
        final LinearLayoutManager layoutManager = new LinearLayoutManager(PostsActivity.this);
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
                PostsVo.Posts data = adapter.getAll().get(position);
                Intent intent = new Intent(PostsActivity.this, PostsDetailActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }

            @Override
            public void onItemLongClickListener(View view, int position) {
                //长按
            }
        });
    }

    class getCommentThread implements Runnable {
        private Thread rthread = null;// 监听线程.

        @Override
        public void run() {
            NameValuePair method_app = new BasicNameValuePair("method", "android");
            NameValuePair classfy_app = new BasicNameValuePair("classfy", classfy + "");
            NameValuePair pageNo_app = new BasicNameValuePair("pageNo", startPage + "");
            NameValuePair pageSize_app = new BasicNameValuePair("pageSize", "10");
            List<NameValuePair> params = new ArrayList<>();
            params.add(method_app);
            params.add(classfy_app);
            params.add(pageNo_app);
            params.add(pageSize_app);
            postsVo = action.getCommentAction(params);
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
                        if (postsVo != null && !postsVo.equals("")) {
                            for (int i = 0; i < postsVo.getComment().size(); i++)
                                data.add(postsVo.getComment().get(i));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_view_1:
                showImageView(img1);
                classfy = 1;
                updateData();
                break;
            case R.id.layout_view_2:
                showImageView(img2);
                classfy = 2;
                updateData();
                break;
            case R.id.layout_view_3:
                showImageView(img3);
                classfy = 3;
                updateData();
                break;
            case R.id.layout_view_4:
                showImageView(img4);
                classfy = 4;
                updateData();
                break;
            case R.id.layout_view_5:
                showImageView(img5);
                classfy = 5;
                updateData();
                break;
            case R.id.right_tv:
                startActivity(new Intent(PostsActivity.this, AddPostsActivity.class));
                break;
        }
    }

    private void showImageView(ImageView imageView) {
        img1.setVisibility(View.INVISIBLE);
        img2.setVisibility(View.INVISIBLE);
        img3.setVisibility(View.INVISIBLE);
        img4.setVisibility(View.INVISIBLE);
        img5.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
    }
}
