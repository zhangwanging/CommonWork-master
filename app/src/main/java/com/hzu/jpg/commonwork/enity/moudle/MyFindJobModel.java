package com.hzu.jpg.commonwork.enity.moudle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Administrator on 2017/3/1.
 */

public class MyFindJobModel {

    RequestQueue queue;

    public MyFindJobModel(RequestQueue queue) {
        this.queue = queue;
    }

    public void getListJobInfo(Response.Listener listener, Response.ErrorListener errorListener){
        String url="http://10.0.2.2:8080/StudentJob/MyServlet?action=getListJobInfos&ID=1000";
        StringRequest stringRequest=new StringRequest(url, listener,errorListener);
        queue.add(stringRequest);
    }
}
