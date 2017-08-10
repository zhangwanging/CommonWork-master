package com.hzu.jpg.commonwork.Receiver;


import com.hzu.jpg.commonwork.enity.Bean.ListJobInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class MyListJobInfoReceiver {

    String statu;
    List<ListJobInfoBean> data;
    String message;

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public List<ListJobInfoBean> getData() {
        return data;
    }

    public void setData(List<ListJobInfoBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
