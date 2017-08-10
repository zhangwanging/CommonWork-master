package com.hzu.jpg.commonwork.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;


import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.StatisticsItemSettingActivity;
import com.hzu.jpg.commonwork.adapter.StatisticsLvAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.Bean.AllowanceBean;
import com.hzu.jpg.commonwork.enity.Bean.CutBean;
import com.hzu.jpg.commonwork.enity.Bean.HelpPaymentBean;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;
import com.hzu.jpg.commonwork.enity.moudle.StatisticsModel;
import com.hzu.jpg.commonwork.fragment.StatisticsFragment;
import com.hzu.jpg.commonwork.utils.DoubleUtil;
import com.hzu.jpg.commonwork.utils.NameMapping;
import com.hzu.jpg.commonwork.widgit.MyLinearLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/3/8.
 */

public class StatisticsPresenter {

    StatisticsFragment fragment;
    StatisticsModel model;
    Map<String, Double> allowanceMap;
    Map<String, Double> cutMap;
    StatisticsLvAdapter adapter_allowance;
    StatisticsLvAdapter adapter_cut;


    public StatisticsPresenter(Fragment fragment) {
        this.fragment = (StatisticsFragment) fragment;
        model = new StatisticsModel(fragment.getContext());
        if (allowanceMap == null) {
            allowanceMap = new LinkedHashMap<>();
        }
        if (cutMap == null) {
            cutMap = new LinkedHashMap<>();
        }
    }

    public void initData(String date) {
        setBasicData(date);
        setAllowanceData(date);
        setCutData(date);
        setHelpPayment(date);
    }

    public void setBasicData(String date) {
        OverTimeRecordMonthBean bean = model.getBasicData(date);
        double salary = 0;
        if (bean == null) {
            if (Config.hourWork) {
                fragment.setBasicSalary("0");
                fragment.setBasicItem("0");
                fragment.setOtSalary("0");
                fragment.setTotalSalary(String.valueOf(salary));
            } else {
                salary =model.getBasicSalary(date);
                String s = Double.toString(salary);
                fragment.setBasicSalary(s);
                fragment.setBasicItem(s);
                fragment.setOtSalary("0");
                fragment.setTotalSalary(String.valueOf(salary));
            }
            return;
        }
        fragment.setDate(date);
        if (Config.hourWork) {
            fragment.setBasicItem(String.valueOf(bean.getOt_salary()));
            fragment.setBasicSalary("0");
            fragment.setOtSalary(Double.toString(bean.getOt_salary()));
            fragment.setTotalSalary(Double.toString(bean.getSalary()));
        } else {
            double basic = bean.getBasic_salary();
            double ot_salary = bean.getOt_salary();
            fragment.setBasicItem(String.valueOf(DoubleUtil.doubleAdd(basic, ot_salary)));
            fragment.setBasicSalary(String.valueOf(basic));
            fragment.setOtSalary(Double.toString(bean.getOt_salary()));
            fragment.setTotalSalary(Double.toString(bean.getSalary()));
        }
    }

