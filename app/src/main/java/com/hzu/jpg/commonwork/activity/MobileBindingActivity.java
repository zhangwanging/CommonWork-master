package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.event.LoginEvent;
import com.hzu.jpg.commonwork.interview.service.GetStuTokenService;
import com.hzu.jpg.commonwork.utils.Constants;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MobileBindingActivity extends AppCompatActivity {

    private static final String TAG = "MobileBindingActivity";

    @Bind(R.id.edit_mobile)
    EditText editMobile;
    @Bind(R.id.edit_code)
    EditText editCode;
    @Bind(R.id.bt_code)
    Button btCode;
    @Bind(R.id.bt_binding)
    Button btBinding;
    String telephone;
    @Bind(R.id.binding_user)
    Button bindingUser;
    @Bind(R.id.back_iv)
    ImageView backIv;
    private Handler handler = new Handler();
    int second = 60;
    private String openId = "";
    private String unionId = "";

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (second == 0) {
                btCode.setEnabled(true);
                btCode.setText("获取验证码");
                handler.removeCallbacks(this);
                second = 60;
                return;
            }
            btCode.setEnabled(false);
            btCode.setText(second + "");
            second--;
            handler.postDelayed(this, 1000);
        }
    };

    @OnClick(R.id.binding_user)
    public void bindingUser() {
        Intent intent = new Intent(new Intent(MobileBindingActivity.this, BindingUserActivity.class));
        intent.putExtra("openId", openId);
        intent.putExtra("unionId", unionId);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_binding);
        ButterKnife.bind(this);
        openId = this.getIntent().getStringExtra("openId");
        unionId = this.getIntent().getStringExtra("unionId");

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.bt_code)
    public void btCode() {
        telephone = editMobile.getText().toString();
        if (telephone.length() == 11) {
            handler.post(runnable);
            OkHttpUtils.post().url(Config.URL_GET_CODE)
                    .addParams(Config.KEY_TELEPHONE, editMobile.getText().toString())
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtil.showToast("网络连接错误~");
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.e(TAG, "onResponse: " + response, null);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String msg = jsonObject.getString("status");
                        ToastUtil.showToast(msg);
                    } catch (JSONException e) {
                        ToastUtil.showToast("网络连接错误~");
                        e.printStackTrace();
                    }
                }
            });
        } else
            ToastUtil.showToast("手机格式不正确");
    }

    @OnClick(R.id.bt_binding)
    public void btBinding() {
        if (checkMsg()) {
            if (openId != null && !openId.equals("")) {
                getTaskData();
            } else if (unionId != null && !unionId.equals("")) {
                getWxbindData();
            }

        }
    }

    private boolean checkMsg() {
        if (editMobile.getText().toString().length() != 11) {
            ToastUtil.showToast("手机格式不正确");
            return false;
        } else if (editCode.getText().toString().equals("")) {
            ToastUtil.showToast("请输入验证码");
            return false;
        }
        return true;
    }

    public void getTaskData() {
        OkHttpUtils.post()
                .url("https://www.jiongzhiw.com/HRM/thirdPart/bnewAcc.html?method=qq")//vehicle
                .addParams("telephone", editMobile.getText().toString())
                .addParams("code", editCode.getText().toString())
                .addParams("agentCode", "")
                .addParams("openId", openId)
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
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.has("boundStatus")) {
                                if (json.getString("boundStatus").equals("-1")) {
                                    ToastUtil.showToast("验证码超时");
                                } else if (json.getString("boundStatus").equals("0")) {
                                    ToastUtil.showToast("绑定失败");
                                } else if (json.getString("boundStatus").equals("1")) {
                                    ToastUtil.showToast("绑定成功");
                                    getUserInfo(json);
                                    finish();
                                } else if (json.getString("boundStatus").equals("2")) {
                                    ToastUtil.showToast("手机与短信验证码不匹配");
                                } else if (json.getString("boundStatus").equals("3")) {
                                    ToastUtil.showToast("账号已存在");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void getWxbindData() {
        OkHttpUtils.post()
                .url("https://www.jiongzhiw.com/HRM/thirdPart/bnewAcc.html?method=wc")//vehicle
                .addParams("telephone", editMobile.getText().toString())
                .addParams("code", editCode.getText().toString())
                .addParams("agentCode", "")
                .addParams("unionId", unionId)
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
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.has("boundStatus")) {
                                if (json.getString("boundStatus").equals("-1")) {
                                    ToastUtil.showToast("验证码超时");
                                } else if (json.getString("boundStatus").equals("0")) {
                                    ToastUtil.showToast("绑定失败");
                                } else if (json.getString("boundStatus").equals("1")) {
                                    ToastUtil.showToast("绑定成功");
                                    getUserInfo(json);
                                    finish();
                                } else if (json.getString("boundStatus").equals("2")) {
                                    ToastUtil.showToast("手机与短信验证码不匹配");
                                } else if (json.getString("boundStatus").equals("3")) {
                                    ToastUtil.showToast("账号已存在");
                                }
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
            Log.e(TAG, "onResponse: " + user, null);
            startStuGetTokenService();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //video:开启请求学生token服务
    private void startStuGetTokenService() {
        Log.e(TAG, "startStuGetTokenService");
        if (MyApplication.role == 0) {
            Intent intent = new Intent(this, GetStuTokenService.class);
            startService(intent);
        }
    }

}
