package com.hzu.jpg.commonwork.activity.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.action.RequestAction;
import com.hzu.jpg.commonwork.activity.ApplyJobActivity;
import com.hzu.jpg.commonwork.activity.CityPickerActivity;
import com.hzu.jpg.commonwork.activity.JobMsgActivity;
import com.hzu.jpg.commonwork.activity.LoginActivity;
import com.hzu.jpg.commonwork.activity.RecruitmentActivity;
import com.hzu.jpg.commonwork.activity.WebViewActivity;
import com.hzu.jpg.commonwork.activity.service.NewsDetailActivity;
import com.hzu.jpg.commonwork.activity.service.PostsActivity;
import com.hzu.jpg.commonwork.activity.service.ServiceActivity;
import com.hzu.jpg.commonwork.adapter.home.MainAdapter;
import com.hzu.jpg.commonwork.adapter.home.MainGridAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.base.BaseRvAdapter;
import com.hzu.jpg.commonwork.enity.home.JobVo;
import com.hzu.jpg.commonwork.enity.moudle.Picture;
import com.hzu.jpg.commonwork.enity.service.NewsVo;
import com.hzu.jpg.commonwork.utils.GlideImageLoader;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.widgit.AutoVerticalScrollTextView;
import com.hzu.jpg.commonwork.widgit.MyRecyclerView;
import com.yyydjk.library.BannerLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fangx.common.ui.fragment.BaseLazyFragment;
import me.fangx.common.util.eventbus.EventCenter;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;

public class MainActivity extends BaseLazyFragment implements View.OnClickListener {

    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    private AutoVerticalScrollTextView verticalScrollTV;
    private ImageView img1, img2, img3, img4, img5;
    private static final String TAG = "HomeFragment";
    private MainAdapter jobMsgAdapter;
    private MainGridAdapter gridAdapter;
    private List<JobVo.Data> jobsMsg = new ArrayList<>();
    private int jobMsgPage = 0;
    private RequestAction action;
    private final int HIDE_PROGRESS = 1001;
    private final int SHOW_PROGRESS = 1002;
    private final int INSERT_DATA = 1003;
    private Handler handler = new Handler();
    private JobVo jobVo;
    private BannerLayout bannerLayout;
    private static final int REQUEST_CODE_PICK_CITY = 0;
    private String[] strings;
    private boolean isRunning = true;
    private int number = 0;
    private View autoView_welfare;
    private Handler uiHandler = null;
    private final int INIT_DATA_VIEW = 1001;
    private NewsVo newsVo;
    private String newsType = "推荐";
    private ListView listContent;
    private MainNewsAdapter newsAdapter;
    private TextView no_data_tv;
    private final int ACCESS_COARSE_LOCATION_REQUEST_CODE = 1000;

