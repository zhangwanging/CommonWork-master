package com.hzu.jpg.commonwork.HourWork.Presenter;

import com.hzu.jpg.commonwork.HourWork.Activity.HourWorkAddRecordActivity;
import com.hzu.jpg.commonwork.HourWork.Bean.HourWorkRecordBean;
import com.hzu.jpg.commonwork.HourWork.Model.HourWorkAddRecordModel;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordBean;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeSalarySettingBean;
import com.hzu.jpg.commonwork.utils.DoubleUtil;
import com.hzu.jpg.commonwork.utils.TimeUtil;

/**
 * Created by Administrator on 2017/5/10.
 */

public class HourWorkAddRecordPresenter {

    HourWorkAddRecordActivity activity;
    HourWorkAddRecordModel model;
    String date;

    public HourWorkAddRecordPresenter(HourWorkAddRecordActivity activity) {
        this.activity = activity;
        model = new HourWorkAddRecordModel(activity);
    }

    public void save() {
        int hours = activity.getHours();
        int minutes = activity.getMinutes();
        String remark = activity.getRemark();
        double salaryPerHour = Double.parseDouble(activity.getSalaryPerHour());
        double time = TimeUtil.Time2Double(hours, minutes);
        double salary = DoubleUtil.doubleKeep2(salaryPerHour * time);
        HourWorkRecordBean bean = new HourWorkRecordBean();
        bean.setDate_ymd(date);
        bean.setHours(hours);
        bean.setMinutes(minutes);
        bean.setRemark(remark);
        bean.setSalary(salary);
        bean.setWeek(activity.getWeek());
        model.save(bean);
    }

    public String getSetting() {
        return String.valueOf(model.getSetting());
    }

    public void setData(String date) {
        HourWorkRecordBean bean = model.getRecord(date);
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
        activity.setHour(bean.getHours());
        activity.setMinute(bean.getMinutes());
        activity.setRemark(bean.getRemark());
        activity.setWeek(bean.getWeek());
        activity.setSalary(getSetting());
        if (bean.getRemark() != null) {
            activity.setRemark(bean.getRemark());
        }
    }

    public void delete() {
        model.delete(date);
        activity.hideDelete(true);
    }

    private HourWorkRecordBean getDef(String date) {
        double salary = model.getSetting();
        HourWorkRecordBean bean = new HourWorkRecordBean();
        String[] split = this.date.split("-");
        bean.setWeek(TimeUtil.getWeek(split[0], split[1], split[2]));
        bean.setHours(0);
        bean.setMinutes(0);
        bean.setRemark("");
        bean.setSalary(salary);
        return bean;
    }

    public String getDate() {
        return date;
    }

}
