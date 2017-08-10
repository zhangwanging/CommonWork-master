package com.hzu.jpg.commonwork.Receiver;

import com.hzu.jpg.commonwork.enity.Bean.CompanyJobsListBean;

import java.util.List;


/**
 * Created by Administrator on 2017/4/10.
 */

public class CompanyJobsListReceiver {
    String statu;
    List<CompanyJobsListBean> data;
    String message;

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public List<CompanyJobsListBean> getData() {
        return data;
    }

    public void setData(List<CompanyJobsListBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
