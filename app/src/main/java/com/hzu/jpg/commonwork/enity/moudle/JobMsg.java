package com.hzu.jpg.commonwork.enity.moudle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobMsg implements Serializable {

    private String id;
    private String cId;
    private String cname;
    private String job;
    private String label;
    private String number;
    private String city;
    private String region;
    private String details;
    private String date;
    private String salary;
    private String unit;
    private String jobId;
    private String unit_zh_cn;
    private List<String> jobLabel;
    //详细工作时的字段
    private String cover;
    private String required;
    private String province;
    private String moreSalary;
    private String linkMan;
    private String linkPhone;
    private int type;
    private String classify;
    private String describes;
    private int finish;
    private String c_name;
    private String lowPrice;
    private String highPrice;
    private String page;


    public JobMsg() {
    }


    public JobMsg(String id, String cId, String cname, String job, String label, String number
            , String city, String region, String details
            , String date, String salary, String unit, String jobId, String unit_zh_cn, List<String> jobLabel
            , String cover, String required, String province
            , String moreSalary, String linkMan, String linkPhone, int type, String classify, int finish
            , String c_name, String lowPrice, String highPrice, String page,String describes) {
        this.id = id;
        this.cId = cId;
        this.cname = cname;
        this.job = job;
        this.label = label;
        this.number = number;
        this.city = city;
        this.region = region;
        this.details = details;
        this.date = date;
        this.salary = salary;
        this.unit = unit;
        this.jobId = jobId;
        this.unit_zh_cn = unit_zh_cn;
        this.jobLabel = jobLabel;
        this.cover = cover;
        this.required = required;
        this.province = province;
        this.moreSalary = moreSalary;
        this.linkMan = linkMan;
        this.linkPhone = linkPhone;
        this.type = type;
        this.classify = classify;
        this.finish = finish;
        this.c_name = c_name;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        this.page = page;
        this.describes = describes;
    }


    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getUnit_zh_cn() {
        return unit_zh_cn;
    }

    public void setUnit_zh_cn(String unit_zh_cn) {
        this.unit_zh_cn = unit_zh_cn;
    }

    public List<String> getJobLabel() {
        return jobLabel;
    }

    public void setJobLabel(List<String> jobLabel) {
        this.jobLabel = jobLabel;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMoreSalary() {
        return moreSalary;
    }

    public void setMoreSalary(String moreSalary) {
        this.moreSalary = moreSalary;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }


    @Override
    public String toString() {
        return "JobMsg{" +
                "id='" + id + '\'' +
                ", cId='" + cId + '\'' +
                ", cname='" + cname + '\'' +
                ", job='" + job + '\'' +
                ", label='" + label + '\'' +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", details='" + details + '\'' +
                ", date='" + date + '\'' +
                ", salary='" + salary + '\'' +
                ", unit='" + unit + '\'' +
                ", jobId='" + jobId + '\'' +
                ", unit_zh_cn='" + unit_zh_cn + '\'' +
                ", jobLabel=" + jobLabel +
                ", cover='" + cover + '\'' +
                ", required='" + required + '\'' +
                ", province='" + province + '\'' +
                ", moreSalary='" + moreSalary + '\'' +
                ", linkMan='" + linkMan + '\'' +
                ", linkPhone='" + linkPhone + '\'' +
                ", type=" + type +
                ", classify='" + classify + '\'' +
                ", finish=" + finish +
                ", c_name='" + c_name + '\'' +
                ", lowPrice='" + lowPrice + '\'' +
                ", highPrice='" + highPrice + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}
