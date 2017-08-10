package com.hzu.jpg.commonwork.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.VersionParams;
import com.hzu.jpg.commonwork.service.AVersionService;
import com.hzu.jpg.commonwork.service.VersionService;
import com.hzu.jpg.commonwork.utils.AppUtils;
import com.hzu.jpg.commonwork.utils.Constants;
import com.hzu.jpg.commonwork.utils.SharedPreferencesUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.utils.cookieUtil.PersistentCookieJar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class SettingActivity extends AppCompatActivity {

    Button btnExit;
    TextView tvUpdate;
    ImageButton ib;
    TextView tvResetPwd;
    TextView tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tvUpdate= (TextView) findViewById(R.id.tv_setting_update);
        btnExit= (Button) findViewById(R.id.btn_setting_exit);
        tvResetPwd = (TextView) findViewById(R.id.tv_resetPwd);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        ib= (ImageButton) findViewById(R.id.ib_setting_back);
        if(MyApplication.user==null){
            btnExit.setVisibility(View.GONE);
            tvResetPwd.setVisibility(View.GONE);
            tvAddress.setVisibility(View.GONE);
        }
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(SettingActivity.this).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyApplication.user = null;
                        Constants.isLogin = false;
                        PersistentCookieJar cookieJar = (PersistentCookieJar) OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
                        cookieJar.clear();
                        setResult(Config.EXIT);
                        SharedPreferencesUtil.saveUserMsg("","");
                        finish();
                    }
                }).setMessage("确定退出登录吗")
                  .show();
            }
        });

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post().url(Config.URL_UPDATE)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast("网络连接出错~");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Log.e("main", "onResponse: " + response, null);
                            JSONObject jsonObject = new JSONObject(response);
                            double anInt = jsonObject.getDouble(Config.KEY_VERSION);
                            int verCode = AppUtils.getVerCode(SettingActivity.this);
                            if(anInt > verCode){
                                VersionParams versionField = new VersionParams()
                                        .setIsForceUpdate(false)
                                        .setRequestMethod(AVersionService.POST)
                                        .setRequestUrl(Config.URL_UPDATE)
                                        .setVersionServiceName(VersionService.class.getName());
                                Intent intent = new Intent(SettingActivity.this, VersionService.class);
                                intent.putExtra("versionField", versionField);
                                startService(intent);
                            }else {
                                ToastUtil.showToast("已更新至最新版本");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,RegisterSetPwdActivity.class);
                intent.putExtra("isSetPwd",true);
                startActivity(intent);
            }
        });

        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,MyAddressActivity.class);
                startActivity(intent);
            }
        });

    }
}
