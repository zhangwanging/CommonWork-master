package com.hzu.jpg.commonwork.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzu.jpg.commonwork.activity.LoginActivity;
import com.hzu.jpg.commonwork.activity.MobileBindingActivity;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.Bean.WeChatInfo;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.event.LoginEvent;
import com.hzu.jpg.commonwork.interview.service.GetStuTokenService;
import com.hzu.jpg.commonwork.utils.Constants;
import com.hzu.jpg.commonwork.utils.OkHttpUtils;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/25.IWXAPIEventHandler
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果没回调onResp，八成是这句没有写
        IWXAPI api;
        api = WXAPIFactory.createWXAPI(this, Config.APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        Log.e("main", "onResp: " + resp.errStr + "错误码 : " + resp.errCode + "");
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) {
                    Log.e("main", "onResp: 分享失败");
                } else {
                    Log.e("main", "onResp: 登录失败");
                }
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        Log.e("main", "onResp: ------------code = " + code);
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        getUnionID(code);
                        //ToastUtil.showToast("resp----(" + resp.openId + ")---code ----(" + code + ")");
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        Log.e("main", "onResp: 微信分享成功");
                        finish();
                        break;
                }
                break;
        }
    }


    public void getUnionID(String code) {
        com.zhy.http.okhttp.OkHttpUtils.get()
                .url("https://api.weixin.qq.com/sns/oauth2/access_token")//vehicle
                .addParams("appid", Config.APP_ID)
                .addParams("secret", Config.APP_SECRET)
                .addParams("code", code)
                .addParams("grant_type", "authorization_code")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("LoginActivity", "error=" + e);
                        ToastUtil.showToast("请求失败，请检查网络是否可用");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("LoginActivity", "token.response=" + response);
                        //ToastUtil.showToast(response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.has("unionid")) {
                               // ToastUtil.showToast(json.getString("unionid"));
                                /**********************************************/
                                wxLogin(json.getString("unionid"));
                            } else {
                                ToastUtil.showToast("获取失败，请重新登录");
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void wxLogin(final String unionid) {
        com.zhy.http.okhttp.OkHttpUtils.post()
                .url("https://www.jiongzhiw.com/HRM/thirdPart/af.html")//vehicle
                .addParams("unionId", unionid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("LoginActivity", "error=" + e);
                        ToastUtil.showToast("请求失败，请检查网络是否可用");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("LoginActivity", "token.response=" + response);
                        //ToastUtil.showToast(response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.has("wcLgStatus")) { //第一次登录
                                Intent intent = new Intent(WXEntryActivity.this, MobileBindingActivity.class);
                                intent.putExtra("unionId", unionid);
                                startActivity(intent);
                                finish();
                            } else {  //等二次登录
                                getUserInfo(json);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void getUserInfo(JSONObject jsonObject) {
        try {
            Gson gson = new Gson();
            User user = gson.fromJson(jsonObject.getJSONArray("accountInfo").getJSONObject(0).toString(), User.class);
            MyApplication.user = user;
            Constants.isLogin = true;
            MyApplication.user.setOwnAgentId(jsonObject.getInt("ownAgentId"));
            MyApplication.user.setOwnAgentStatus(jsonObject.getInt("ownAgentStatus"));
            EventBus.getDefault().post(new LoginEvent(true));
            //SharedPreferencesUtil.saveUserMsg(phoneNum, password);
            //Log.e(TAG, "onResponse: " + user, null);
            startStuGetTokenService();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //video:开启请求学生token服务
    private void startStuGetTokenService() {
        // Log.e(TAG, "startStuGetTokenService");
        if (MyApplication.role == 0) {
            Intent intent = new Intent(this, GetStuTokenService.class);
            startService(intent);
        }
    }


}