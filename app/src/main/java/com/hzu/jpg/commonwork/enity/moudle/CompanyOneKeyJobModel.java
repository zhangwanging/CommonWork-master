package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.hzu.jpg.commonwork.Receiver.CompanyOneKeyJobReceiver;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.Bean.CompanyOneKeyJobBean;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/4/10.
 */

public class CompanyOneKeyJobModel {

    private RequestQueue queue;
    Context context;

    public CompanyOneKeyJobModel(Context context){
        this.context=context;
        if (queue==null){
            queue= MyApplication.getQueue();
        }
    }


    public void getData(final OnCompanyOneKeyJobReceiveListener listener,int start){
        String url= Config.IP+"/HRM/capply/list.html";
        OkHttpUtils.get().url(url).addParams("start", String.valueOf(start)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                CompanyOneKeyJobReceiver receiver = gson.fromJson(response, CompanyOneKeyJobReceiver.class);
                if (receiver.getStatu().equals("1")) {
                    listener.onCompanyOneKeyJobReceive(receiver.getData());
                } else {
                    ToastUtil.showToast(receiver.getMessage());
                }
            }
        });

    }

    public void Delete(final String sId,final OnCompanyOneKeyJobDeleteListener listener){
        String url= Config.IP+"/HRM/capply/delete.html";
        OkHttpUtils.get().url(url).addParams("id", String.valueOf(sId)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
               listener.onCompanyOneKeyJobDelete(sId);
            }
        });
    }

    public interface OnCompanyOneKeyJobReceiveListener{
        void onCompanyOneKeyJobReceive(List<CompanyOneKeyJobBean> list);
    }
    public interface OnCompanyOneKeyJobDeleteListener{
        void onCompanyOneKeyJobDelete(String id);
    }
}
