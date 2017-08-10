package com.hzu.jpg.commonwork.enity.moudle;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.hzu.jpg.commonwork.Receiver.MyListJobInfoReceiver;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.Bean.ListJobInfoBean;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/3/1.
 */

public class MyListJobModel {

    RequestQueue queue;
    Context context;

    public MyListJobModel(Activity activity) {
        context=activity;
        if (queue==null){
            queue= MyApplication.getQueue();
        }
    }

    public void getListJobInfo(final OnMyListJobReceiveListener listener){
        String url= Config.IP+"/HRM/uac/showRecordA.html";
//        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Gson gson=new Gson();
//                Log.d("response",s);
//                ListJobInfoReceiver receiver=gson.fromJson(s,ListJobInfoReceiver.class);
//                listener.onMyListJobReceive(receiver.getData());
//            }
//        }, new NetworkErrorListener(context));
//        queue.add(stringRequest);

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("listjob","aaaaa"+response);
                if (response != null && !response.equals("")) {
                    Gson gson=new Gson();
                    MyListJobInfoReceiver receiver=gson.fromJson(response,MyListJobInfoReceiver.class);
                    if(receiver.getStatu().equals("1")){
                        listener.onMyListJobReceive(receiver.getData());
                    }else{
                        ToastUtil.showToast(receiver.getMessage());
                    }
                }
            }
        });
    }

    public interface OnMyListJobReceiveListener{
        void onMyListJobReceive(List<ListJobInfoBean> list);
    }
}
