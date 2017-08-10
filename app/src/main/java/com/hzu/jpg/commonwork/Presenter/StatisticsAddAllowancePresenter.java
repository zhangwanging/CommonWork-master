package com.hzu.jpg.commonwork.Presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.hzu.jpg.commonwork.activity.StatisticsAddAllowanceActivity;
import com.hzu.jpg.commonwork.adapter.StatisticsAddAdapter;
import com.hzu.jpg.commonwork.enity.Bean.AllowanceBean;
import com.hzu.jpg.commonwork.enity.moudle.AddStatisticsItemModel;
import com.hzu.jpg.commonwork.enity.moudle.StatisticsModel;
import com.hzu.jpg.commonwork.utils.DoubleUtil;
import com.hzu.jpg.commonwork.utils.NameMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/3/9.
 */

public class StatisticsAddAllowancePresenter {

    LinkedHashMap<String,Boolean> map;
    AddStatisticsItemModel model;
    RecyclerView.Adapter adapter;
    StatisticsAddAllowanceActivity activity;
    AllowanceBean bean;
    boolean hasData;
    public StatisticsAddAllowancePresenter(StatisticsAddAllowanceActivity activity){
        this.activity=activity;
        if(model==null){
            model=new AddStatisticsItemModel(activity);
        }
        if(map==null){
            map=new LinkedHashMap<>();
        }
    }

    public void init(String date){
        bean= (AllowanceBean) model.getData(date, StatisticsModel.TABLE_ALLOWANCE,AllowanceBean.class);
        if(bean==null){
            bean=new AllowanceBean();
            bean.setDate_ym(date);
            hasData=false;
        }else{
            hasData=true;
        }
        Class<?> clazz=bean.getClass();
        List<String> listName=new ArrayList<>(NameMapping.allowance_map.keySet());
        for(int i=0;i<listName.size();i++){
            String fieldName=listName.get(i);
            String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            try {
                Method method=clazz.getMethod(methodName);
                double d= (double) method.invoke(bean);
                String name= NameMapping.getAllowance(fieldName);
                if(d!=0){
                    map.put(name,true);
                }else{
                    map.put(name,false);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(adapter==null){
            adapter=new StatisticsAddAdapter(activity,map);
            activity.setRv(adapter);
        }
    }

    public void onSave(){
        double deleteTotal=0.0;
        for(Map.Entry<String,Boolean> entry:map.entrySet()){
            Log.d("map",entry.getKey()+entry.getValue());
            String name=NameMapping.getAllowanceName(entry.getKey());
            Class<?> clazz=bean.getClass();
            if(entry.getValue()){
                try {
                    String methodName="get"+Character.toUpperCase(name.charAt(0))+name.substring(1);
                    Method method=clazz.getMethod(methodName);
                    double d= (double) method.invoke(bean);
                    if(d==0){
                        methodName="s"+methodName.substring(1);
                        method=clazz.getMethod(methodName,Double.TYPE);
                        method.invoke(bean,-1);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    String methodName="get"+Character.toUpperCase(name.charAt(0))+name.substring(1);
                    Method method=clazz.getMethod(methodName);
                    double d= (double) method.invoke(bean);
                    if(d>0){
                        deleteTotal+=d;
                    }
                    methodName="set"+Character.toUpperCase(name.charAt(0))+name.substring(1);
                    method = clazz.getMethod(methodName,Double.TYPE);
                    method.invoke(bean,0);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        if(deleteTotal!=0){
            bean.setAllowance_total(DoubleUtil.DoubleSubtract(bean.getAllowance_total(),deleteTotal));
            model.deleteAllowance(bean.getDate_ym(),deleteTotal);
        }
        if (hasData) {
            model.update(bean,bean.getDate_ym(),StatisticsModel.TABLE_ALLOWANCE);
            return ;
        }
        model.save(bean,StatisticsModel.TABLE_ALLOWANCE);
    }
}
