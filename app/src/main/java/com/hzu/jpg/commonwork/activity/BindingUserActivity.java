package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.event.LoginEvent;
import com.hzu.jpg.commonwork.interview.service.GetStuTokenService;
import com.hzu.jpg.commonwork.utils.Constants;
import com.hzu.jpg.commonwork.utils.SharedPreferencesUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class BindingUserActivity extends AppCompatActivity {

    @Bind(R.id.edit_mobile)
    EditText editMobile;
    @Bind(R.id.edit_code)
    EditText editCode;
    @Bind(R.id.bt_binding)
    Button btBinding;
    @Bind(R.id.binding_user)
    Button bindingUser;
    private static final String TAG = "BindingUserActivity";
    @Bind(R.id.back_iv)
    ImageView backIv;
    private String openId = "";
    private String unionId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_user);
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

    @OnClick({R.id.bt_binding, R.id.binding_user})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.bt_binding:
                if (checkMsg()) {
                    if (openId != null && !openId.equals("")) {
                        getTaskData();
                    } else if (unionId != null && !unionId.equals("")) {
                        getWxbindData();
                    }

                }
                break;
            case R.id.binding_user:
                Intent intent = new Intent(BindingUserActivity.this, MobileBindingActivity.class);
                intent.putExtra("openId", openId);
                intent.putExtra("unionId", unionId);
                startActivity(intent);
                finish();
                break;

        }
    }

    public void getTaskData() {
        OkHttpUtils.post()
                .url("https://www.jiongzhiw.com/HRM/thirdPart/boundQq.html?method=qq")//vehicle
                .addParams("username", editMobile.getText().toString())
                .addParams("password", editCode.getText().toString())
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
                                if (json.getString("boundStatus").equals("0")) {
                                    ToastUtil.showToast("绑定失败");
                                } else if (json.getString("boundStatus").equals("1")) {
                                    ToastUtil.showToast("绑定成功");
                                    getUserInfo(json);
                                    finish();
                                } else if (json.getString("boundStatus").equals("2")) {
                                    ToastUtil.showToast("绑定超时");
                                } else if (json.getString("boundStatus").equals("3")) {
                                    ToastUtil.showToast("账号或密码错误");
                                } else if (json.getString("boundStatus").equals("-1")) {
                                    ToastUtil.showToast("未注册用户");
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
                .url("https://www.jiongzhiw.com/HRM/thirdPart/boundWc.html")//vehicle
                .addParams("username", editMobile.getText().toString())
                .addParams("password", editCode.getText().toString())
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
                                if (json.getString("boundStatus").equals("0")) {
                                    ToastUtil.showToast("绑定失败");
                                } else if (json.getString("boundStatus").equals("1")) {
                                    ToastUtil.showToast("绑定成功");
                                    getUserInfo(json);
                                    finish();
                                } else if (json.getString("boundStatus").equals("2")) {
                                    ToastUtil.showToast("绑定超时");
                                } else if (json.getString("boundStatus").equals("3")) {
                                    ToastUtil.showToast("账号或密码错误");
                                } else if (json.getString("boundStatus").equals("-1")) {
                                    ToastUtil.showToast("未注册用户");
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
            SharedPreferencesUtil.saveUserMsg(editMobile.getText().toString(), editCode.getText().toString());
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

    private boolean checkMsg() {
        if (editMobile.getText().toString().equals("")) {
            ToastUtil.showToast("请输入账号");
            return false;
        } else if (editCode.getText().toString().equals("")) {
            ToastUtil.showToast("请输入密码");
            return false;
        }
        return true;
    }
}
