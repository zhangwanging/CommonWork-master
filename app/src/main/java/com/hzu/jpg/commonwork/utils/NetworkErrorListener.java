package com.hzu.jpg.commonwork.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2017/3/2.
 */

public class NetworkErrorListener implements Response.ErrorListener {
    Context context;

    public NetworkErrorListener(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Toast.makeText(context,"网络异常，加载失败",Toast.LENGTH_LONG).show();
    }
}
