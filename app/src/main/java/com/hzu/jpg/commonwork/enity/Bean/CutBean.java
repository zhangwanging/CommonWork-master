package com.hzu.jpg.commonwork.enity.Bean;

/**
 * Created by Administrator on 2017/3/8.
 */

public class CutBean {

    String date_ym;
    double event_leave;
    double ill_leave;
    double canteen;
    double water_electricity;
    double dormitory;
    double cut_total;

    public double getWater_electricity() {
        return water_electricity;
    }

    public void setWater_electricity(double water_electricity) {
        this.water_electricity = water_electricity;
    }

    public double getIll_leave() {
        return ill_leave;
    }

    public void setIll_leave(double ill_leave) {
        this.ill_leave = ill_leave;
    }

    public double getEvent_leave() {
        return event_leave;
    }

    public void setEvent_leave(double event_leave) {
        this.event_leave = event_leave;
    }

    public double getDormitory() {
        return dormitory;
    }

    public void setDormitory(double dormitory) {
        this.dormitory = dormitory;
    }

    public String getDate_ym() {
        return date_ym;
    }

    public void setDate_ym(String date_ym) {
        this.date_ym = date_ym;
    }

    public double getCanteen() {
        return canteen;
    }

    public void setCanteen(double canteen) {
        this.canteen = canteen;
    }

    public double getCut_total() {
        return cut_total;
    }

    public void setCut_total(double cut_total) {
        this.cut_total = cut_total;
    }
}
