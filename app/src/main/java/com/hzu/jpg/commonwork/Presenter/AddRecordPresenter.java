package com.hzu.jpg.commonwork.Presenter;


import android.content.Context;
import android.util.Log;

import com.hzu.jpg.commonwork.activity.AddOverTimeRecordActivity;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordBean;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeSalarySettingBean;
import com.hzu.jpg.commonwork.enity.moudle.AddOverTimeRecordModel;
import com.hzu.jpg.commonwork.utils.DoubleUtil;
import com.hzu.jpg.commonwork.utils.TimeUtil;

/**
 * Created by Administrator on 2017/3/6.
 */

public class AddRecordPresenter {

    AddOverTimeRecordModel model;
    AddOverTimeRecordActivity activity;
    String date;

    public AddRecordPresenter(AddOverTimeRecordActivity activity) {
        this.activity = activity;
        model = new AddOverTimeRecordModel(activity);
    }

    public AddRecordPresenter(Context context) {
        model = new AddOverTimeRecordModel(context);
    }

    public void setData(String date) {
        OverTimeRecordBean bean = model.getRecord(date);
        this.date = date;
        if (bean == null) {
            bean = getDef(date.substring(0, 7));
            activity.hideDelete(true);
        } else {
            activity.hideDelete(false);
        }
        if (TimeUtil.getDateYMD().equals(date)) {
            activity.setDate("今天");
        } else {
            activity.setDate(date);
        }
        activity.setBasicTime(String.valueOf(bean.getBasic_hours()), String.valueOf(bean.getBasic_minutes()));
        activity.setWorkType(bean.getWork_type());
        activity.setMultiply(String.valueOf(bean.getOt_salary_multiple()));
        activity.setOT_Salary_per_hour(String.valueOf(bean.getOt_salary_per_hour()));
        activity.setOverTimeHours(bean.getOt_hours());
        activity.setOverTimeMinutes(bean.getOt_minutes());
        activity.setWeek(bean.getWeek());
        if (bean.getRemark() != null) {
            activity.setRemark(bean.getRemark());
        }
    }

    public void delete() {
        model.delete(date);
        activity.hideDelete(true);
    }

    public void save() {
        OverTimeRecordBean bean = new OverTimeRecordBean();
        bean.setDate_ymd(date);
        bean.setWeek(activity.getWeek());
        bean.setBasic_hours(activity.getBasicHours());
        bean.setBasic_minutes(activity.getBasicMinutes());
        bean.setOt_hours(activity.getOverTimeHour());
        bean.setOt_minutes(activity.getOverTimeMinute());
        bean.setWork_type(activity.getWorkType())

        ;
        OverTimeSalarySettingBean settingBean = model.getSetting(TimeUtil.getDateYM());
        if (settingBean.getBasic_salary() == 0) {
            //小时工 小时工资*应出勤时间
            double salaryPerHour = settingBean.getSalary_per_hour();
            double basicTime = TimeUtil.Time2Double(bean.getBasic_hours(), bean.getBasic_minutes());
            double salary = DoubleUtil.doubleKeep2(salaryPerHour * basicTime);
            bean.setOt_salary(salary);
            bean.setOt_salary_per_hour(0);
            Log.d("salaryPerHour", String.valueOf(bean.getOt_salary()));
        } else {
            //同工同酬 加班时长*加班倍数*加班工资
            double hours = TimeUtil.Time2Double(bean.getOt_hours(), bean.getOt_minutes());
            double per = Double.parseDouble(activity.getSalaryPerHour());
            bean.setOt_salary_per_hour(per);
            double otSalary = DoubleUtil.doubleKeep2(hours * per);
            bean.setOt_salary(otSalary);
        }
        bean.setOt_salary_multiple(Double.parseDouble(activity.getMultiply()));
        bean.setRemark(activity.getRemark());
        if (model.getRecord(date) != null) {
            model.update(bean, bean.getDate_ymd());
        } else {
            model.save(bean);
        }
    }

    private OverTimeRecordBean getDef(String date) {
        OverTimeSalarySettingBean settingBean = model.getSetting(date);
        OverTimeRecordBean bean = new OverTimeRecordBean();
        String[] split = this.date.split("-");
        bean.setWeek(TimeUtil.getWeek(split[0], split[1], split[2]));
        bean.setWork_type("工作日");
        bean.setOt_salary_multiple(settingBean.getNormal_multiply());
        bean.setOt_salary_per_hour(settingBean.getNormal_salary());
        bean.setBasic_hours(8);
        bean.setBasic_minutes(0);
        bean.setRemark("");
        bean.setOt_salary(0.0);
        return bean;
    }

    public String[] getSetting(String date) {
        OverTimeSalarySettingBean bean = model.getSetting(date);
        String[] names = new String[3];
        names[0] = bean.getNormal_salary() + "元/小时(工作日" + bean.getNormal_multiply() + "倍)";
        names[1] = bean.getWeekend_salary() + "元/小时(休息日" + bean.getWeekend_multiply() + "倍)";
        names[2] = bean.getFestival_salary() + "元/小时(节假日" + bean.getFestival_multiply() + "倍)";
        return names;
    }

    public String getDate() {
        return date;
    }

}
