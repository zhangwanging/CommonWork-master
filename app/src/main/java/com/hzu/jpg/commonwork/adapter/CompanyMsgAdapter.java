package com.hzu.jpg.commonwork.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.JobMsgActivity;
import com.hzu.jpg.commonwork.activity.LoginActivity;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.moudle.JobMsg;
import com.hzu.jpg.commonwork.utils.Constants;
import com.hzu.jpg.commonwork.utils.DialogUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by cimcitech on 2017/8/31.
 */

public class CompanyMsgAdapter extends BaseAdapter {


    private List<JobMsg> data;
    private LayoutInflater inflater;
    private Context context;

    public CompanyMsgAdapter(Context context, List<JobMsg> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    public List<JobMsg> getAll() {
        return data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final JobMsg detail = data.get(position);
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = inflater.inflate(R.layout.item_apply_jobs, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(detail.getJob());


        StringBuffer salary = new StringBuffer("");
        salary.append(detail.getSalary());
        if (Integer.valueOf(detail.getUnit()) == 0)
            salary.append("元/小时");
        else
            salary.append("元/月");
        viewHolder.tvSalaryTitle.setText(salary);
        viewHolder.tvRecruitsNumber.setText("招聘人数：" + detail.getNumber());

        int length = detail.getJobLabel().size();

        if (length > 3) {
            viewHolder.tvWelfare1.setText(detail.getJobLabel().get(0));
            viewHolder.tvWelfare2.setText(detail.getJobLabel().get(1));
            viewHolder.tvWelfare3.setText(detail.getJobLabel().get(2));
            viewHolder.tvWelfare1.setVisibility(View.VISIBLE);
            viewHolder.tvWelfare2.setVisibility(View.VISIBLE);
            viewHolder.tvWelfare3.setVisibility(View.VISIBLE);
        } else if (length == 2) {
            viewHolder.tvWelfare1.setText(detail.getJobLabel().get(0));
            viewHolder.tvWelfare2.setText(detail.getJobLabel().get(1));
            viewHolder.tvWelfare1.setVisibility(View.VISIBLE);
            viewHolder.tvWelfare2.setVisibility(View.VISIBLE);
        } else if (length == 1) {
            viewHolder.tvWelfare1.setText(detail.getJobLabel().get(0));
            viewHolder.tvWelfare1.setVisibility(View.VISIBLE);
        }
        viewHolder.tvDate.setText(detail.getDate());
        viewHolder.tvCompany.setText(detail.getCname());
        viewHolder.tvLocation.setText(detail.getCity() + "-" + detail.getRegion());

        Glide.with(context.getApplicationContext())
                .load(Constants.imageUrl + detail.getCover())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.image_bg_default)
                .error(R.mipmap.image_bg_default).into(viewHolder.imgJob);

        viewHolder.btnApplyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.user == null) {
                    ToastUtil.showToast("请先登录");
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else if (MyApplication.role == 1) {
                    ToastUtil.showToast("企业帐号无法申请工作职位！");
                } else {
                    final AlertDialog dialog = DialogUtil.showLoadingDialog(context);
                    //Log.e(TAG, "onClick: " + item.getId() + " " + MyApplication.user.getTelephone(),null );
                    OkHttpUtils.post().url(Config.URL_DETAIL_APPLY_JOB)
                            .addParams(Config.KEY_JOB_ID, detail.getId())
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            dialog.dismiss();
                            ToastUtil.showNetError();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            //Log.e(TAG, "onResponse: " + response,null );
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

        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inflater.getContext(), JobMsgActivity.class);
                intent.putExtra(Config.ID, Integer.parseInt(detail.getId()));
                inflater.getContext().startActivity(intent);
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.img_job)
        ImageView imgJob;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_salary_title)
        TextView tvSalaryTitle;
        @Bind(R.id.tv_recruits_number)
        TextView tvRecruitsNumber;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_welfare_1)
        TextView tvWelfare1;
        @Bind(R.id.tv_welfare_2)
        TextView tvWelfare2;
        @Bind(R.id.tv_welfare_3)
        TextView tvWelfare3;
        @Bind(R.id.ll_welfare)
        LinearLayout llWelfare;
        @Bind(R.id.btn_applyJob)
        AppCompatButton btnApplyJob;
        @Bind(R.id.tv_company)
        AppCompatTextView tvCompany;
        @Bind(R.id.tv_location)
        TextView tvLocation;
        @Bind(R.id.item_layout)
        LinearLayout itemLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}