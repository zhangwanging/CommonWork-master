package com.hzu.jpg.commonwork.enity.Bean;

/**
 * Created by Administrator on 2017/3/17.
 */

public class OverTimeSalarySettingBean {

    String date_ym;
    double basic_salary;
    double salary_per_hour;
    double normal_multiply;
    double normal_salary;
    double weekend_multiply;
    double weekend_salary;
    double festival_multiply;
    double festival_salary;

    public String getDate_ym() {
        return date_ym;
    }

    public void setDate_ym(String date_ym) {
        this.date_ym = date_ym;
    }

    public double getBasic_salary() {
        return basic_salary;
    }

    public void setBasic_salary(double basic_salary) {
        this.basic_salary = basic_salary;
    }

    public double getSalary_per_hour() {
        return salary_per_hour;
    }

    public void setSalary_per_hour(double salary_per_hour) {
        this.salary_per_hour = salary_per_hour;
    }

    public double getNormal_multiply() {
        return normal_multiply;
    }

    public void setNormal_multiply(double normal_multiply) {
        this.normal_multiply = normal_multiply;
    }

    public double getNormal_salary() {
        return normal_salary;
    }

    public void setNormal_salary(double normal_salary) {
        this.normal_salary = normal_salary;
    }

    public double getWeekend_multiply() {
        return weekend_multiply;
    }

    public void setWeekend_multiply(double weekend_multiply) {
        this.weekend_multiply = weekend_multiply;
    }

    public double getWeekend_salary() {
        return weekend_salary;
    }

    public void setWeekend_salary(double weekend_salary) {
        this.weekend_salary = weekend_salary;
    }

    public double getFestival_salary() {
        return festival_salary;
    }

    public void setFestival_salary(double festival_salary) {
        this.festival_salary = festival_salary;
    }

    public double getFestival_multiply() {
        return festival_multiply;
    }

    public void setFestival_multiply(double festival_multiply) {
        this.festival_multiply = festival_multiply;
    }
}
