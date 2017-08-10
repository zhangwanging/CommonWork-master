package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;


/**
 * Created by Administrator on 2017/3/28.
 */

public class MyInfoModel {

    Context context;

    public MyInfoModel(Context context) {
        this.context = context;
    }

    public void setData(OnMyInfoReceiveListener listener){
        listener.onMyInfoReceive(MyApplication.user);

    }

    public void getBitmap(String path,final onMyInfoBitmapReceiveListener listener){
        String url= Config.IP+path;
        Log.d("bm url",url);
        ImageRequest imageRequest = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        listener.onBitmapReceive(response);
                    }
                }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFail(error.getMessage());
            }
        });
    }

    public interface OnMyInfoReceiveListener{
        void onMyInfoReceive(User user);
    }

    public interface onMyInfoBitmapReceiveListener{
        void onBitmapReceive(Bitmap bm);

        void onFail(String message);
    }

}
