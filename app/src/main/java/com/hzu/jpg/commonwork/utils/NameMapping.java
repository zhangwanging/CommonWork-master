package com.hzu.jpg.commonwork.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/8.
 */

public class NameMapping {

    public static Map<String,String> allowance_map=new HashMap<>();
    public static Map<String,String> cut_map=new HashMap<>();
    public static Map<String,String> help_payment_map=new HashMap<>();

    public static List<Map<String,String>> maps=new ArrayList<>();

    static {


        allowance_map.put("attendance_bonus","全勤奖");
        allowance_map.put("position","岗位补贴");
        allowance_map.put("board_wages","伙食");
        allowance_map.put("live","生活补贴");
        allowance_map.put("high_temperature","高温补贴");
        allowance_map.put("level","星级补贴");
        allowance_map.put("environment","环境补贴");
        allowance_map.put("traffic","交通补贴");
        allowance_map.put("performance","效绩补贴");
        allowance_map.put("other","其他补贴");

        cut_map.put("event_leave","事假");
        cut_map.put("ill_leave","病假");
        cut_map.put("canteen","食堂");
        cut_map.put("water_electricity","水电");
        cut_map.put("dormitory","住宿");

        maps.add(allowance_map);
        maps.add(cut_map);

        help_payment_map.put("social_security","社保");
        help_payment_map.put("accumulation_fund","公积金");
        help_payment_map.put("income_tax","个人所得税");
    }

    public static String getAllowance(String name){
        return allowance_map.get(name);
    }

    public static String getAllowanceName(String values){
        Set<Map.Entry<String,String>> set=allowance_map.entrySet();
        for(Map.Entry<String,String> entry:set){
            if(entry.getValue().equals(values)){
                return entry.getKey();
            }
        }
        return null;
    }

    public static String getCut(String name){
        return cut_map.get(name);
    }

    public static String getCutName(String values){
        Set<Map.Entry<String,String>> set=cut_map.entrySet();
        for(Map.Entry<String,String> entry:set){
            if(entry.getValue().equals(values)){
                return entry.getKey();
            }
        }
        return null;
    }

    public static String getHelpPaymentName(String values){
        Set<Map.Entry<String,String>> set=help_payment_map.entrySet();
        for(Map.Entry<String,String> entry:set){
            if(entry.getValue().equals(values)){
                return entry.getKey();
            }
        }
        return null;
    }
    public static String getItemName(String value){
        for (int i=0;i<maps.size();i++){
            Map<String,String> map=maps.get(i);
            for(Map.Entry<String,String> entry:map.entrySet()){
                if(entry.getValue().equals(value)){
                    return entry.getKey();
                }
            }
        }
        return null;
    }

}
