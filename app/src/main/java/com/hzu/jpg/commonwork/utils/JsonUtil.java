package com.hzu.jpg.commonwork.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class JsonUtil {

    public static  Object Json2Object(String jsonData, Class<?> clazz) {
        Gson gson = new Gson();
        Object o = gson.fromJson(jsonData, clazz);
        return o;
    }

    public static Object[] Json2Array(String data,Class<?> clazz){
        Gson gson=new Gson();
        return (Object[]) gson.fromJson(data,clazz);
    }

    public static List Json2List(String s, TypeToken token){
        Gson gson=new Gson();
        s=s.substring(s.indexOf("["),s.indexOf("]")+1);
        List list = gson.fromJson(s, token.getType());
        return list;

    }

    //将一个类转换陈json字符串
    public static <T> String getJsonStringformat(T oject) {
        ObjectMapper mapper = new ObjectMapper();
        String JsonString = "";
        try {
            JsonString = mapper.writeValueAsString(oject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return JsonString;
        }
        return JsonString;
    }

    //将一个json字符串转成list
    public static ArrayList<String> getlistfromString(String string) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> list = new ArrayList<>();
        if (StringUtils.isEmpty(string)) {
            return list;
        }
        try {
            list = mapper.readValue(string, new TypeReference<ArrayList<String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
