package com.hzu.jpg.commonwork.enity.Bean;

public class OverTimeRecordMonthBean {

    private double salary;
    private double ot_salary;
    private double ot_hours;
    private double basic_salary;
    private String date_ym;
    private int hour_work;

    public int getHour_work() {
        return hour_work;
    }

    public void setHour_work(int hour_work) {
        this.hour_work = hour_work;
    }

    public String getDate_ym() {
        return date_ym;
    }

    public void setDate_ym(String date_ym) {
        this.date_ym = date_ym;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getOt_salary() {
        return ot_salary;
    }

    public void setOt_salary(double ot_salary) {
        this.ot_salary = ot_salary;
    }

    public double getOt_hours() {
        return ot_hours;
    }

    public void setOt_hours(double ot_hours) {
        this.ot_hours = ot_hours;
    }

    public double getBasic_salary() {
        return basic_salary;
    }

    public void setBasic_salary(double basic_salary) {
        this.basic_salary = basic_salary;
    }
}
