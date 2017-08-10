package com.hzu.jpg.commonwork.enity.moudle;

import android.app.Activity;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.google.gson.reflect.TypeToken;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.Bean.BorrowBean;
import com.hzu.jpg.commonwork.utils.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/3/29.
 */

public class BorrowRecordModel {

    Context context;
    RequestQueue queue;

    public BorrowRecordModel(Activity activity){
        context=activity;
        if (queue==null){
            queue= MyApplication.getQueue();
        }
    }

    public void getRecord(final OnBorrowRecordReceiveListener listener){
        String url= Config.IP+"/HRM/loan/loanRecord.html";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                List<BorrowBean> list= JsonUtil.Json2List(response,new TypeToken<List<BorrowBean>>(){});
                listener.onBorrowRecordReceive(list);
            }
        });
    }

    public interface OnBorrowRecordReceiveListener{
        void onBorrowRecordReceive(List<BorrowBean> list);
    }



}
