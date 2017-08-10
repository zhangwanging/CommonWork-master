package com.hzu.jpg.commonwork.utils;

import android.widget.Toast;

import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/6/25.
 */

public class WxLoginUtils {
    private static IWXAPI iwxapi;

    public static IWXAPI getWXAPI() {
        if (iwxapi == null) {
            //通过WXAPIFactory创建IWAPI实例
            iwxapi = WXAPIFactory.createWXAPI(MyApplication.getContext(), Config.APP_ID, true);
            //将应用的appid注册到微信
            iwxapi.registerApp(Config.APP_ID);
        }
        return iwxapi;
    }

    /**
     * 微信登录
     */
    public static void WxLogin() {
        if (!judgeCanGo()) {
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        //授权域 获取用户个人信息则填写snsapi_userinfo
        req.scope = "snsapi_userinfo";
        //用于保持请求和回调的状态 可以任意填写
        req.state = "test_login";
        iwxapi.sendReq(req);
    }

    private static boolean judgeCanGo() {
        getWXAPI();
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(MyApplication.getContext(), "请先安装微信应用", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!iwxapi.isWXAppSupportAPI()) {
            Toast.makeText(MyApplication.getContext(), "请先更新微信应用", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
