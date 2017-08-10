package com.hzu.jpg.commonwork.enity.moudle;

/**
 * Created by Administrator on 2017/3/25.
 */

public class RegionBean {

    private String id;

    private String name;

    private String pid;

    private String temp;

    private String number;

    private String city;

    private String region;

    private String date;

    public RegionBean() {}

    public RegionBean(String id, String name, String pid
            , String temp, String number, String city
            , String region, String date) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.temp = temp;
        this.number = number;
        this.city = city;
        this.region = region;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RegionBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", temp='" + temp + '\'' +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
