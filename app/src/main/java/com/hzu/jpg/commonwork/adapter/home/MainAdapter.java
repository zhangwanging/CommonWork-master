package com.hzu.jpg.commonwork.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.LoginActivity;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.base.BaseRvAdapter;
import com.hzu.jpg.commonwork.base.BaseViewHolder;
import com.hzu.jpg.commonwork.enity.home.JobVo;
import com.hzu.jpg.commonwork.utils.Constants;
import com.hzu.jpg.commonwork.utils.DialogUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

/**
 * Created by cimcitech on 2017/6/2.
 */

public class MainAdapter extends BaseRvAdapter<JobVo.Data> {

    private static final String TAG = "JobMsgAdapter";
    private final Context context;
    private int[] tvWelfareId = {R.id.tv_welfare_1, R.id.tv_welfare_2, R.id.tv_welfare_3};

    public MainAdapter(Context context, List<JobVo.Data> data) {
        super(Config.CONTEXT, R.layout.item_apply_jobs, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final JobVo.Data item) {
        helper.setText(R.id.tv_title, item.getJob());
        helper.setText(R.id.tv_date, item.getDate());
        StringBuffer salary = new StringBuffer("");
        salary.append(item.getSalary());
        if (Integer.valueOf(item.getUnit()) == 0)
            salary.append("元/小时");
        else
            salary.append("元/月");

        helper.setText(R.id.tv_salary_title, salary);

        helper.setText(R.id.tv_recruits_number,"招聘人数："+item.getNumber());
        helper.setImageUrl(R.id.img_job, Constants.imageUrl + item.getCover());

        int length = item.getJobLabel().size();
        length = length > 3 ? 2 : length - 1;

        for (int i = 0; i < length; i++) {
            helper.setText(tvWelfareId[i], item.getJobLabel().get(i));
            helper.setVisible(tvWelfareId[i], true);
        }

        //helper.setText(R.id.tv_date, "12312312312");
        helper.setText(R.id.tv_company, item.getCname());
        helper.setText(R.id.tv_location, item.getCity() + "-" + item.getRegion());

        helper.setOnClickListener(R.id.btn_applyJob, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.user == null) {
                    ToastUtil.showToast("请先登录");
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else if (MyApplication.role == 1) {
                    ToastUtil.showToast("企业帐号无法申请工作职位！");
                } else {
                    final AlertDialog dialog = DialogUtil.showLoadingDialog(context);
                    Log.e(TAG, "onClick: " + item.getId() + " " + MyApplication.user.getTelephone(), null);
                    OkHttpUtils.post().url(Config.URL_DETAIL_APPLY_JOB)
                            .addParams(Config.KEY_JOB_ID, item.getId() + "")
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            dialog.dismiss();
                            ToastUtil.showNetError();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e(TAG, "onResponse: " + response, null);
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
                            }
                        }
                    });
                }
            }
        });
    }

}
