package com.hzu.jpg.commonwork.holder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.CompanyOneKeyJobBean;


/**
 * Created by Administrator on 2017/4/10.
 */

public class CompanyOneKeyJobHolder extends MyRvHolder {

    TextView tvSalary;
    TextView tvMen;
    TextView tvWomen;
    TextView tvNumber;
    TextView tvAge;
    TextView tvDate;
    TextView tvUnit;
    TextView tvDelete;

    public CompanyOneKeyJobHolder(View itemView) {
        super(itemView);
        tvSalary= (TextView) itemView.findViewById(R.id.tv_item_company_one_key_salary);
        tvAge= (TextView) itemView.findViewById(R.id.tv_item_company_one_key_age);
        tvDate= (TextView) itemView.findViewById(R.id.tv_item_company_one_key_date);
        tvMen= (TextView) itemView.findViewById(R.id.tv_item_company_one_key_man);
        tvWomen= (TextView) itemView.findViewById(R.id.tv_item_company_one_key_woman);
        tvNumber= (TextView) itemView.findViewById(R.id.tv_item_company_one_key_number);
        tvUnit= (TextView) itemView.findViewById(R.id.tv_item_company_one_key_unit);
        tvDelete= (TextView) itemView.findViewById(R.id.tv_item_company_one_key_delete);
    }

    @Override
    public void setData(Object obj) {
        CompanyOneKeyJobBean bean= (CompanyOneKeyJobBean) obj;
        tvNumber.setText(bean.getNumber());
        Log.e("unit",bean.getUnit());
        if(bean.getUnit().equals("2")){
            tvUnit.setText("小时");
        }else{
            tvUnit.setText("月");
        }
        String age=bean.getAgeBegin()+"-"+bean.getAgeEnd();
        tvAge.setText(age);
        tvMen.setText(bean.getMen());
        tvWomen.setText(bean.getWomen());
        tvDate.setText(bean.getDate());
        tvSalary.setText(bean.getSalary());
    }

    @Override
    public Object getOnClickId(Object obj) {
        CompanyOneKeyJobBean bean= (CompanyOneKeyJobBean) obj;
        return bean.getId();
    }


    public void setOnClick(final MyRvAdapter.OnRvClickListener listener, final Object obj, final int position){
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRvClick(obj,position);
            }
        });
    }
}
