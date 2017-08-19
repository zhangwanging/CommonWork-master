package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
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
 * Created by Administrator on 2017/3/1.
 */

public class CompanyMsgActivity extends AppCompatActivity {

    private static final String TAG = "CompanyMsgActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_companyName)
    TextView tvCompanyName;
    @Bind(R.id.tv_label)
    TextView tvLabel;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tv_jobDescribe)
    TextView tvJobDescribe;
    @Bind(R.id.img_company)
    ImageView imgCompany;
    @Bind(R.id.tv_title)
    TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_company_msg);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }

        iniData();
    }

    private void iniData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra(Config.KEY_ID);
        OkHttpUtils.post().url(Config.URL_COMPANY_MSG)
                .addParams(Config.KEY_ID, id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: " + response, null);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            switch (jsonObject.getInt(Config.KEY_STATU)) {

                                case Config.STATUS_FAIL:
                                    ToastUtil.showToast(Config.KEY_MESSAGE);
                                    break;
                                case Config.STATUS_SUCCESS:
                                    jsonObject = jsonObject.getJSONObject(Config.KEY_DATA);
                                    setData(jsonObject);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: " + e.getMessage(), null);
                        }
                    }
                });

        tvTitle.setText("公司详情");
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void setData(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString(Config.KEY_NAME);
        String label = jsonObject.getString(Config.KEY_LABEL);
        String province = jsonObject.getString(Config.KEY_PROVINCE);
        String city = jsonObject.getString(Config.KEY_CITY);
        String region = jsonObject.getString(Config.KEY_REGION);
        String detail = jsonObject.getString(Config.KEY_DETAILS);
        String describes = jsonObject.getString(Config.KEY_DESCRIBES);
        String url = jsonObject.getString(Config.KEY_ICON);
        Glide.with(this).load(Config.IP + url).into(imgCompany);
        tvCompanyName.setText(name);
        tvLabel.setText(label);
        tvJobDescribe.setText(describes);
        tvLocation.setText(("null".equals(province) ? "" : province) + " " + ("null".equals(city) ? "" : city) + " " + ("null".equals(region) ? "" : region) + " " + ("null".equals(detail) ? "" : detail));
        Log.e(TAG, "setData: " + name + label, null);
    }
}
