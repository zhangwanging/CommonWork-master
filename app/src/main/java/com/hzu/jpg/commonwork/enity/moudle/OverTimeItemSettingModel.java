package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;

import com.hzu.jpg.commonwork.db.DaoTemplate;


/**
 * Created by Administrator on 2017/3/23.
 */

public class OverTimeItemSettingModel {

    DaoTemplate template;

    public OverTimeItemSettingModel(Context context){
        template=new DaoTemplate(context);
    }

    public Object getBean(String table,String date,Class clazz){
        return template.query(table,"date_ym=?",new String[]{date},clazz);
    }

}
