package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.utils.StringUtils;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.widgit.MarqueTextView;
import com.suke.widget.SwitchButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fangx.common.ui.activity.BaseAppCompatActivity;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.common.util.netstatus.NetUtils;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/17.
 */

public class RecruitmentActivity extends BaseAppCompatActivity {
    private static final String TAG = "RecruitmentActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_menPersonNum)
    EditText etMenPersonNum;
    @Bind(R.id.et_womenPersonNum)
    EditText etWomenPersonNum;
    @Bind(R.id.et_menSalary)
    EditText etMenSalary;

    @Bind(R.id.cb_month)
    AppCompatCheckBox cbMonth;
    @Bind(R.id.cb_hour)
    AppCompatCheckBox cbHour;
    @Bind(R.id.sb_isSex)
    SwitchButton sbIsSex;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.layout_womenNum)
    TextInputLayout layoutWomenNum;
    @Bind(R.id.tv_hintMen_1)
    TextView tvHintMen1;
    @Bind(R.id.tv_hintWomen_1)
    TextView tvHintWomen1;
    @Bind(R.id.layout_personNum)
    LinearLayout layoutPersonNum;
    @Bind(R.id.tv_recruimentMsg)
    MarqueTextView tvRecruimentMsg;
    @Bind(R.id.et_startAge)
    EditText etStartAge;
    @Bind(R.id.et_endAge)
    EditText etEndAge;

    private String numberMsg;

    private String num;
    private String menNum = "0";
    private String womenNum = "0";
    private String salary;
    private String startAge;
    private String endAge;
    private String unit = "1";
    private boolean isDefineSex = false;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_recruitment;
    }

    @Override
    protected void initViewsAndEvents() {

        tvTitle.setText("一键招聘");
        tvRecruimentMsg.setAnimation(AnimationUtils.loadAnimation(this,R.anim.in_right_slide_slow));
        tvRecruimentMsg.setText("sdfasdfsdifnsidfs");
        sbIsSex.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    layoutPersonNum.setVisibility(View.VISIBLE);
                    layoutPersonNum.setAnimation(AnimationUtils.loadAnimation(RecruitmentActivity.this, R.anim.in_right_slide));
                    isDefineSex = true;
                } else {
                    layoutPersonNum.setVisibility(View.GONE);
                    layoutPersonNum.setAnimation(AnimationUtils.loadAnimation(RecruitmentActivity.this, R.anim.right_out));
                    isDefineSex = false;
                }
            }
        });

        initData();
    }

    private void initData() {
        OkHttpUtils.post().url(Config.URL_SHOW_NUMBER)
                .addParams(Config.KEY_CITY, Config.SELECTED_CITY)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse: " + response, null);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt(Config.KEY_STATU);
                    switch (status) {

                        case Config.STATUS_FAIL:
                            ToastUtil.showToast(jsonObject.getString(Config.KEY_MESSAGE));
                            break;
                        case Config.STATUS_SUCCESS:
                            jsonObject = jsonObject.getJSONObject(Config.KEY_DATA);
                            numberMsg = "截至" + jsonObject.getString(Config.DATE)
                                    + "为止有" + jsonObject.getString(Config.KEY_NUMBER) + "人来该区域求职";
                            Log.e(TAG, "onResponse: " + numberMsg,null );
                            tvRecruimentMsg.setText(numberMsg);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.btn_confirm, R.id.cb_month, R.id.cb_hour})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_confirm:
                if(checkMsg()){
                    OkHttpUtils.post().url(Config.URL_RECRUITMENT)
                            .addParams(Config.KEY_NUMBER, num)
                            .addParams(Config.KEY_MEN, menNum)
                            .addParams(Config.KEY_WOMEN,womenNum)
                            .addParams(Config.KEY_AGE_BEGIN,startAge)
                            .addParams(Config.KEY_AGE_END,endAge)
                            .addParams(Config.KEY_SALARY,salary)
                            .addParams(Config.KEY_UNIT,unit)
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e(TAG, "onResponse: " + response, null);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int status = jsonObject.getInt(Config.KEY_STATU);
                                switch (status) {
                                    case Config.STATUS_SUCCESS:
                                        ToastUtil.showToast(jsonObject.getString(Config.KEY_DATA));
                                        finish();
                                        break;
                                    case Config.STATUS_FAIL:
                                        ToastUtil.showToast(jsonObject.getString(Config.KEY_MESSAGE));
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    ToastUtil.showToast("请先完善信息");
                }
                break;
            case R.id.cb_month:
                cbHour.setChecked(false);
                unit = "1";
                break;
            case R.id.cb_hour:
                cbMonth.setChecked(false);
                unit = "2";
                break;
        }
    }

    private boolean checkMsg() {
        boolean numCheck = false;
        if(isDefineSex){
            if(!StringUtils.isNotEmpty(etMenPersonNum.getText().toString())
                    || !StringUtils.isNotEmpty(etWomenPersonNum.getText().toString()))
                return false;
            num = Integer.valueOf(etMenPersonNum.getText().toString())
                    + Integer.valueOf(etWomenPersonNum.getText().toString())+"";
            womenNum = etWomenPersonNum.getText().toString();
            menNum = etMenPersonNum.getText().toString();
            numCheck = StringUtils.isNotEmpty(womenNum) || StringUtils.isNotEmpty(menNum);
        }else {
            num = etMenPersonNum.getText().toString();
            womenNum = "0";
            menNum = "0";
            numCheck = StringUtils.isNotEmpty(num);
        }
        salary = etMenSalary.getText().toString();
        startAge = etStartAge.getText().toString();
        endAge = etEndAge.getText().toString();
        return numCheck
                && StringUtils.isNotEmpty(salary)
                && (cbMonth.isChecked() || cbHour.isChecked())
                && StringUtils.isNotEmpty(startAge)
                && StringUtils.isNotEmpty(endAge);
    }
}