    public void setAllowanceData(String date) {
        AllowanceBean allowanceBean = (AllowanceBean) model.getData(date, StatisticsModel.TABLE_ALLOWANCE, AllowanceBean.class);
        allowanceMap.clear();
        if (allowanceBean == null) {
            if (adapter_allowance != null) {
                adapter_allowance.update(allowanceMap);
            }
            fragment.setAllowanceTotal("0");
            return;
        }
        List<String> listName = new ArrayList<>(NameMapping.allowance_map.keySet());
        for (int i = 0; i < listName.size(); i++) {
            String fieldName = listName.get(i);
            String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            Method method = null;
            try {
                method = allowanceBean.getClass().getMethod(methodName);
                double d = (double) method.invoke(allowanceBean);
                if (d == 0) {
                    continue;
                }
                if (d == -1) {
                    d = 0;
                }
                allowanceMap.put(NameMapping.getAllowance(fieldName), d);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        fragment.setAllowanceTotal(String.valueOf(allowanceBean.getAllowance_total()));

        if (adapter_allowance == null) {
            adapter_allowance = new StatisticsLvAdapter(fragment.getContext(), allowanceMap);
            fragment.setLv_allowance(adapter_allowance);
        } else {
            adapter_allowance.update(allowanceMap);
        }
    }

    public void setCutData(String date) {
        CutBean cutBean = (CutBean) model.getData(date, StatisticsModel.TABLE_CUT, CutBean.class);
        cutMap.clear();
        if (cutBean == null) {
            if (adapter_cut != null) {
                adapter_cut.update(cutMap);
            }
            fragment.setCutTotal("0");
            return;
        }
        List<String> listName = new ArrayList<>(NameMapping.cut_map.keySet());
        for (int i = 0; i < listName.size(); i++) {
            String fieldName = listName.get(i);
            String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            Method method = null;
            try {
                method = cutBean.getClass().getMethod(methodName);
                double d = (double) method.invoke(cutBean);
                if (d == 0) {
                    continue;
                }
                if (d == -1) {
                    d = 0;
                }
                cutMap.put(NameMapping.getCut(fieldName), d);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        fragment.setCutTotal(Double.toString(cutBean.getCut_total()));
        if (adapter_cut == null) {
            adapter_cut = new StatisticsLvAdapter(fragment.getContext(), cutMap);
            fragment.setLv_cut(adapter_cut);
        } else {
            adapter_cut.update(cutMap);
        }
    }

    public void setHelpPayment(String date) {
        HelpPaymentBean bean = (HelpPaymentBean) model.getData(date, StatisticsModel.TABLE_HELP, HelpPaymentBean.class);
        if (bean == null) {
            fragment.setAccumulation("0.0");
            fragment.setIncomeTax("0.0");
            fragment.setSocialSecurity("0.0");
            fragment.setHelpPaymentTotal("0.0");
            return;
        }
        fragment.setSocialSecurity(String.valueOf(bean.getSocial_security()));
        fragment.setAccumulation(String.valueOf(bean.getAccumulation_fund()));
        fragment.setIncomeTax(String.valueOf(bean.getIncome_tax()));
        fragment.setHelpPaymentTotal(String.valueOf(bean.getHelp_payment_total()));
    }

    public void itemSetting(int type, String date, View view, int position) {
        TextView tv = (TextView) view.findViewById(R.id.tv_item_statistics_title);
        String name =  tv.getText().toString();
        tv = (TextView) view.findViewById(R.id.tv_item_statistics_money);
        String money =   tv.getText().toString();
        Intent intent = new Intent(fragment.getActivity(), StatisticsItemSettingActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("money", Double.parseDouble(money));
        intent.putExtra("name", name);
        intent.putExtra("type", type);
        intent.putExtra("position", position);
        fragment.startActivityForResult(intent, 0);
    }

    public void helpItemSetting(View v, String date) {
        MyLinearLayout ll = (MyLinearLayout) v;
        String name = ll.getName();
        String money = ll.getMoney();
        Intent intent = new Intent(fragment.getActivity(), StatisticsItemSettingActivity.class);
        intent.putExtra("date", date);
        intent.putExtra("money", Double.parseDouble(money));
        intent.putExtra("name", name);
        intent.putExtra("type", StatisticsItemSettingActivity.CHANGE_HELP_PAYMENT);
        fragment.startActivityForResult(intent, 0);
    }

    public void updateAllowance(int position, double d) {
        double total = Double.parseDouble(fragment.getAllowanceTotal());
        double oldView = adapter_allowance.update(position, d);
        total = DoubleUtil.doubleKeep2(DoubleUtil.doubleAdd(DoubleUtil.DoubleSubtract(total, oldView), d));
        fragment.setAllowanceTotal(Double.toString(total));
    }

    public void updateCut(int position, double d) {
        double total = Double.parseDouble(fragment.getCutTotal());
        double oldView = adapter_cut.update(position, d);
        total = DoubleUtil.doubleKeep2(DoubleUtil.DoubleSubtract(DoubleUtil.doubleAdd(total, d), oldView));
        fragment.setCutTotal(Double.toString(total));
    }

}
