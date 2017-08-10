package com.hzu.jpg.commonwork.Receiver;


import com.hzu.jpg.commonwork.enity.Bean.CompanyOneKeyJobBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class CompanyOneKeyJobReceiver {

    String statu;
    List<CompanyOneKeyJobBean> data;
    String message;


    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public List<CompanyOneKeyJobBean> getData() {
        return data;
    }

    public void setData(List<CompanyOneKeyJobBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
