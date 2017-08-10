package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.moudle.JobMsg;
import com.hzu.jpg.commonwork.utils.DialogUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.utils.URLImageParser;
import com.hzu.jpg.commonwork.widgit.AutoLineView;
import com.hzu.jpg.commonwork.widgit.CircleProgressbar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fangx.common.ui.activity.BaseAppCompatActivity;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.common.util.netstatus.NetUtils;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/2/26.
 */

public class JobMsgActivity extends BaseAppCompatActivity {


    private static final String TAG = "JobMsgActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.layout_toolbarBg)
    RelativeLayout layoutToolbarBg;
    @Bind(R.id.tv_jobTitle)
    TextView tvJobTitle;
    @Bind(R.id.tv_salary)
    TextView tvSalary;
    @Bind(R.id.tv_more_salary)
    TextView tvMoreSalary;
    @Bind(R.id.tv_peopleNum)
    TextView tvPeopleNum;
    @Bind(R.id.tv_education)
    TextView tvEducation;
    //    @Bind(R.id.tv_sex)
//    TextView tvSex;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.autoView_welfare)
    AutoLineView autoViewWelfare;
    @Bind(R.id.tv_companyName)
    TextView tvCompanyName;
    @Bind(R.id.tv_companyPeopleNum)
    TextView tvCompanyPeopleNum;
    @Bind(R.id.ll_PeopleNum)
    LinearLayout llPeopleNum;
    @Bind(R.id.tv_quality)
    TextView tvQuality;
    @Bind(R.id.rl_company)
    RelativeLayout rlCompany;
    @Bind(R.id.tv_jobDescribe)
    TextView tvJobDescribe;
    @Bind(R.id.sv_content)
    ScrollView svContent;
    @Bind(R.id.btn_applyJob)
    Button btnApplyJob;
    @Bind(R.id.loading_progress)
    CircleProgressbar loadingProgress;
    @Bind(R.id.img_company)
    ImageView imgCompany;
    private int id;
    JobMsg jobMsg;

    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_job_msg;
    }

    @Override
    protected void initViewsAndEvents() {
        svContent.setVisibility(View.GONE);
        id = getIntent().getIntExtra(Config.ID, 0);
        Log.e(TAG, "initViewsAndEvents: " + id, null);
        tvTitle.setText("职位详情");

        OkHttpUtils.post().url(Config.URL_JOB_MSG_DETAIL)
                .addParams(Config.ID, id + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: " + e.getMessage(), null);
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e(TAG, "onResponse: " + response, null);
                svContent.setVisibility(View.VISIBLE);
                loadingProgress.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt(Config.KEY_STATU);
                    switch (status) {
                        case Config.STATUS_SUCCESS:
                            ObjectMapper mapper = new ObjectMapper();
                            jobMsg = mapper.readValue(jsonObject.getString(Config.KEY_DATA), JobMsg.class);
                            Log.e(TAG, "onResponse: " + jobMsg, null);
                            updateMsg(jobMsg);
                            break;
                        case Config.STATUS_FAIL:
                            ToastUtil.showToast(jsonObject.getString(Config.KEY_MESSAGE));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateMsg(JobMsg jobMsg) {
        tvEducation.setText(jobMsg.getRequired());
        tvPeopleNum.setText(jobMsg.getNumber());
        tvLocation.setText(jobMsg.getProvince() + " " + jobMsg.getCity() + " " + jobMsg.getRegion() + " " + jobMsg.getDetails());
        tvCompanyName.setText(jobMsg.getCname());
        tvJobTitle.setText(jobMsg.getJob());
        StringBuffer str = new StringBuffer();
        str.append(jobMsg.getSalary());
        if (Integer.valueOf(jobMsg.getUnit()) == 0)
            str.append("元/小时");
        else
            str.append("元/月");
        tvSalary.setText(str);
        if (jobMsg.getMoreSalary() != null && !jobMsg.getMoreSalary().isEmpty())
            tvMoreSalary.setText(jobMsg.getMoreSalary());
        tvJobDescribe.setText(Html.fromHtml(jobMsg.getDescribes(), new URLImageParser(tvJobDescribe), null));
        tvCompanyPeopleNum.setText(jobMsg.getLinkMan());
        tvQuality.setText(jobMsg.getLinkPhone());
        for (String s : jobMsg.getJobLabel()) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.item_tag_welfare, null);
            TextView tv = (TextView) inflate.findViewById(R.id.tv_welfare);
            tv.setText(s);
            autoViewWelfare.addView(inflate);
        }
        Glide.with(this).load(Config.IP + jobMsg.getCover()).into(imgCompany);
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

    @OnClick({R.id.back, R.id.rl_company, R.id.btn_applyJob})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_company:
                Intent intent = new Intent(JobMsgActivity.this, CompanyMsgActivity.class);
                intent.putExtra(Config.KEY_ID, jobMsg.getcId());
                startActivity(intent);
                break;
            case R.id.btn_applyJob:

                if (MyApplication.user == null) {
                    ToastUtil.showToast("请先登录");
                    startActivity(new Intent(this, LoginActivity.class));
                } else if (MyApplication.role == 1) {
                    ToastUtil.showToast("企业帐号无法申请工作职位");
                } else {
                    final AlertDialog dialog = DialogUtil.showLoadingDialog(this);
                    OkHttpUtils.post().url(Config.URL_DETAIL_APPLY_JOB)
                            .addParams(Config.KEY_JOB_ID, id + "")
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            dialog.dismiss();
                            ToastUtil.showNetError();
                            Log.e(TAG, "onError: " + e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            dialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int status = jsonObject.getInt(Config.KEY_STATU);
                                switch (status) {
                                    case Config.STATUS_SUCCESS:
                                        ToastUtil.showToast("求职信息已发送，请耐心等待工作人员联系~");
                                        break;
                                    case Config.STATUS_FAIL:
                                        ToastUtil.showToast(jsonObject.getString(Config.KEY_MESSAGE));
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "onResponse: " + e.getMessage());
                            }
                        }
                    });
                }

                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
