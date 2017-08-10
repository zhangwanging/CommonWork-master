package com.hzu.jpg.commonwork.enity.Bean;

public class OverTimeRecordBean {

    private String date_ymd;
    private String week;
    private int basic_hours;
    private int basic_minutes;
    private int ot_hours;
    private int ot_minutes;
    private double ot_salary_multiple;  //倍数
    private double ot_salary;
    private double ot_salary_per_hour;
    private String work_type;
    private String remark;

    public int getBasic_minutes() {
        return basic_minutes;
    }

    public void setBasic_minutes(int basic_minutes) {
        this.basic_minutes = basic_minutes;
    }

    public int getBasic_hours() {
        return basic_hours;
    }

    public void setBasic_hours(int basic_hours) {
        this.basic_hours = basic_hours;
    }

    public String getDate_ymd() {
        return date_ymd;
    }

    public void setDate_ymd(String date_ymd) {
        this.date_ymd = date_ymd;
    }

    public int getOt_hours() {
        return ot_hours;
    }

    public void setOt_hours(int ot_hours) {
        this.ot_hours = ot_hours;
    }

    public int getOt_minutes() {
        return ot_minutes;
    }

    public void setOt_minutes(int ot_minutes) {
        this.ot_minutes = ot_minutes;
    }

    public double getOt_salary_multiple() {
        return ot_salary_multiple;
    }

    public void setOt_salary_multiple(double ot_salary_multiple) {
        this.ot_salary_multiple = ot_salary_multiple;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }


    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getOt_salary() {
        return ot_salary;
    }

    public void setOt_salary(double ot_salary) {
        this.ot_salary = ot_salary;
    }

    public double getOt_salary_per_hour() {
        return ot_salary_per_hour;
    }

    public void setOt_salary_per_hour(double ot_salary_per_hour) {
        this.ot_salary_per_hour = ot_salary_per_hour;
    }
}
