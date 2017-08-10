package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.utils.NetworkErrorListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/3/24.
 */

public class BorrowModel {

    Context context;
    RequestQueue queue;

    public BorrowModel(Context context) {
        this.context = context;
        if(queue==null)
            queue= MyApplication.getQueue();
    }

    public void borrow(final String money, String name, String company, String id, String purpose, final Response.Listener<String> listener){

        String url= Config.IP+"/HRM/loan/applyLoan.html";

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,listener, new NetworkErrorListener(context)) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("loanamount", money);
//                map.put("borrowname", name);
//                map.put("workcompany",company);
//                map.put("jobnumber",id);
//                map.put("purpose",purpose);
//                return map;
//            }
//        };
//        queue.add(stringRequest);
        OkHttpUtils.get().url(url)
                .addParams("loanamount", money)
                .addParams("borrowname", name)
                .addParams("workcompany",company)
                .addParams("jobnumber",id)
                .addParams("purpose",purpose)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (e!=null)
                    e.printStackTrace();
                new NetworkErrorListener(context);
            }

            @Override
            public void onResponse(String response, int id) {
                listener.onResponse(response);
            }
        });

    }



}
