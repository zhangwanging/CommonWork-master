package com.hzu.jpg.commonwork.enity.moudle;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


/**
 * Created by Administrator on 2017/2/27.
 */

public class PacketModel {

    RequestQueue queue;

    public PacketModel(RequestQueue queue) {
        this.queue = queue;
    }

    public void getSalaryForm(Response.Listener listener, Response.ErrorListener errorListener){
        String url="http://10.0.2.2:8080/StudentJob/MyServlet?action=getSalaryForms&ID=1000";
        Log.d("network",url);
        StringRequest stringRequest=new StringRequest(url, listener,errorListener);
        queue.add(stringRequest);
    }

    public void getBorrow(Response.Listener listener, Response.ErrorListener errorListener){
        StringRequest stringRequest=new StringRequest("", listener,errorListener);
    }

}
