package com.hzu.jpg.commonwork.enity.moudle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.hzu.jpg.commonwork.Receiver.MyJobInfoReceiver;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.Bean.MyJobInfoBean;
import com.hzu.jpg.commonwork.utils.NetworkErrorListener;


/**
 * Created by Administrator on 2017/4/2.
 */

public class MyJobInfoModel {

    Context context;
    RequestQueue queue;

    public MyJobInfoModel(Activity activity){
        context=activity;
        if (queue==null){
            queue= MyApplication.getQueue();
        }
    }

    public void getJobInfo(final OnJobInfoReceiveListener listener, String id){
        String url= Config.IP+"/HRM/job/showAllInfoA.html?id="+id;
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson=new Gson();
                        MyJobInfoReceiver receiver=gson.fromJson(response,MyJobInfoReceiver.class);
                        if(receiver.getStatu().equals("1")){
                            listener.onJobInfoReceive(receiver.getData());
                        }else{
                            Toast.makeText(context,receiver.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new NetworkErrorListener(context));
        queue.add(stringRequest);
    }

    public void getIcon(final OnJobInfoIconReceiveListener listener, String iconUrl){
        String url=Config.IP+iconUrl;
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        listener.onJobInfoIconReceive(response);
                    }
                }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onJobInfoIconFail();
            }
        });
        queue.add(imageRequest);
    }

    public interface OnJobInfoReceiveListener{
        void onJobInfoReceive(MyJobInfoBean bean);
    }

    public interface OnJobInfoIconReceiveListener{
        void onJobInfoIconReceive(Bitmap bitmap);

        void onJobInfoIconFail();
    }
}
