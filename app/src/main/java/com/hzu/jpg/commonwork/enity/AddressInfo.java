package com.hzu.jpg.commonwork.enity;

import java.util.List;

/**
 * Created by zhutao on 2017/8/2 0002.
 */

public class AddressInfo {

    private int amount;
    private int lgstatus;
    private List<Address> addrList;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getLgstatus() {
        return lgstatus;
    }

    public void setLgstatus(int lgstatus) {
        this.lgstatus = lgstatus;
    }

    public List<Address> getAddrList() {
        return addrList;
    }

    public void setAddrList(List<Address> addrList) {
        this.addrList = addrList;
    }
}
