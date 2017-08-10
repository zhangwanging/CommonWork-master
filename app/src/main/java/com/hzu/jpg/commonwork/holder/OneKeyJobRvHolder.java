package com.hzu.jpg.commonwork.holder;

import android.view.View;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.OneKeyJobBean;


/**
 * Created by Administrator on 2017/4/1.
 */

public class OneKeyJobRvHolder extends MyRvHolder {

    TextView tvProvince;
    TextView tvCity;
    TextView tvRegion;
    TextView tvDate;
    TextView tvClassify;

    public OneKeyJobRvHolder(View itemView) {
        super(itemView);
        tvCity= (TextView) itemView.findViewById(R.id.tv_item_one_key_job_city);
        tvClassify= (TextView) itemView.findViewById(R.id.tv_item_one_key_job_classify);
        tvDate= (TextView) itemView.findViewById(R.id.tv_item_one_key_job_date);
        tvProvince= (TextView) itemView.findViewById(R.id.tv_item_one_key_job_province);
        tvRegion= (TextView) itemView.findViewById(R.id.tv_item_one_key_job_region);
    }

    @Override
    public void setData(Object obj) {
        OneKeyJobBean bean= (OneKeyJobBean) obj;
        tvCity.setText(bean.getCity());
        tvRegion.setText(bean.getRegion());
        tvProvince.setText(bean.getProvince());
        tvDate.setText(bean.getDate());
        tvClassify.setText(bean.getClassify());
    }

    @Override
    public void setOnClick(MyRvAdapter.OnRvClickListener listener, Object obj, final int position) {

    }

    @Override
    public Object getOnClickId(Object obj) {
        OneKeyJobBean bean= (OneKeyJobBean) obj;
        return bean.getDate();
    }
}
