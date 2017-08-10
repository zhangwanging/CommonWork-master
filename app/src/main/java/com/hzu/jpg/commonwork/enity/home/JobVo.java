package com.hzu.jpg.commonwork.enity.home;

import com.hzu.jpg.commonwork.enity.service.NewsVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cimcitech on 2017/6/5.
 */

public class JobVo {

    private int statu;
    private ArrayList<Data> data;
    private String message;

    public int getStatu() {
        return statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data {
        private int id;
        private int cId;
        private String cname;
        private String cover;
        private String job;
        private String salary;
        private String label;
        private List<String> jobLabel;
        private String city;
        private String unit_zh_cn;
        private String unit;
        private String region;
        private String details;
        private String date;
        private String classify;
        private int number;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getcId() {
            return cId;
        }

        public void setcId(int cId) {
            this.cId = cId;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<String> getJobLabel() {
            return jobLabel;
        }

        public void setJobLabel(List<String> jobLabel) {
            this.jobLabel = jobLabel;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getUnit_zh_cn() {
            return unit_zh_cn;
        }

        public void setUnit_zh_cn(String unit_zh_cn) {
            this.unit_zh_cn = unit_zh_cn;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getClassify() {
            return classify;
        }

        public void setClassify(String classify) {
            this.classify = classify;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
