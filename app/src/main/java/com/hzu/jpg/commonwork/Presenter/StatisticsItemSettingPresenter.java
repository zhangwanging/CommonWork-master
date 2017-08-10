package com.hzu.jpg.commonwork.Presenter;

import android.content.Context;

import com.hzu.jpg.commonwork.enity.moudle.StatisticsItemSettingModel;
import com.hzu.jpg.commonwork.utils.NameMapping;


/**
 * Created by Administrator on 2017/3/10.
 */

public class StatisticsItemSettingPresenter {

    StatisticsItemSettingModel model;

    public StatisticsItemSettingPresenter(Context context){
        model=new StatisticsItemSettingModel(context);
    }

    public void save(String date,String name,double value,double oldValue,String table,String itemTotalColumn,Class<?> clazz){
        String columnName= NameMapping.getItemName(name);
        model.update(columnName,date,value,table,clazz);
        model.updateSalary(date,oldValue,value,table,itemTotalColumn);
    }

    public void saveHelpPayment(String date,String name,double value,double oldValue){
        String columnName=NameMapping.getHelpPaymentName(name);
        model.updateSalaryHelpPayment(columnName,oldValue,value,date);
    }

}
