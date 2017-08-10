package com.hzu.jpg.commonwork.Receiver;

import com.hzu.jpg.commonwork.enity.Bean.OneKeyJobBean;

import java.util.List;


/**
 * Created by Administrator on 2017/4/1.
 */

public class OneKeyJobReceiver {

    String statu;
    List<OneKeyJobBean> data;
    String message;

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public List<OneKeyJobBean> getData() {
        return data;
    }

    public void setData(List<OneKeyJobBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
