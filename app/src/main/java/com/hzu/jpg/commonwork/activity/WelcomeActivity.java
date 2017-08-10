package com.hzu.jpg.commonwork.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.google.gson.Gson;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.event.AddressEvent;
import com.hzu.jpg.commonwork.interview.service.GetStuTokenService;
import com.hzu.jpg.commonwork.service.LocationService;
import com.hzu.jpg.commonwork.utils.SharedPreferencesUtil;
import com.hzu.jpg.commonwork.utils.StringUtils;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/19.
 */

public class WelcomeActivity extends Activity{
    private static final int TO_MAIN_ACTIVITY = 0;
    private static final String TAG = "WelcomeActivity";
    private LocationService locationService;

    private static class MyHandler extends Handler {
        private WeakReference<WelcomeActivity> activityWeakReference;

        public MyHandler(WelcomeActivity activity) {
            activityWeakReference = new WeakReference<WelcomeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            WelcomeActivity activity = activityWeakReference.get();
            if (activity != null) {
                if (msg.what == 0) {
                    activity.autoLogin();
                }
            }
        }
    }
    private MyHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        starLocationService();
        mHandler = new MyHandler(this);
        mHandler.sendEmptyMessageDelayed(0, 2500);
    }

    public void autoLogin(){
        String[] userMsg = SharedPreferencesUtil.getUserMsg();
        final String phoneNum = userMsg[0];
        final String password = userMsg[1];
        if(StringUtils.isNotEmpty(userMsg[0]) && StringUtils.isNotEmpty(userMsg[1])){
            OkHttpUtils.post().url(Config.URL_STUDENT_LOGIN)
                    .addParams(Config.KEY_USER_NAME,phoneNum)
                    .addParams(Config.KEY_PASSWORD,password)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.e(TAG, "onError: " + e.getMessage(), null);
                    turnToMainActivity();
                    MyApplication.user = null;
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        Log.e(TAG, "onResponse: " + response, null);
                        JSONObject jsonObject = new JSONObject(response);
                        boolean loginStatu = jsonObject.getBoolean("loginStatus");
                        if (loginStatu) {
                            Log.e(TAG, "onResponse: 自动登录成功", null);
                            String type = jsonObject.getString(Config.KEY_ACCOUNT_CLASS);
                            if(type.equals("com")){
                                MyApplication.role = 1;
                            }else{
                                MyApplication.role = 0;
                            }
                            Gson gson = new Gson();
                            User user = gson.fromJson(jsonObject.getJSONArray("accountInfo").getJSONObject(0).toString(), User.class);
                            MyApplication.user = user;
                            MyApplication.user.setOwnAgentId(jsonObject.getInt("ownAgentId"));
                            MyApplication.user.setOwnAgentStatus(jsonObject.getInt("ownAgentStatus"));
                            SharedPreferencesUtil.saveUserMsg(phoneNum, password);
                            Log.e(TAG, "onResponse: " + user, null);
                            startStuGetTokenService();
                            turnToMainActivity();
                        } else {
                            ToastUtil.showToast("自动登录失败");
                            MyApplication.user = null;
                            turnToMainActivity();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: " + e.getMessage(), null);
                        MyApplication.user = null;
                        turnToMainActivity();
                    }
                }
            });
        }else{
            turnToMainActivity();
            MyApplication.user = null;
        }
    }

    private void turnToMainActivity(){
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }

    private void starLocationService() {
        // -----------location config ------------
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例
        locationService.registerListener(mListener);
        //注册监听
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
    }
    /*****
     * 定位结果回调
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                String city = location.getCity();

                Log.e(TAG, "onReceiveLocation: " + location.getCity(), null);

                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // gps定位成功
                    Config.LOCATION_CITY = city;
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位成功
                    Config.LOCATION_CITY = city;
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位成功，离线定位结果也是有效的
                    Config.LOCATION_CITY = city;
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    // 服务端网络定位失败
                    ToastUtil.showToast("无法获取当前定位");
                    return;
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    // 网络不通导致定位失败，请检查网络是否通畅
                    ToastUtil.showToast("无法获取当前定位");
                    return;
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    // 无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机
                    ToastUtil.showToast("无法获取当前定位");
                    return;
                }

                Config.LOCATION_CITY = location.getCity();
                Config.SELECTED_CITY = location.getCity();
                locationService.unregisterListener(mListener); //定位成功后注销定位监听
                locationService.stop(); //定位成功后停止定位服务

                EventBus.getDefault().post(new AddressEvent(location.getCity()));
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    };

    @Override
    protected void onDestroy() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onDestroy();
    }

    //video:开启请求学生token服务
    private void startStuGetTokenService(){
        Log.e(TAG,"startStuGetTokenService");
        if(MyApplication.role==0) {
            Intent intent = new Intent(this, GetStuTokenService.class);
            startService(intent);
        }
    }
}
