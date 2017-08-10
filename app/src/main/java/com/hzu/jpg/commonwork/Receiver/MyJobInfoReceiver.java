package com.hzu.jpg.commonwork.Receiver;


import com.hzu.jpg.commonwork.enity.Bean.MyJobInfoBean;

/**
 * Created by Administrator on 2017/4/1.
 */

public class MyJobInfoReceiver {

    String statu;
    MyJobInfoBean data;
    String message;

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public MyJobInfoBean getData() {
        return data;
    }

    public void setData(MyJobInfoBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
