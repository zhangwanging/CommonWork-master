package com.hzu.jpg.commonwork.HourWork.Presenter;

import com.hzu.jpg.commonwork.HourWork.Fragment.HourWorkMainFragment;
import com.hzu.jpg.commonwork.HourWork.Model.HourWorkMainModel;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;
import com.hzu.jpg.commonwork.utils.TimeUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;

/**
 * Created by Administrator on 2017/5/11.
 */

public class HourWorkMonthPresenter {

    HourWorkMainFragment fragment;
    HourWorkMainModel model;

    public HourWorkMonthPresenter(HourWorkMainFragment fragment){
        this.fragment=fragment;
        model=new HourWorkMainModel(fragment.getContext());
    }

    public void initData(){
        String date=TimeUtil.getDateYM();
        OverTimeRecordMonthBean bean=model.getBean(date);
        if (bean==null){
            bean=model.getDef();
        }else if(bean.getHour_work()==1){
            ToastUtil.showToast("hourwork update month");
            bean=model.updateMonth(bean.getDate_ym());
        }
        fragment.setDate(date);
        fragment.setBasicSalary(String.valueOf(bean.getOt_salary()));
        fragment.setHours(String.valueOf(bean.getOt_hours()));
        fragment.setTotalSalary(String.valueOf(bean.getSalary()));
    }

}
