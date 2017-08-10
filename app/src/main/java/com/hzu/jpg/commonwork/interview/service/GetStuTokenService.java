package com.hzu.jpg.commonwork.interview.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.interview.activity.VideoStuHouseActivity;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by ThinkPad on 2017/6/23.
 */

public class GetStuTokenService extends Service {

    public static final String TAG="GetStuTokenService";

    private String studentToken="";
    private String status="";
    private Thread thread;
    private boolean flagThread;
    private int times=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startStuTokenThread();
    }

    private void startStuTokenThread(){
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                flagThread=true;
                while(flagThread){

                    Log.e(TAG,"stuToken="+studentToken);
                    if(times==0) {
                        updateStuToken();
                    }
                    if(studentToken!=null&&!studentToken.equals("")){
                        flagThread=false;
                        turnToStuVideoHouse();
                        stopSelf();
                        break;
                    }else if(MyApplication.user==null){
                        flagThread=false;
                        stopSelf();
                        break;
                    }else if(MyApplication.role==1){
                        flagThread=false;
                        stopSelf();
                        break;
                    }

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void turnToStuVideoHouse(){
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("studentToken",studentToken);
        intent.putExtra("status",status);
        intent.setClass(getApplicationContext(),VideoStuHouseActivity.class);
        startActivity(intent);
    }

    private void updateStuToken(){
        OkHttpUtils.post()
                .url(Config.URL_GET_STUDENT_TOKEN)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"updateStuToken error!");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,"times="+times+" "+"stuToken,response="+response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            studentToken=jsonObject.getString("tooken");
                            status=jsonObject.getString("status");
                            if (!studentToken.equals("")){
                                times++;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public boolean onUnbind(Intent intent) {
        flagThread=false;
        return super.onUnbind(intent);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        times=0;
        status="";
        studentToken="";
    }

    @Override
    public void onDestroy() {
        flagThread=false;
        super.onDestroy();
    }
}
