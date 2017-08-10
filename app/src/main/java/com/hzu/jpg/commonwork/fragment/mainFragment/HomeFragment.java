package com.hzu.jpg.commonwork.fragment.mainFragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Region;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.hzu.jpg.commonwork.activity.WebViewActivity;
import com.hzu.jpg.commonwork.adapter.HomeFunctionRVAdapter;
import com.hzu.jpg.commonwork.adapter.JobMsgAdapter;
import com.hzu.jpg.commonwork.adapter.SharedMsgRvAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.base.BaseRvAdapter;
import com.hzu.jpg.commonwork.callback.JobMsgCallback;
import com.hzu.jpg.commonwork.enity.Comment;
import com.hzu.jpg.commonwork.enity.SharedMsg;
import com.hzu.jpg.commonwork.enity.moudle.JobMsg;
import com.hzu.jpg.commonwork.enity.moudle.Picture;
import com.hzu.jpg.commonwork.enity.moudle.RegionBean;
import com.hzu.jpg.commonwork.enity.service.NewsVo;
import com.hzu.jpg.commonwork.event.AddressEvent;
import com.hzu.jpg.commonwork.utils.DataUtil;
import com.hzu.jpg.commonwork.utils.GlideImageLoader;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.widgit.MyRecyclerView;
import com.yyydjk.library.BannerLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fangx.common.ui.fragment.BaseLazyFragment;
import me.fangx.common.util.DensityUtils;
import me.fangx.common.util.eventbus.EventCenter;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/1/21.
 */

public class HomeFragment extends BaseLazyFragment {

    @Bind(R.id.bannerLayout)
    BannerLayout bannerLayout;
    @Bind(R.id.collapsingLayout)
    CollapsingToolbarLayout collapsingLayout;
    @Bind(R.id.appBar)
    AppBarLayout appBar;
    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;
    @Bind(R.id.ll_location)
    LinearLayout llLocation;
    @Bind(R.id.tv_location)
    TextView tvLocation;

    private static final String TAG = "HomeFragment";
    private static final int REQUEST_CODE_PICK_CITY = 0;
    private TypedArray functionIcons;
    private String[] functionTitles;
    private List<Integer> functionIconsRes;

    private HomeFunctionRVAdapter functionRVAdapter;
    private JobMsgAdapter jobMsgAdapter;
    private List<JobMsg> jobsMsg = new ArrayList<>();
    private int jobMsgPage = 0;

    @Override
    protected void initViewsAndEvents() {
        tvLocation.setText(Config.LOCATION_CITY);
        searchLocation(Config.LOCATION_CITY);

        initPicture();

        //头部View
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_home_rv, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(DensityUtils.getDisplayWidth(Config.CONTEXT), ViewGroup.LayoutParams.WRAP_CONTENT);

        headView.setLayoutParams(lp);
        RecyclerView headRV = (RecyclerView) headView.findViewById(R.id.recyclerView_function);
        functionTitles = getResources().getStringArray(R.array.functionTitles);
        functionIcons = getResources().obtainTypedArray(R.array.functionIcons);
        functionIconsRes = new ArrayList<>();
        for (int i = 0; i < functionIcons.length(); i++) {
            functionIconsRes.add(functionIcons.getResourceId(i, -1));
        }
        functionRVAdapter = new HomeFunctionRVAdapter(getContext(), functionTitles, functionIconsRes);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        headRV.setLayoutManager(layoutManager);
        headRV.setNestedScrollingEnabled(false);
        headRV.setAdapter(functionRVAdapter);

        //recyclerView 设置
        recyclerView.setVLinerLayoutManager();
        recyclerView.addHeaderView(headView);


        jobMsgAdapter = new JobMsgAdapter(getContext(), jobsMsg);
        jobMsgAdapter.setOnRecyclerViewItemClickListener(new BaseRvAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                startActivity(new Intent(getActivity(), ApplyJobActivity.class));

                //change :chow
                position = position - 1;
                Intent intent = new Intent(getActivity(), JobMsgActivity.class);
                Log.d("main click id", String.valueOf(position));
                Log.e(TAG, "onItemClick: " + jobMsgAdapter.getItem(position).getId(), null);
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
                searchJobSmg();
            }
        });

        searchJobSmg();
    }

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
                                    Intent intent = new Intent(HomeFragment.this.getActivity(), WebViewActivity.class);
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
                            //ToastUtil.showToast(jsonObject.getString(Config.KEY_MESSAGE));
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

    private void searchJobSmg() {
        Log.e(TAG, "searchJobSmg: " + Config.SELECTED_CITY, null);
        OkHttpUtils.post()
                .url(Config.URL_JOB_MSG_SIMPLE)
                .addParams(Config.KEY_CITY, Config.SELECTED_CITY)
                .addParams(Config.KEY_REGION, "")
                .addParams(Config.KEY_CLASSIFY, "")
                .addParams(Config.KEY_LOW_PRICE, "")
                .addParams(Config.KEY_HIGH_PRICE, "")
                .addParams(Config.KEY_LABEL, "")
                .addParams(Config.KEY_PAGE, String.valueOf(jobMsgPage))
                .tag(TAG)
                .build().execute(new JobMsgCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showNetError();
                Log.e(TAG, "onError: " + e.getMessage(), null);
            }

            @Override
            public void onResponse(List<JobMsg> response, int id) {
                if (response == null || response.isEmpty()) {
                    if (jobMsgPage == 0) {
                        jobMsgAdapter.clearData();
                        recyclerView.noMoreLoading();
                        ToastUtil.showToast("暂无该城市求职信息");
                    } else {
                        recyclerView.noMoreLoading();
                    }
                } else {
                    jobMsgAdapter.addData(response);
                    recyclerView.loadMoreComplete();
                }

            }
        });


    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
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
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.ll_location)
    public void onClick() {
        //启动
        startActivityForResult(new Intent(getContext(), CityPickerActivity.class),
                REQUEST_CODE_PICK_CITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                Config.SELECTED_CITY = city;
                tvLocation.setText(city);
                searchLocation(city);
                searchJobSmg();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void searchLocation(String city) {
        Log.e(TAG, "searchLocation: " + city, null);
        OkHttpUtils.post().url(Config.URL_SHOW_LOCATION)
                .addParams(Config.KEY_CITY, city)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: " + e.getMessage(), null);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    switch (jsonObject.getInt(Config.KEY_STATU)) {
                        case Config.STATUS_FAIL:
                            ToastUtil.showToast("找不到位置");
                            DataUtil.setRegionData(null);
                            break;
                        case Config.STATUS_SUCCESS:
                            String jsonArray = jsonObject.getString(Config.KEY_DATA);
                            ObjectMapper mapper = new ObjectMapper();
                            List<RegionBean> regions = mapper.readValue(jsonArray, new TypeReference<List<RegionBean>>() {
                            });
                            DataUtil.setRegionData(regions);
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
