package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.action.RequestAction;
import com.hzu.jpg.commonwork.activity.home.MainActivity;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.event.LoginEvent;
import com.hzu.jpg.commonwork.interview.service.GetStuTokenService;
import com.hzu.jpg.commonwork.utils.Constants;
import com.hzu.jpg.commonwork.utils.DialogUtil;
import com.hzu.jpg.commonwork.utils.SharedPreferencesUtil;
import com.hzu.jpg.commonwork.utils.StringUtils;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.utils.Util;
import com.hzu.jpg.commonwork.utils.WxLoginUtils;
import com.hzu.jpg.commonwork.wxapi.WXEntryActivity;
import com.tencent.connect.UserInfo;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.hzu.jpg.commonwork.utils.WxLoginUtils.getWXAPI;

/**
 * Created by Administrator on 2017/4/1.
 */

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";

    @Bind(R.id.et_phoneNum)
    TextInputEditText etPhoneNum;
    @Bind(R.id.et_password)
    TextInputEditText etPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.ll_forgetPassword)
    LinearLayout llForgetPassword;
    String phoneNum;
    String password;

    public static Tencent mTencent;
    private UserInfo mInfo;
    private static boolean isServerSideLogin = false;
    private static Handler uiHandler = null;
    private static final int INIT_DATA_VIEW = 1001;
    private static RequestAction action;

    private static String loginRequestStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        ButterKnife.bind(this);

        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。

        mTencent = Tencent.createInstance(Config.QQ_APP_ID, this);
        // 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取

        action = new RequestAction(this);
        initHandler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.isLogin) {
            finish();
        }
    }


    public void wxLogin() {
        IWXAPI api;
        api = WXAPIFactory.createWXAPI(this, Config.APP_ID, false);
        Log.e("main", "wxLogin: ---------------");
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        api.sendReq(req);
    }

    @OnClick(R.id.wx_login_iv)
    public void wxLogin(View view) {
        wxLogin();
        //wxLogin("oSCAcwVHi003XHOL3_glNYm-ScRY");
    }

    @OnClick({R.id.btn_login, R.id.btn_register, R.id.ll_forgetPassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                phoneNum = etPhoneNum.getText().toString();
                password = etPassword.getText().toString();
                if (StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(phoneNum))
                    userLogin();
                else
                    ToastUtil.showToast("帐号或密码为空");
                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterSetPwdActivity.class));
                break;
            case R.id.ll_forgetPassword:
                Intent intent = new Intent(LoginActivity.this, RegisterSetPwdActivity.class);
                intent.putExtra("isSetPwd", true);
                startActivity(intent);
                break;
        }
    }

    private void userLogin() {
        /*NameValuePair username = new BasicNameValuePair(Config.KEY_USER_NAME, phoneNum);
        NameValuePair pwd = new BasicNameValuePair(Config.KEY_PASSWORD, password);
        List<NameValuePair> params = new ArrayList<>();
        params.add(username);
        params.add(pwd);
        String result = action.userLogin(params);

        try {
            JSONObject jsonObject = new JSONObject(result);
            boolean loginStatu = jsonObject.getBoolean("loginStatus");
            if (loginStatu) {
                String type = jsonObject.getString(Config.KEY_ACCOUNT_CLASS);
                Log.e(TAG, "onResponse: " + type, null);
                if (type.equals("com")) {
                    MyApplication.role = 1;
                    setResult(Config.LOGIN);
                    finish();
                } else {
                    MyApplication.role = 0;
                    setResult(Config.LOGIN);
                    finish();
                }
                getUserInfo(jsonObject);
            } else {
                ToastUtil.showToast("账户或密码有误");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "onResponse: " + e.getMessage(), null);
            ToastUtil.showToast("登录失败");
        }*/

        final AlertDialog dialog = DialogUtil.showLoadingDialog(this);
        OkHttpUtils.post().url(Config.URL_STUDENT_LOGIN)
                .addParams(Config.KEY_USER_NAME, phoneNum)
                .addParams(Config.KEY_PASSWORD, password)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: " + e.getMessage(), null);
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse: " + response, null);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean loginStatu = jsonObject.getBoolean("loginStatus");
                    if (loginStatu) {
                        String type = jsonObject.getString(Config.KEY_ACCOUNT_CLASS);
                        Log.e(TAG, "onResponse: " + type, null);
                        if (type.equals("com")) {
                            MyApplication.role = 1;
                            setResult(Config.LOGIN);
                            finish();
                        } else {
                            MyApplication.role = 0;
                            setResult(Config.LOGIN);
                            finish();
                        }
                        getUserInfo(jsonObject);
                    } else {
                        ToastUtil.showToast("账户或密码有误");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onResponse: " + e.getMessage(), null);
                    ToastUtil.showToast("登录失败");
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
            SharedPreferencesUtil.saveUserMsg(phoneNum, password);
            Log.e(TAG, "onResponse: " + user, null);
            startStuGetTokenService();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    //video:开启请求学生token服务
    private void startStuGetTokenService() {
        Log.e(TAG, "startStuGetTokenService");
        if (MyApplication.role == 0) {
            Intent intent = new Intent(this, GetStuTokenService.class);
            startService(intent);
        }
    }


    //---------------- QQ Login -------------//

    @OnClick(R.id.new_qq_login_iv)
    public void qqLogin(View view) {
        onClickLogin();

    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
            updateLoginButton();
        }
    };

    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
            isServerSideLogin = false;
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(this);
                mTencent.login(this, "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
            mTencent.logout(this);
            updateUserInfo();
            updateLoginButton();
        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        //mUserInfo.setVisibility(View.VISIBLE);
                        //mUserInfo.setText(response.getString("nickname"));
                        Log.d("nickname", response.getString("nickname"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    };

    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    new Thread() {

                        @Override
                        public void run() {
                            JSONObject json = (JSONObject) response;
                            if (json.has("figureurl")) {
                                Bitmap bitmap = null;
                                try {
                                    bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
                                } catch (JSONException e) {

                                }
                                Message msg = new Message();
                                msg.obj = bitmap;
                                msg.what = 1;
                                mHandler.sendMessage(msg);
                            }
                        }

                    }.start();
                }

                @Override
                public void onCancel() {

                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        }
    }

    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
                //0BD5662748249CD997DC1EB6FAACA25E
                System.out.print(mTencent.getOpenId());
                String string = mTencent.getOpenId();
                //ToastUtil.showToast(string);
                new getAndrlgByQqThread().startThread(string);
            }
        } catch (Exception e) {
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                //Util.showResultDialog(QQLoginActivity.this, "返回为空", "登录失败");
                ToastUtil.showToast("登录失败，返回为空");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                //Util.showResultDialog(QQLoginActivity.this, "返回为空", "登录失败");
                ToastUtil.showToast("登录失败，返回为空");
                return;
            }
            //Util.showResultDialog(QQLoginActivity.this, response.toString(), "登录成功");
            ToastUtil.showToast("登录成功");
            // 有奖分享处理
            //handlePrizeShare();
            doComplete((JSONObject) response);//解析json
        }

        protected void doComplete(JSONObject values) {
        }

        @Override
        public void onError(UiError e) {
            //Util.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
            //Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            //Util.toastMessage(LoginActivity.this, "onCancel: ");
            //Util.dismissDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "-->onActivityResult " + requestCode + " resultCode=" + resultCode);
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN ||
                requestCode == com.tencent.connect.common.Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateLoginButton() {
        if (mTencent != null && mTencent.isSessionValid()) {

        }
    }

    static class getAndrlgByQqThread implements Runnable {
        private Thread rthread = null;// 监听线程.
        private String openId;

        @Override
        public void run() {
            NameValuePair openId_app = new BasicNameValuePair("openId", openId);
            List<NameValuePair> params = new ArrayList<>();
            params.add(openId_app);
            loginRequestStr = action.andrlgByQqAction(params);
            uiHandler.sendEmptyMessage(INIT_DATA_VIEW);
        }

        public void startThread(String openId) {
            this.openId = openId;
            if (rthread == null) {
                rthread = new Thread(this);
                rthread.start();
            }
        }
    }

    private void initHandler() {
        uiHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case INIT_DATA_VIEW:
                        if (!loginRequestStr.equals("")) {
                            if (Constants.isOneQQlogin) { //第一次使用QQ登录 跳转到绑定
                                Intent intent = new Intent(LoginActivity.this, MobileBindingActivity.class);
                                intent.putExtra("openId", mTencent.getOpenId());
                                startActivity(intent);
                                finish();
                            } else { //已经绑定过了
                                try {
                                    JSONObject jsonObject = new JSONObject(loginRequestStr);
                                    getUserInfo(jsonObject);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                        break;
                }
            }
        };
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
                                Intent intent = new Intent(LoginActivity.this, MobileBindingActivity.class);
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


}
