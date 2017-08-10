package com.hzu.jpg.commonwork.utils;

import android.graphics.Bitmap;

/**
 * Created by cimcssc on 2017/2/17.
 * 系统常量
 */

public class Constants {
    //www.chaojilanling.com/managerSystem
    //http://121.35.242.203:8080/imagesServer
    //百度云
    //http://120.77.244.255:8080/managerSystem/
    //http://120.77.244.255:8080/imagesServer
    public static final String getHttpUrl = "http://www.chaojilanling.com/managerSystem/";
    public static final String imageUrl = "http://www.jiongzhiw.com";
    public static int fragment_number = 0;
    public static boolean isLogin = false;
    public static MyHttpCookies li;
    public static String KEY_LOGIN_AUTO = "key_login_auto";
    public static String COOKIES = "cookies";
    public static int requestCodeTime = 60;
    public static final String REGISTERED_USER_ROLE = "1";
    public static String workersId = ""; //用户登录后的加密ID
    public static String userId = "";//用户登录后的没有加密的ID
    public static Bitmap photo = null; //工作照
    public static boolean isOneQQlogin = false;
}
