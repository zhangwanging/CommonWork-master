package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.hzu.jpg.commonwork.Receiver.OneKeyJobReceiver;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.Bean.OneKeyJobBean;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/3/31.
 */

public class MyOneKeyJobModel {

    private RequestQueue queue;
    Context context;

    public MyOneKeyJobModel(Context context){
        this.context=context;
        if (queue==null){
            queue= MyApplication.getQueue();
        }
    }


    public void getData(final OnMyOneKeyJobReceiveListener listener){
        String url= Config.IP+"/HRM/UserApply/showOneRecordA.html";
//        StringRequest stringRequest = new StringRequest(url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson=new Gson();
//                        Log.d("response",response);
//                        OneKeyJobReceiver receiver=gson.fromJson(response, OneKeyJobReceiver.class);
//                        listener.onMyOneKeyJobReceive(receiver.getData());
//                    }
//                }, new NetworkErrorListener(context));
//        queue.add(stringRequest);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if (response != null && !response.equals("")) {
                    OneKeyJobReceiver receiver=new Gson().fromJson(response,OneKeyJobReceiver.class);
                   if(receiver.getStatu().equals("1"))
                    listener.onMyOneKeyJobReceive(receiver.getData());
                    else
                       ToastUtil.showToast(receiver.getMessage());
                }
            }
        });


    }

    public interface OnMyOneKeyJobReceiveListener{
        void onMyOneKeyJobReceive(List<OneKeyJobBean> list);
    }
}
