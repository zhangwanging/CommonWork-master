package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.utils.StringUtils;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/4/1.
 */

public class RegisterSetPwdActivity extends AppCompatActivity {

    private static final String TAG = "RegisterSetPwdActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.edit_mobile)
    EditText editMobile;
    @Bind(R.id.bt_getyanzheng)
    Button btGetyanzheng;
    @Bind(R.id.et_yanzheng)
    EditText etYanzheng;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.et_agent_id)
    EditText etAgentId;
    @Bind(R.id.btn_show_password)
    Button btnShowPassword;
    @Bind(R.id.btn_sure)
    Button btnSure;
    String telephone;
    String pwd;
    String code;
    String agentId;
    String companyName;
    boolean isCompanyRegister = false;
    boolean isSetPwd = false;

    int second = 60;
    @Bind(R.id.et_companyName)
    EditText etCompanyName;
    @Bind(R.id.tv_toCompanyRegister)
    TextView tvToCompanyRegister;
    @Bind(R.id.ll_companyName)
    LinearLayout llCompanyName;
    @Bind(R.id.ll_switch)
    LinearLayout llSwitch;

    private String token;


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (second == 0) {
                btGetyanzheng.setEnabled(true);
                btGetyanzheng.setText("获取验证码");
                handler.removeCallbacks(this);
                second = 60;
                return;
            }
            btGetyanzheng.setEnabled(false);
            btGetyanzheng.setText(second + "");
            second--;
            handler.postDelayed(this, 1000);
        }
    };

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter_set_pwd);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        isSetPwd = getIntent().getBooleanExtra("isSetPwd", false);
        if (isSetPwd) {
            toolbar.setTitle("修改密码");
            llSwitch.setVisibility(View.GONE);
        } else {
            toolbar.setTitle("注册帐号");
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getCodeToken() {
        OkHttpUtils.post().url(Config.IP + "/HRM/token/gtToken.html")
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
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        String data = jsonObject.getString("data");
                        JSONObject json = new JSONObject(data);
                        token = json.getString("token");
                        if (token != null && !token.equals("")) {
                            telephone = editMobile.getText().toString();
                            if (telephone.length() == 11) {
                                handler.post(runnable);
                                OkHttpUtils.post().url(Config.IP + "/HRM/msg/gtMsgTerminal.html")
                                        .addParams(Config.KEY_TELEPHONE, editMobile.getText().toString())
                                        .addParams("token", token)
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
                            } else {
                                ToastUtil.showToast("手机格式不正确");
                            }
                        }
                    }
                } catch (JSONException e) {
                    ToastUtil.showToast("网络连接错误~");
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.bt_getyanzheng, R.id.btn_sure, R.id.btn_show_password, R.id.tv_toCompanyRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_getyanzheng:
                telephone = editMobile.getText().toString();
                if (telephone.length() == 11) {
                    getCodeToken();
                } else
                    ToastUtil.showToast("手机格式不正确");
                break;
            case R.id.btn_sure:
                if (checkMsg()) {
                    if (isSetPwd) {
                        resetPwd();
                    } else if (!isSetPwd && isCompanyRegister) {
                        companyRegister();
                    } else if (!isSetPwd && !isCompanyRegister) {
                        studentRegister();
                    }
                }
                break;
            case R.id.btn_show_password:
                if (etPwd.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD + 1) {
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnShowPassword.setBackgroundResource(R.mipmap.ic_password_show);
                } else {
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnShowPassword.setBackgroundResource(R.mipmap.ic_password_normal);
                }
                break;
            case R.id.tv_toCompanyRegister:
                if (isCompanyRegister) {
                    //切换为学生注册
                    isCompanyRegister = false;
                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.left_out);
                    llCompanyName.setAnimation(animation);
                    tvToCompanyRegister.setAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));
                    llCompanyName.setVisibility(View.GONE);
                    tvToCompanyRegister.setText("切换为企业注册");
                } else {
                    //切换为企业注册
                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.left_in);
                    llCompanyName.setAnimation(animation);
                    llCompanyName.setVisibility(View.VISIBLE);
                    isCompanyRegister = true;
                    tvToCompanyRegister.setText("切换为学生注册");
                }
                break;
        }
    }

    private void resetPwd() {
        Log.e(TAG, "resetPwd: " + telephone + pwd + code);
        OkHttpUtils.post().url(Config.URL_RESET_PWD)
                .addParams(Config.KEY_USER_NAME, telephone)
                .addParams(Config.KEY_PASSWORD, pwd)
                .addParams(Config.KEY_FORGET_CODE, code)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast("网络连接出错~");
                Log.e(TAG, "onError: " + e.getMessage() + e.toString(), null);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "resetPwd onResponse: " + response, null);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("密码修改成功，请重新登录！")) {
                        ToastUtil.showToast(status);
                        finish();
                    } else {
                        ToastUtil.showToast(status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtil.showToast("网络连接出错~");
                }
            }
        });
    }

    //企业注册
    private void companyRegister() {
        OkHttpUtils.post().url(Config.URL_COMPANY_REGISTER)
                .addParams(Config.KEY_USER_NAME, telephone)
                .addParams(Config.KEY_PASSWORD, pwd)
                .addParams(Config.KEY_COM_MSG, code)
                .addParams(Config.KEY_ENTERPRISE_NAME, companyName)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast("网络连接出错~");
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    Log.e(TAG, "companyRegister onResponse: " + response, null);
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("注册成功！")) {
                        ToastUtil.showToast(status);
                        finish();
                    } else {
                        ToastUtil.showToast(status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //学生注册
    private void studentRegister() {
        OkHttpUtils.post().url(Config.URL_STUDENT_REGISTER)
                .addParams(Config.KEY_USER_NAME, telephone)
                .addParams(Config.KEY_PASSWORD, pwd)
                .addParams(Config.KEY_STU_MSG, code)
                .addParams("agentId", agentId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast("网络连接出错~");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "studentRegister onResponse: " + response, null);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("注册成功！")) {
                        ToastUtil.showToast(status);
                        finish();
                    } else {
                        ToastUtil.showToast(status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean checkMsg() {
        if (editMobile.getText().toString().length() != 11) {
            ToastUtil.showToast("手机格式不正确");
            return false;
        } else if (etPwd.getText().toString().length() < 6) {
            ToastUtil.showToast("密码长度需大于6");
            return false;
        } else if (!StringUtils.isNotEmpty(etYanzheng.getText().toString())) {
            ToastUtil.showToast("验证码为空");
            return false;
        } else if (isCompanyRegister && !StringUtils.isNotEmpty(etCompanyName.getText().toString())) {
            ToastUtil.showToast("企业名称为空");
            return false;
        }

        telephone = editMobile.getText().toString();
        pwd = etPwd.getText().toString();
        code = etYanzheng.getText().toString();
        companyName = etCompanyName.getText().toString();
        agentId = etAgentId.getText().toString();
        if (StringUtils.isEmpty(agentId)) {
            agentId = "0";
        }
        return true;
    }

}
