package com.hzu.jpg.commonwork.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.utils.AppUtils;
import com.hzu.jpg.commonwork.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class VersionService extends AVersionService {

    private double serverVersion = -1;
    private int clientVersion = -1;
    private String url = "";
    private static final String TAG = "VersionService";

    @Override
    public void onResponses(AVersionService service, String response) {
        Log.e(TAG, "onResponses: " + response, null);
        clientVersion = AppUtils.getVerCode(this);
        JSONObject obj = null;
        try {
            obj = new JSONObject(response);
            url = obj.getString(Config.KEY_UPDATE_URL);
            service.showVersionDialog(Config.IP + url, getString(R.string.please_update));
        } catch (JSONException e) {
            stopSelf();
            ToastUtil.showToast("获取最新版本失败");
            e.printStackTrace();
        }
    }
}
