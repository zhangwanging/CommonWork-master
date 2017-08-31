package com.hzu.jpg.commonwork.callback;

import android.util.Log;
import android.view.Gravity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.moudle.JobMsg;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Response;


/**
 * Created by Administrator on 2017/3/22.
 */

public abstract class JobMsgCallback extends Callback<List<JobMsg>> {

    private static final String TAG = "JobMsgCallback";

    @Override
    public List<JobMsg> parseNetworkResponse(Response response, int id) throws Exception {
        List<JobMsg> jobMsgList = null;
        String result = response.body().string();
        Log.e(TAG, "parseNetworkResponse: " + result, null);
        JSONObject jsonObject = new JSONObject(result);
        String listResponse = null;
        int statue = jsonObject.getInt(Config.KEY_STATU);
        switch (statue) {
            case Config.STATUS_FAIL:
                EventBus.getDefault().post(jsonObject.get(Config.KEY_MESSAGE));
                break;
            case Config.STATUS_SUCCESS:
                listResponse = jsonObject.get(Config.KEY_DATA).toString();
                ObjectMapper mapper = new ObjectMapper();
                jobMsgList = mapper.readValue(listResponse, new TypeReference<List<JobMsg>>() {
                });
                break;
        }
        return jobMsgList;
    }
}
