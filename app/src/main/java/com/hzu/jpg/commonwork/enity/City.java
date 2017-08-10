package com.hzu.jpg.commonwork.enity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * author zaaach on 2016/1/26.
 */
@JsonIgnoreProperties
public class City {
    private String name;
    private String pinyin;

    private String id;

    public City() {}

    public City(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
