package com.hzu.jpg.commonwork.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.moudle.AllCityRegionModel;
import com.hzu.jpg.commonwork.enity.moudle.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class SharedPreferencesUtil {

    private static final String TAG = "SharedPreferencesUtil";


    public static void saveRegionData(List<AllCityRegionModel> models) {
        String json = JsonUtil.getJsonStringformat(models);
        SharedPreferences sp = Config.CONTEXT.getSharedPreferences(Config.KEY_IS_GET_C_R_MSG, Context.MODE_PRIVATE);
        sp.edit().putString("regionData", json).apply();
    }


    public static List<AllCityRegionModel> getRegionData() {
        SharedPreferences sp = Config.CONTEXT.getSharedPreferences(Config.KEY_IS_GET_C_R_MSG, Context.MODE_PRIVATE);
        String json = sp.getString("regionData", "");
        ObjectMapper mapper = new ObjectMapper();
        List<AllCityRegionModel> models = new ArrayList<>();
        if (!TextUtils.isEmpty(json)) {
            try {
                models = mapper.readValue(json, new TypeReference<List<AllCityRegionModel>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return models;
    }

    public static void saveUserMsg(String phone, String password) {
        SharedPreferences sp = Config.CONTEXT.getSharedPreferences(Config.KEY_USER_MSG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        password = StringUtils.convertMD5(password);
        edit.putString(Config.KEY_USER_NAME, phone);
        edit.putString(Config.KEY_PASSWORD, password);
        edit.apply();
    }

    public static String[] getUserMsg() {
        String[] msg = new String[]{"", "", ""};
        SharedPreferences sp = Config.CONTEXT.getSharedPreferences(Config.KEY_USER_MSG, Context.MODE_PRIVATE);
        String name = sp.getString(Config.KEY_USER_NAME, null);
        String pwd = sp.getString(Config.KEY_PASSWORD, null);
        if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(pwd)) {
            msg[0] = name;
            msg[1] = StringUtils.convertMD5(sp.getString(Config.KEY_PASSWORD, null));
        }
        return msg;
    }

    public static void setSalarySetting(String value) {
        SharedPreferences sp = Config.CONTEXT.getSharedPreferences("salary_setting", Context.MODE_PRIVATE);
        sp.edit().putString("salary", value).apply();
    }

    public static String getSalarySetting() {
        SharedPreferences sp = Config.CONTEXT.getSharedPreferences("salary_setting", Context.MODE_PRIVATE);
        return sp.getString("salary", "10");
    }

}
