package com.hzu.jpg.commonwork.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.ListJobInfoBean;


/**
 * Created by Administrator on 2017/3/2.
 */

public class MyListJobRvHolder extends MyRvHolder {

    TextView tvTitle;
    TextView tvClassify;
    TextView tvSalary;
    TextView tvCity;
    TextView tvRegion;
    TextView tvDate;
    TextView tvIsPass;

    public MyListJobRvHolder(View itemView) {
        super(itemView);
        tvTitle= (TextView) itemView.findViewById(R.id.tv_item_job_info_title);
        tvCity= (TextView) itemView.findViewById(R.id.tv_item_job_info_city);
        tvClassify= (TextView) itemView.findViewById(R.id.tv_item_job_info_classify);
        tvDate= (TextView) itemView.findViewById(R.id.tv_item_job_info_date);
        tvIsPass= (TextView) itemView.findViewById(R.id.tv_item_job_info_pass);
        tvRegion= (TextView) itemView.findViewById(R.id.tv_item_job_info_region);
        tvSalary= (TextView) itemView.findViewById(R.id.tv_item_job_info_salary);

    }

    @Override
    public void setData(Object obj) {
        ListJobInfoBean bean= (ListJobInfoBean) obj;
        tvSalary.setText(bean.getSalary());
        tvRegion.setText(bean.getRegion());
        tvDate.setText(bean.getDate());
        tvCity.setText(bean.getCity());
        tvClassify.setText(bean.getClassify());
        tvTitle.setText(bean.getJobName());
        String pass=bean.getIsPass();
        if(pass!=null&&!pass.equals("")){
            if(pass.equals("0")){
                tvIsPass.setText("被拒绝");
                tvIsPass.setTextColor(Color.RED);
            }else if(pass.equals("1")){
                tvIsPass.setText("审核中");
                tvIsPass.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
            }else if(pass.equals("2")){
                tvIsPass.setText("已通过");
                tvIsPass.setTextColor(Color.GREEN);
            }
        }
    }

    @Override
    public void setOnClick(final MyRvAdapter.OnRvClickListener listener, final Object obj, final int position) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRvClick(obj,position);
            }
        });
    }

    @Override
    public Object getOnClickId(Object obj) {
        ListJobInfoBean bean= (ListJobInfoBean) obj;
        return bean.getJobId();
    }

}
