package com.hzu.jpg.commonwork.HourWork.Bean;

/**
 * Created by Administrator on 2017/5/10.
 */

public class HourWorkRecordBean {

    String date_ymd;
    int hours;
    int minutes;
    double salary;
    String remark;
    String week;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate_ymd() {
        return date_ymd;
    }

    public void setDate_ymd(String date_ymd) {
        this.date_ymd = date_ymd;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
