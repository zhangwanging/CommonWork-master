package com.hzu.jpg.commonwork.enity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterData implements Serializable {

    private List<FilterEntity> firstOne;
    private List<FilterTwoEntity> firstTwo;
    private List<FilterEntity> second;
    private List<FilterEntity> third;
    private List<FilterEntity> fourth;

    public List<FilterEntity> getFirstOne(){
        return this.firstOne;
    }
    public void setFirstOne(List<FilterEntity> firstOne){
        this.firstOne = firstOne;
    }

    public List<FilterTwoEntity> getFirstTwo() {
        return firstTwo;
    }

    public void setFirstTwo(List<FilterTwoEntity> firstTwo) {
        this.firstTwo = firstTwo;
    }

    public List<FilterEntity> getSecond() {
        return second;
    }

    public void setSecond(List<FilterEntity> second) {
        this.second = second;
    }

    public List<FilterEntity> getThird() {
        return third;
    }

    public void setThird(List<FilterEntity> third) {
        this.third = third;
    }

    public List<FilterEntity> getFourth() {
        return fourth;
    }

    public void setFourth(List<FilterEntity> fourth) {
        this.fourth = fourth;
    }
}
