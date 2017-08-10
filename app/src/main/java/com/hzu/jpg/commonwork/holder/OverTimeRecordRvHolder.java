package com.hzu.jpg.commonwork.holder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordBean;
import com.hzu.jpg.commonwork.utils.TimeUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;

import java.util.Locale;


/**
 * Created by Administrator on 2017/3/16.
 */

public class OverTimeRecordRvHolder extends MyRvHolder{

    TextView tvDate;
    TextView tvWeek;
    TextView tvBasicHours;
    TextView tvOtHours;
    TextView tvOtSalary;
    TextView tvMultiply;

    public OverTimeRecordRvHolder(View itemView) {
        super(itemView);
        tvBasicHours= (TextView) itemView.findViewById(R.id.tv_item_ot_record_basic_time);
        tvDate= (TextView) itemView.findViewById(R.id.tv_item_ot_record_date);
        tvMultiply= (TextView) itemView.findViewById(R.id.tv_item_ot_record_multiply);
        tvOtHours= (TextView) itemView.findViewById(R.id.tv_item_ot_record_ot_hours);
        tvOtSalary= (TextView) itemView.findViewById(R.id.tv_item_ot_record_ot_salary);
        tvWeek= (TextView) itemView.findViewById(R.id.tv_item_ot_record_week);
    }
    @Override
    public void setData(Object obj){
        OverTimeRecordBean bean= (OverTimeRecordBean) obj;
        tvOtSalary.setText(String.format(Locale.CHINA,"%.2f",bean.getOt_salary()));
        tvWeek.setText(bean.getWeek());
        Log.d("week","week="+bean.getWeek());
        tvMultiply.setText(String.format(Locale.CHINA,"%.2f",bean.getOt_salary_multiple()));
        int hours=bean.getOt_hours();
        int minutes=bean.getOt_minutes();
        tvOtHours.setText(Double.toString(TimeUtil.Time2Double(hours,minutes)));
        hours=bean.getBasic_hours();
        minutes=bean.getBasic_minutes();
        tvBasicHours.setText(Double.toString(TimeUtil.Time2Double(hours,minutes)));
        tvDate.setText(bean.getDate_ymd().substring(5));
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
        OverTimeRecordBean bean= (OverTimeRecordBean) obj;
        return bean.getDate_ymd();
    }

}
