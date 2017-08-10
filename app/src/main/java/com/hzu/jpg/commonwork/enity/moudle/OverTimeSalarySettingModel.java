package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hzu.jpg.commonwork.db.DaoTemplate;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordBean;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeSalarySettingBean;
import com.hzu.jpg.commonwork.utils.DoubleUtil;
import com.hzu.jpg.commonwork.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/3/18.
 */

public class OverTimeSalarySettingModel {

    public final static String TABLE_SETTING="ot_salary_setting";

    DaoTemplate template;
    SharedPreferences sp;

    public OverTimeSalarySettingModel(Context context){
        if(sp==null){
            sp=context.getSharedPreferences("setting",Context.MODE_PRIVATE);
        }
        template=new DaoTemplate(context);
    }

    public OverTimeSalarySettingBean getBean(String date){
        return (OverTimeSalarySettingBean) template.query(TABLE_SETTING,"date_ym=?",new String[]{date},OverTimeSalarySettingBean.class);
    }

    public void save(OverTimeSalarySettingBean bean){
        template.save(bean,TABLE_SETTING);
        updateOtherData(bean);
    }

    public void update(OverTimeSalarySettingBean bean,String date){
        template.update(TABLE_SETTING,"date_ym=?",new String[]{date},bean);
        updateOtherData(bean);
    }

    private void updateOtherData(OverTimeSalarySettingBean bean){
        String date=bean.getDate_ym();
        List<OverTimeRecordBean> dayBeans=new ArrayList<>();
        template.query(AddOverTimeRecordModel.TABLE,"date_ymd like ?",new String[]{date+"%"},OverTimeRecordBean.class,null,dayBeans);
        double otSalary=0;
        for (int i=0;i<dayBeans.size();i++){
            OverTimeRecordBean dayBean=dayBeans.get(i);
            Log.d("setting change",dayBean.getDate_ymd()+dayBean.getWork_type()+dayBean.getOt_salary_multiple());
            if(dayBean.getWork_type().equals("工作日")){
                dayBean.setOt_salary_multiple(bean.getNormal_multiply());
                dayBean.setOt_salary_per_hour(bean.getNormal_salary());
                double workHours= TimeUtil.Time2Double(dayBean.getOt_hours(),dayBean.getOt_minutes());
                dayBean.setOt_salary(DoubleUtil.doubleKeep2(workHours*bean.getNormal_salary()));
                template.update(AddOverTimeRecordModel.TABLE,"date_ymd=?",new String[]{dayBean.getDate_ymd()},dayBean);
            }else if(dayBean.getWork_type().equals("休息日")){
                dayBean.setOt_salary_multiple(bean.getWeekend_multiply());
                dayBean.setOt_salary_per_hour(bean.getWeekend_salary());
                double workHours= TimeUtil.Time2Double(dayBean.getOt_hours(),dayBean.getOt_minutes());
                dayBean.setOt_salary(DoubleUtil.doubleKeep2(workHours*bean.getWeekend_salary()));
                template.update(AddOverTimeRecordModel.TABLE,"date_ymd=?",new String[]{dayBean.getDate_ymd()},dayBean);
            }else if(dayBean.getWork_type().equals("节假日")){
                dayBean.setOt_salary_multiple(bean.getFestival_multiply());
                dayBean.setOt_salary_per_hour(bean.getFestival_salary());
                double workHours= TimeUtil.Time2Double(dayBean.getOt_hours(),dayBean.getOt_minutes());
                dayBean.setOt_salary(DoubleUtil.doubleKeep2(workHours*bean.getFestival_salary()));
                template.update(AddOverTimeRecordModel.TABLE,"date_ymd=?",new String[]{dayBean.getDate_ymd()},dayBean);
            }
            otSalary=DoubleUtil.doubleAdd(otSalary,dayBean.getOt_salary());
        }
        OverTimeRecordMonthBean monthBean= (OverTimeRecordMonthBean) template.query(OverTimeRecordMonthModel.TABLE_PART,"date_ym=?",new String[]{date},OverTimeRecordMonthBean.class);
        if(monthBean!=null){
            monthBean.setSalary(DoubleUtil.doubleAdd(DoubleUtil.DoubleSubtract(monthBean.getSalary(),monthBean.getOt_salary()),otSalary));
            if(otSalary<0){ otSalary=0;}
            monthBean.setOt_salary(otSalary);
            double salary=DoubleUtil.doubleAdd(DoubleUtil.DoubleSubtract(monthBean.getSalary(),monthBean.getBasic_salary()),bean.getBasic_salary());
            if(salary<0){ salary=0;}
            monthBean.setSalary(salary);
            monthBean.setBasic_salary(bean.getBasic_salary());
            template.update(OverTimeRecordMonthModel.TABLE_PART,"date_ym=?",new String[]{monthBean.getDate_ym()},monthBean);
        }
    }

}
