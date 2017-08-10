package com.hzu.jpg.commonwork.enity;

import java.io.Serializable;

/**
 * Created by zhutao on 2017/8/2 0002.
 */

public class Address implements Serializable{

    private String addr_id;
    private String address;
    private int user_id;

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
