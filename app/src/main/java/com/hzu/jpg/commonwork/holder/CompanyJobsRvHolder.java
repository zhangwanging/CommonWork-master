package com.hzu.jpg.commonwork.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.Bean.CompanyJobsListBean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class CompanyJobsRvHolder extends MyRvHolder {

    private TextView tvJobName;
    private TextView tvSalary;
    private TextView tvUnit;
    private TextView tvClassify;
    private TextView tvNumber;
    private TextView tvFinish;
    private TextView tvDate;
    private TextView tvLocation;

    private ImageView ivCover;

    public CompanyJobsRvHolder(View itemView) {
        super(itemView);
        tvClassify= (TextView) itemView.findViewById(R.id.tv_item_company_jobs_classify);
        tvDate= (TextView) itemView.findViewById(R.id.tv_item_company_jobs_date);
        tvFinish= (TextView) itemView.findViewById(R.id.tv_item_company_jobs_finish);
        tvJobName= (TextView) itemView.findViewById(R.id.tv_item_company_jobs_job_name);
        tvSalary= (TextView) itemView.findViewById(R.id.tv_item_company_jobs_salary);
        tvNumber= (TextView) itemView.findViewById(R.id.tv_item_company_jobs_number);
        tvUnit= (TextView) itemView.findViewById(R.id.tv_item_company_jobs_unit);
        tvLocation= (TextView) itemView.findViewById(R.id.tv_item_company_jobs_location);

        ivCover= (ImageView) itemView.findViewById(R.id.iv_item_company_jobs_cover);
    }

    @Override
    public void setData(Object obj) {
        CompanyJobsListBean bean= (CompanyJobsListBean) obj;
        tvClassify.setText(bean.getClassify());
        tvNumber.setText(bean.getNumber());
        tvSalary.setText(bean.getSalary());
        tvJobName.setText(bean.getJob());
        tvDate.setText(bean.getDate());
        tvFinish.setText(bean.getFinish());
        StringBuilder sb=new StringBuilder();
        sb.append(bean.getProvince()).append(bean.getCity()).append(bean.getRegion()).append(bean.getDetails());
        tvLocation.setText(sb.toString());
        String unit=bean.getUnit_zh_cn();
        if(unit.equals("0")){
            tvUnit.setText("小时");
        }else{
            tvUnit.setText("月");
        }

        Glide.with(Config.CONTEXT).load(Config.IP + bean.getCover())
                .error(R.mipmap.ic_head_default)
                .into(ivCover);
    }

    @Override
    public void setOnClick(final MyRvAdapter.OnRvClickListener listener, final Object obj, final int position) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id= (String) obj;
                listener.onRvClick(id,position);
            }
        });
    }

    @Override
    public Object getOnClickId(Object obj) {
        CompanyJobsListBean bean= (CompanyJobsListBean) obj;
        return bean.getId();
    }
}