    @Override
    protected void initViewsAndEvents() {
        initHandler();
        action = new RequestAction(getActivity());

        //头部View
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_home_rv, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(lp);
        GridView jobLogoGrid = (GridView) headView.findViewById(R.id.jobLogoGrid);
        gridAdapter = new MainGridAdapter(getActivity());
        jobLogoGrid.setAdapter(gridAdapter);
        jobLogoGrid.setSelector(R.drawable.hide_listview_yellow_selector);
        jobLogoGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {//"求职",
                    Intent intent = new Intent(getActivity(), ApplyJobActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
                if (position == 1) {//"求职",
                    Intent intent = new Intent(getActivity(), ApplyJobActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
                if (position == 2) {//"求职",
                    Intent intent = new Intent(getActivity(), ApplyJobActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
                if (position == 3) {//"求职",
                    Intent intent = new Intent(getActivity(), ApplyJobActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
                if (position == 4) {//"招聘",
                    if (MyApplication.user == null) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        getActivity().startActivity(intent);
                    } else if (MyApplication.role == 0) {
                        ToastUtil.showToast("求职者无法发布招聘信息");
                    } else {
                        Intent intent = new Intent(getActivity(), RecruitmentActivity.class);
                        getActivity().startActivity(intent);
                    }
                }
                if (position == 7) {//"便民服务",
                    getActivity().startActivity(new Intent(getActivity(), PostsActivity.class));
                }
//                if (position == 5) {//"投诉与建议",
//                    Intent intent = new Intent(getActivity(), AdviceActivity.class);
//                    getActivity().startActivity(intent);
//                }
                if (position == 6) {//"视频面试",
                    if (MyApplication.user == null) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        getActivity().startActivity(intent);
                        ToastUtil.showToast("登录后才能视频面试哦~");
                    } else {
                        ToastUtil.showToast("仅供人事经理使用！");
                    }
                }
                if (position == 5) {//"消息",
                    ToastUtil.showToast("功能即将开发，敬请期待。");
                }

            }
        });
        headView.findViewById(R.id.layout_view_1).setOnClickListener(this);
        headView.findViewById(R.id.layout_view_2).setOnClickListener(this);
        headView.findViewById(R.id.layout_view_3).setOnClickListener(this);
        headView.findViewById(R.id.layout_view_4).setOnClickListener(this);
        headView.findViewById(R.id.layout_view_5).setOnClickListener(this);
        img1 = (ImageView) headView.findViewById(R.id.image_view_1);
        img2 = (ImageView) headView.findViewById(R.id.image_view_2);
        img3 = (ImageView) headView.findViewById(R.id.image_view_3);
        img4 = (ImageView) headView.findViewById(R.id.image_view_4);
        img5 = (ImageView) headView.findViewById(R.id.image_view_5);
        listContent = (ListView) headView.findViewById(R.id.listContent);
        TextView more_news_tv = (TextView) headView.findViewById(R.id.more_news_tv);
        no_data_tv = (TextView) headView.findViewById(R.id.no_data_tv);
        more_news_tv.setOnClickListener(this);
        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsVo.Data data = newsAdapter.getAll().get(i);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });

        verticalScrollTV = (AutoVerticalScrollTextView) headView.findViewById(R.id.textView);
        autoView_welfare = headView.findViewById(R.id.autoView_welfare);
        autoView_welfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(), ServiceActivity.class));
                /*NewsVo.Data data = newsVo.getData().get(number);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);*/
                getActivity().startActivity(new Intent(getActivity(), PostsActivity.class));
            }
        });

        bannerLayout = (BannerLayout) headView.findViewById(R.id.bannerLayout);
        initPicture();
        //recyclerView 设置
        recyclerView.setVLinerLayoutManager();
        recyclerView.addHeaderView(headView);


        jobMsgAdapter = new MainAdapter(getContext(), jobsMsg);
        jobMsgAdapter.setOnRecyclerViewItemClickListener(new BaseRvAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                position = position - 1;
                Intent intent = new Intent(getActivity(), JobMsgActivity.class);
                intent.putExtra(Config.ID, jobMsgAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
        recyclerView.setVLinerLayoutManager();
        recyclerView.setAdapter(jobMsgAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLoadingListener(new MyRecyclerView.LoadingListener() {
            @Override
            public void onLoadMore() {
                jobMsgPage++;
                initData();
            }
        });
        initData();

        getActivity().findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        new getNewsListDataThread().startThread();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        isRunning = false;
    }

    @OnClick(R.id.tv_location)
    public void onClick() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                MainActivity.this.requestPermissions(
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_COARSE_LOCATION_REQUEST_CODE);
            } else {
                startActivityForResult(new Intent(getContext(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
            }
        } else {
            //启动
            startActivityForResult(new Intent(getContext(), CityPickerActivity.class),
                    REQUEST_CODE_PICK_CITY);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCESS_COARSE_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                startActivityForResult(new Intent(getContext(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
            } else {
                // Permission Denied
                Toast.makeText(this.getContext(), "访问被拒绝！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                Config.SELECTED_CITY = city;
                tvLocation.setText(city);
                initData();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 1000);
    }

    private void getData() {
        new showSimpleInfoThread().startThread();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_view_1:
                showImageView(img1);
                newsType = "推荐";
                new getNewsListDataThread().startThread();
                break;
            case R.id.layout_view_2:
                showImageView(img2);
                newsType = "搞笑";
                new getNewsListDataThread().startThread();
                break;
            case R.id.layout_view_3:
                showImageView(img3);
                newsType = "八卦";
                new getNewsListDataThread().startThread();
                break;
            case R.id.layout_view_4:
                showImageView(img4);
                newsType = "娱乐";
                new getNewsListDataThread().startThread();
                break;
            case R.id.layout_view_5:
                showImageView(img5);
                newsType = "工作";
                new getNewsListDataThread().startThread();
                break;
            case R.id.more_news_tv:
                Intent intent = new Intent(getActivity(), ServiceActivity.class);
                startActivity(intent);
                break;
        }
    }

    class getNewsListDataThread implements Runnable {
        private Thread rthread = null;// 监听线程.

        @Override
        public void run() {
            NameValuePair pageNo_app = new BasicNameValuePair("startPage", 1 + "");
            NameValuePair pageSize_app = new BasicNameValuePair("pageSize", "10");
            NameValuePair newsType_app = new BasicNameValuePair("classfy", newsType);
            List<NameValuePair> params = new ArrayList<>();
            params.add(pageNo_app);
            params.add(pageSize_app);
            params.add(newsType_app);
            newsVo = action.getNewsListDataAction(params);
            if (newsVo != null) {

                uiHandler.sendEmptyMessage(1);
            }
        }

        public void startThread() {
            if (rthread == null) {
                rthread = new Thread(this);
                rthread.start();
            }
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


    class showSimpleInfoThread implements Runnable {
        private Thread rthread = null;// 监听线程.

        @Override
        public void run() {
            NameValuePair page_app = new BasicNameValuePair("page", jobMsgPage + "");//搜索字段
            NameValuePair city_app = new BasicNameValuePair("city", Config.SELECTED_CITY);//搜索字段
            List<NameValuePair> params = new ArrayList<>();
            params.add(page_app);
            params.add(city_app);
            jobVo = action.showSimpleInfoAction(params);
            uiHandler.sendEmptyMessage(INSERT_DATA);
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
                    case SHOW_PROGRESS:// 显示加载中....
                        break;
                    case HIDE_PROGRESS:// 关闭加载....
                        break;
                    case INSERT_DATA:
                        if (jobVo != null && !jobVo.equals("")) {
                            //ToastUtil.showToast(jobVo.getMessage());
                            if (jobVo.getData() != null) {
                                if (jobVo.getStatu() != 0) {
                                    if (jobVo.getData().size() <= 0) {
                                        recyclerView.noMoreLoading();
                                    } else {
                                        jobMsgAdapter.addData(jobVo.getData());
                                        recyclerView.loadMoreComplete();
                                    }
                                } else
                                    recyclerView.noMoreLoading();
                            }
                        }
                        break;
                    case 1:

                        newsAdapter = new MainNewsAdapter(getContext(), newsVo.getData());
                        if (newsVo != null && !newsVo.equals(""))
                            if (newsVo.getData() != null && newsVo.getData().size() > 0) {
                                listContent.setVisibility(View.VISIBLE);
                                no_data_tv.setVisibility(View.GONE);
                                listContent.setAdapter(newsAdapter);
                                strings = new String[newsVo.getData().size()];
                                for (int i = 0; i < newsVo.getData().size(); i++) {
                                    strings[i] = newsVo.getData().get(i).getTitle();
                                }
                            } else {
                                listContent.setVisibility(View.GONE);
                                no_data_tv.setVisibility(View.VISIBLE);
                            }
                        if (strings.length > 0) {
                            verticalScrollTV.setText(strings[0]);
                            new Thread() {
                                @Override
                                public void run() {
                                    while (isRunning) {
                                        SystemClock.sleep(3000);
                                        he.sendEmptyMessage(199);
                                    }
                                }
                            }.start();
                        }

                        break;
                }
            }
        };
    }

    private Handler he = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 199) {
                verticalScrollTV.next();
                number++;
                if (number > (strings.length - 1))
                    number = 0;
                verticalScrollTV.setText(strings[number]);
            }

        }
    };

    private void initPicture() {
        final List<String> urls = new ArrayList<>();
        OkHttpUtils.post().url(Config.URL_PICTURE)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: " + e.getMessage(), null);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse: " + response, null);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int statu = jsonObject.getInt(Config.KEY_STATU);
                    switch (statu) {
                        case Config.STATUS_SUCCESS:
                            ObjectMapper objectMapper = new ObjectMapper();
                            final List<Picture> pictures = objectMapper.readValue(jsonObject.getString(Config.KEY_DATA)
                                    , new TypeReference<List<Picture>>() {
                                    });
                            for (Picture picture : pictures) {
                                urls.add(Config.IP + picture.getPicture());
                            }
                            bannerLayout.setImageLoader(new GlideImageLoader(new GlideImageLoader.OnImageClickListener() {
                                @Override
                                public void onImageClick(String path) {
                                    Intent intent = new Intent(MainActivity.this.getActivity(), WebViewActivity.class);
                                    for (Picture picture : pictures) {
                                        if ((Config.IP + picture.getPicture()).equals(path)) {
                                            intent.putExtra("url", picture.getUrl());
                                            break;
                                        }
                                    }
                                    startActivity(intent);
                                }
                            }));
                            Log.e(TAG, "onResponse: " + urls);
                            bannerLayout.setViewUrls(urls);
                            break;
                        case Config.STATUS_FAIL:
                            ToastUtil.showToast(jsonObject.getString(Config.KEY_MESSAGE));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
