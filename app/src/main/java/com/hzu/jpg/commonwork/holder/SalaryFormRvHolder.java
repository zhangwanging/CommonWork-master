package com.hzu.jpg.commonwork.holder;

import android.view.View;
import android.widget.TextView;


import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.SalaryFormBean;


/**
 * Created by Administrator on 2017/2/27.
 */

public class SalaryFormRvHolder extends MyRvHolder {

    TextView tv;
    TextView tvDate;
    TextView tvRealSalary;

    public SalaryFormRvHolder(View itemView) {
        super(itemView);
        this.tv = (TextView) itemView.findViewById(R.id.item_tv_my_packet_salary_form);
        this.tvDate = (TextView) itemView.findViewById(R.id.item_tv_my_packet_salary_form_date);
        tvRealSalary = (TextView) itemView.findViewById(R.id.tv_item_salary_form_real);
    }

    public void setData(Object obj) {
        SalaryFormBean bean = (SalaryFormBean) obj;
        StringBuilder sb = new StringBuilder();
        sb.append(bean.getJobNumber()).append("\n")
                .append(bean.getWokerName()).append("\n")
                .append(bean.getBaseWorkDays()).append("\n")
                .append(bean.getWeekWorkDays()).append("\n")
                .append(bean.getBaseSalaryHours()).append("\n")
                .append(bean.getOverSalaryHours()).append("\n")
                .append(bean.getBaseSalarys()).append("\n")
                .append(bean.getOverSalarys()).append("\n")
                .append(bean.getNightAllowance()).append("\n")
                .append(bean.getMealAllowance()).append("\n")
                .append(bean.getWorkAllowance()).append("\n")
                .append(bean.getFullReward()).append("\n")
                .append(bean.getExamineReward()).append("\n")
                .append(bean.getOtherAllowance()).append("\n")
                .append(bean.getTotalWages()).append("\n")
                .append(bean.getIncomeTax()).append("\n")
                .append(bean.getAbsenteeism()).append("\n")
                .append(bean.getQianka()).append("\n")
                .append(bean.getLateEarly()).append("\n")
                .append(bean.getPayFor()).append("\n")
                .append(bean.getMealPay()).append("\n")
                .append(bean.getAccommodationFee()).append("\n")
                .append(bean.getInsuranceFee()).append("\n")
                .append(bean.getSocialSecurity()).append("\n")
                .append(bean.getOtherFee()).append("\n")
                .append(bean.getTotalFee()).append("\n")
                .append(bean.getCompanyName()).append("\n")
                .append(bean.getCompanyManager());
        tv.setText(sb);
        tvDate.setText(bean.getSalaryDate());
        tvRealSalary.setText(bean.getRealSalarys());
    }

    @Override
    public void setOnClick(MyRvAdapter.OnRvClickListener listener, Object obj, final int position) {

    }

    @Override
    public Object getOnClickId(Object obj) {
        return null;
    }

}
