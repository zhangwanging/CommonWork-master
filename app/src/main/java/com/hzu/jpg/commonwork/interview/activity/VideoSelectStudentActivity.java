package com.hzu.jpg.commonwork.interview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.LoginActivity;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.interview.adapter.VideoSelectStuAdapter;
import com.hzu.jpg.commonwork.interview.bean.VideoStudentBean;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.Call;

/**
 * Created by zw on 2017/5/8.
 *
 */

public class VideoSelectStudentActivity extends AppCompatActivity {
    public static final String TAG="VideoSelectStudent";

    @Bind(R.id.lv_vedio_companyselection)
    ListView listView;
    @Bind(R.id.tv_emptyview)
    TextView tv_emptyView;
    @Bind(R.id.ll_emptyview)
    LinearLayout ll_emptyview;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private ArrayList<VideoStudentBean> list;
    private VideoSelectStuAdapter adapter;

    private String companyToken="";
    private String status="";

    private boolean flagInitOneTime;
    private boolean flagThread=true;
    private volatile boolean flagRequestFinish;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_selectcompany);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        flagInitOneTime=true;

        list=new ArrayList<VideoStudentBean>();
        adapter=new VideoSelectStuAdapter(this,list);

        listView.setEmptyView(ll_emptyview);
        swipeLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorAccent);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchCompany();
            }
        });
        swipeLayout.setRefreshing(true);
        searchCompany();
    }

    private void  searchCompany(){
        if(MyApplication.user==null){
            ToastUtil.showToast("登录后才可以进行视频面试哦~");
            Intent intent=new Intent(this,LoginActivity.class);
            this.startActivity(intent);
            return;
        }
        list.clear();
        ll_emptyview.setVisibility(View.GONE);
        tv_emptyView.setText("您没有未面试的学生呀~");
        OkHttpUtils.post().url(Config.URL_GET_STUDENT_LIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        tv_emptyView.setText("网络有点问题，未成功加载数据呀~");
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                        swipeLayout.setRefreshing(false);
                        ToastUtil.showNetError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Log.e(TAG,"studentlist response="+response);
                            swipeLayout.setRefreshing(false);
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                                String studentName=jsonObject.getString("studentName");
                                String studentId=jsonObject.getString("studentId");
                                String studentStatus=jsonObject.getString("online");
                                VideoStudentBean bean=new VideoStudentBean();
                                bean.setId(studentId);
                                bean.setName(studentName);
                                if(studentStatus.equals("off")){
                                    bean.setOnlineStatus("离线");
                                }else{
                                    bean.setOnlineStatus("在线");
                                }
                                list.add(bean);
                            }
                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnItemClick(R.id.lv_vedio_companyselection)
    public void onClick(int position){
        String studentStatus=list.get(position).getOnlineStatus();
        if(studentStatus.equals("离线")){
            ToastUtil.showToast_center("学生处于离线状态，不能进行视频面试~");
        }else {
            final String studentId = list.get(position).getId();
            updateCompanyToken(studentId);
        }
    }

    private void updateCompanyToken(final String studentId){
        OkHttpUtils.post()
                .url(Config.URL_GET_COMPANY_TOKEN)
                .addParams("studentId",studentId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showNetError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Log.e(TAG,"studentId="+studentId+"  updateToken.response="+response);
                            JSONObject jsonObject=new JSONObject(response);
                            companyToken=jsonObject.getString("tooken");
                            status=jsonObject.getString("status");

                            if(status.equals("start")){
                                Intent intent=new Intent(VideoSelectStudentActivity.this,VideoCpyHouseActivity.class);
                                intent.putExtra("companyToken",companyToken);
                                intent.putExtra("studentId",studentId);
                                startActivity(intent);
                            }else if(status.equals("stop")){
                                ToastUtil.showToast_center("后台停止面试~");
                            }else if(status.equals("wait")){
                                ToastUtil.showToast_center("后台未开始面试~");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @OnClick(R.id.back)
    public void backOnClick(){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!flagInitOneTime) {
            swipeLayout.setRefreshing(true);
            searchCompany();
        }else{
            flagInitOneTime=false;
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}