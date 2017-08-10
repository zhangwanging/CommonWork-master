package com.hzu.jpg.commonwork.event;

/**
 * Created by Azusa on 2016/7/14.
 */
public class AddressEvent {
    private String address;

    public AddressEvent(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
