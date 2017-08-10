package com.hzu.jpg.commonwork.Presenter;

import android.support.v7.widget.RecyclerView;

import com.hzu.jpg.commonwork.activity.StatisticsAddCutActivity;
import com.hzu.jpg.commonwork.adapter.StatisticsAddAdapter;
import com.hzu.jpg.commonwork.enity.Bean.CutBean;
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

public class StatisticsAddCutPresenter {

    LinkedHashMap<String, Boolean> map;
    AddStatisticsItemModel model;
    RecyclerView.Adapter adapter;
    StatisticsAddCutActivity activity;
    CutBean bean;
    boolean hasData = false;

    public StatisticsAddCutPresenter(StatisticsAddCutActivity activity) {
        this.activity = activity;
        if (model == null) {
            model = new AddStatisticsItemModel(activity);
        }
        if (map == null) {
            map = new LinkedHashMap<>();
        }
    }

    public void init(String date) {

        bean = (CutBean) model.getData(date, StatisticsModel.TABLE_CUT, CutBean.class);
        if (bean == null) {
            hasData = false;
            bean = new CutBean();
            bean.setDate_ym(date);
        } else {
            hasData = true;
        }
        Class<?> clazz = bean.getClass();
        List<String> listName = new ArrayList<>(NameMapping.cut_map.keySet());
        for (int i = 0; i < listName.size(); i++) {
            String fieldName = listName.get(i);
            String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            try {
                Method method = clazz.getMethod(methodName);
                double d = (double) method.invoke(bean);
                String name = NameMapping.getCut(fieldName);
                if (d != 0) {
                    map.put(name, true);
                } else {
                    map.put(name, false);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (adapter == null) {
            adapter = new StatisticsAddAdapter(activity, map);
            activity.setRv(adapter);
        }

    }

    public void onSave() {
        double deleteValue=0;
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            String name = NameMapping.getCutName(entry.getKey());
            Class<?> clazz = bean.getClass();
            try {
                if (entry.getValue()) {

                    String methodName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                    Method method = clazz.getMethod(methodName);
                    double d = (double) method.invoke(bean);
                    if (d == 0) {
                        methodName = "s" + methodName.substring(1);
                        method = clazz.getMethod(methodName, Double.TYPE);
                        method.invoke(bean, -1);
                    }
                } else {
                    String methodName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                    Method method = clazz.getMethod(methodName);
                    double d = (double) method.invoke(bean);
                    if (d > 0) {
                        deleteValue+=d;
                    }
                    methodName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                    method = clazz.getMethod(methodName, Double.TYPE);
                    method.invoke(bean, 0);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(deleteValue!=0){
            bean.setCut_total(DoubleUtil.DoubleSubtract(bean.getCut_total(),deleteValue));
            model.deleteCut(bean.getDate_ym(),deleteValue);
        }
        if (hasData) {
            model.update(bean, bean.getDate_ym(), StatisticsModel.TABLE_CUT);
            return;
        }
        model.save(bean, StatisticsModel.TABLE_CUT);
    }


}
