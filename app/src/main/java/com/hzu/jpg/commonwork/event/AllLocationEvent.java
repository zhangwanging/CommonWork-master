package com.hzu.jpg.commonwork.event;

import com.hzu.jpg.commonwork.enity.moudle.AllCityRegionModel;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class AllLocationEvent {

    List<AllCityRegionModel> data;

    public AllLocationEvent(List<AllCityRegionModel> data) {
        this.data = data;
    }

    public List<AllCityRegionModel> getData() {
        return data;
    }

    public void setData(List<AllCityRegionModel> data) {
        this.data = data;
    }
}
