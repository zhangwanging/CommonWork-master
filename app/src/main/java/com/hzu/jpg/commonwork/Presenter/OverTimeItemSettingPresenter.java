package com.hzu.jpg.commonwork.Presenter;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;


import com.hzu.jpg.commonwork.activity.OverTimeItemSettingActivity;
import com.hzu.jpg.commonwork.activity.StatisticsItemSettingActivity;
import com.hzu.jpg.commonwork.adapter.StatisticsLvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.AllowanceBean;
import com.hzu.jpg.commonwork.enity.Bean.CutBean;
import com.hzu.jpg.commonwork.enity.Bean.HelpPaymentBean;
import com.hzu.jpg.commonwork.enity.moudle.OverTimeItemSettingModel;
import com.hzu.jpg.commonwork.enity.moudle.StatisticsModel;
import com.hzu.jpg.commonwork.utils.NameMapping;
import com.hzu.jpg.commonwork.utils.TimeUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/3/23.
 */

public class OverTimeItemSettingPresenter implements AdapterView.OnItemClickListener {

    OverTimeItemSettingActivity activity;
    OverTimeItemSettingModel model;
    Map<String, Double> map;
    StatisticsLvAdapter adapter;
    String title;
    int type;

    public OverTimeItemSettingPresenter(OverTimeItemSettingActivity activity, String title) {
        this.activity = activity;
        model = new OverTimeItemSettingModel(activity);
        this.title = title;
    }

    public void setData() {
        String date = TimeUtil.getDateYM();
        if (map == null) {
            map = new HashMap<>();
        } else {
            map.clear();
        }
        if (title.equals("补贴设置")) {
            type = StatisticsItemSettingActivity.CHANGE_ALLOWANCE_ITEM;
            setMap(date, StatisticsModel.TABLE_ALLOWANCE, AllowanceBean.class, NameMapping.allowance_map);
        } else if (title.equals("扣款设置")) {
            type = StatisticsItemSettingActivity.CHANGE_CUT_ITEM;
            setMap(date, StatisticsModel.TABLE_CUT, CutBean.class, NameMapping.cut_map);
        } else if (title.equals("代缴设置")) {
            type = StatisticsItemSettingActivity.CHANGE_HELP_PAYMENT;
            setMap(date, StatisticsModel.TABLE_HELP, HelpPaymentBean.class, NameMapping.help_payment_map);
        }

        if (adapter == null) {
            adapter = new StatisticsLvAdapter(activity, map);
            activity.setLv(adapter);
        } else {
            adapter.update(map);
        }

    }

    private void setMap(String date, String table, Class<?> clazz, Map<String, String> nameMapping) {
        Object bean = model.getBean(table, date, clazz);
        if(bean==null){
            try {
                bean=clazz.newInstance();
                Method m=clazz.getDeclaredMethod("setDate_ym",String.class);
                m.invoke(bean,date);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        for (Map.Entry<String, String> entry : nameMapping.entrySet()) {
            String methodName = "get" + Character.toUpperCase(entry.getKey().charAt(0)) + entry.getKey().substring(1);
            try {
                Method method = clazz.getDeclaredMethod(methodName);
                double d = (double) method.invoke(bean);
                map.put(entry.getValue(), d);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = (String) adapter.getItem(position);
        double money = map.get(name);
        Intent intent = new Intent(activity, StatisticsItemSettingActivity.class);
        intent.putExtra("name", name);
        if(money==-1){
            intent.putExtra("money", 0.0);
        }else{
            intent.putExtra("money", money);
        }
        intent.putExtra("date", TimeUtil.getDateYM());
        intent.putExtra("position", position);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, 0);
    }

    public void update(int position, double d) {
        String name= (String) adapter.getItem(position);
        map.put(name,d);
        adapter.update(position, d);
    }
}
