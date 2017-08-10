package com.hzu.jpg.commonwork.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hzu.jpg.commonwork.app.Config;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by cimcitech on 2017/7/20.
 */

public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, null);

        // 将该app注册到微信
        api.registerApp(Config.APP_ID);
    }
}
