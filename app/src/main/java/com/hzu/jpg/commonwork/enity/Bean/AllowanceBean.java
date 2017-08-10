package com.hzu.jpg.commonwork.enity.Bean;

/**
 * Created by Administrator on 2017/3/8.
 */

public class AllowanceBean {

    String date_ym;
    double attendance_bonus;
    double position;
    double board_wages;
    double live;
    double high_temperature;
    double level;
    double environment;
    double traffic;
    double performance;
    double other;
    double allowance_total;

    public double getAllowance_total() {
        return allowance_total;
    }

    public void setAllowance_total(double allowance_total) {
        this.allowance_total = allowance_total;
    }

    public double getAttendance_bonus() {
        return attendance_bonus;
    }

    public void setAttendance_bonus(double attendance_bonus) {
        this.attendance_bonus = attendance_bonus;
    }

    public double getBoard_wages() {
        return board_wages;
    }

    public void setBoard_wages(double board_wages) {
        this.board_wages = board_wages;
    }

    public String getDate_ym() {
        return date_ym;
    }

    public void setDate_ym(String date_ym) {
        this.date_ym = date_ym;
    }

    public double getHigh_temperature() {
        return high_temperature;
    }

    public void setHigh_temperature(double high_temperature) {
        this.high_temperature = high_temperature;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getLive() {
        return live;
    }

    public void setLive(double live) {
        this.live = live;
    }

    public double getEnvironment() {
        return environment;
    }

    public void setEnvironment(double environment) {
        this.environment = environment;
    }

    public double getTraffic() {
        return traffic;
    }

    public void setTraffic(double traffic) {
        this.traffic = traffic;
    }

    public double getPerformance() {
        return performance;
    }

    public void setPerformance(double performance) {
        this.performance = performance;
    }

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
    }
}
