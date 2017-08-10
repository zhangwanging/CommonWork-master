package com.hzu.jpg.commonwork.enity.moudle;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.google.gson.reflect.TypeToken;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.Bean.SalaryFormBean;
import com.hzu.jpg.commonwork.utils.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/3/27.
 */

public class SalaryFormModel {

    String url = Config.IP + "/HRM/salarys/selectSalary.html";

    Context context;
    RequestQueue queue;

    public SalaryFormModel(Activity activity) {
        context = activity;
        if (queue == null) {
            queue = MyApplication.getQueue();
        }
    }

    public void getSalaryForms(final OnSalaryFormResultListener listener) {
//        StringRequest stringRequest = new StringRequest(url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if(response!=null&&!response.equals("")){
//                            List<SalaryFormBean> list=JsonUtil.Json2List(response,new TypeToken<List<SalaryFormBean>>(){});
//                            listener.onSuccess(list);
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        });
//        queue.add(stringRequest);

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if (response != null && !response.equals("")) {
                    Log.d("salaryForm",response);
                    List<SalaryFormBean> list = JsonUtil.Json2List(response, new TypeToken<List<SalaryFormBean>>() {});
                    listener.onSuccess(list);
                }
            }
        });
    }

    public interface OnSalaryFormResultListener {
        void onSuccess(List<SalaryFormBean> list);
    }

}
