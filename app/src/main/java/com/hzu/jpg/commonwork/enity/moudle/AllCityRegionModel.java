package com.hzu.jpg.commonwork.enity.moudle;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class AllCityRegionModel {

    private String city;

    private List<String> region;


    public AllCityRegionModel() {}

    public AllCityRegionModel(String city, List<String> region) {
        this.city = city;
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getRegion() {
        return region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "AllCityRegionModel{" +
                "region=" + region +
                ", city='" + city + '\'' +
                '}';
    }

}
