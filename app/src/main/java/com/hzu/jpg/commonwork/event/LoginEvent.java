package com.hzu.jpg.commonwork.event;

/**
 * Created by Administrator on 2017/4/16.
 */

public class LoginEvent {
    boolean isLogin;


    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
