package com.hzu.jpg.commonwork.Presenter;


import com.hzu.jpg.commonwork.activity.OverTimeSalarySettingActivity;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeSalarySettingBean;
import com.hzu.jpg.commonwork.enity.moudle.OverTimeSalarySettingModel;
import com.hzu.jpg.commonwork.utils.DoubleUtil;
import com.hzu.jpg.commonwork.utils.TimeUtil;

/**
 * Created by Administrator on 2017/3/17.
 */

public class OverTimeSalarySettingPresenter {

    private OverTimeSalarySettingActivity activity;
    OverTimeSalarySettingModel model;
    private String date;

    public OverTimeSalarySettingPresenter(OverTimeSalarySettingActivity activity) {
        this.activity = activity;
        model=new OverTimeSalarySettingModel(activity);
    }

    public void setData(){
        String date= TimeUtil.getDateYM();
        OverTimeSalarySettingBean bean=model.getBean(date);
        if(bean==null){
            bean=getDefBean(date);
            model.save(bean);
        }
        this.date=bean.getDate_ym();
        activity.setBasic_salary(Double.toString(bean.getBasic_salary()));
        activity.setSalary_per_hour(Double.toString(bean.getSalary_per_hour()));
        activity.setNormal_multiply(Double.toString(bean.getNormal_multiply()));
        activity.setNormal_salary(Double.toString(bean.getNormal_salary()));
        activity.setFestival_salary(Double.toString(bean.getFestival_salary()));
        activity.setFestival_multiply(Double.toString(bean.getFestival_multiply()));
        activity.setWeekend_multiply(Double.toString(bean.getWeekend_multiply()));
        activity.setWeekend_salary(Double.toString(bean.getWeekend_salary()));
    }
    public void saveData(){
        OverTimeSalarySettingBean bean=new OverTimeSalarySettingBean();
        bean.setDate_ym(date);
        bean.setBasic_salary(Double.parseDouble(activity.getBasic_salary()));
        bean.setSalary_per_hour(Double.parseDouble(activity.getSalary_per_hour()));
        bean.setNormal_multiply(Double.parseDouble(activity.getNormal_multiply()));
        bean.setNormal_salary(Double.parseDouble(activity.getNormal_salary()));
        bean.setFestival_salary(Double.parseDouble(activity.getFestival_salary()));
        bean.setFestival_multiply(Double.parseDouble(activity.getFestival_multiply()));
        bean.setWeekend_multiply(Double.parseDouble(activity.getWeekend_multiply()));
        bean.setWeekend_salary(Double.parseDouble(activity.getWeekend_salary()));
        model.update(bean,bean.getDate_ym());

    }

    public String calculateByMultiply(double multiply){
        double salary=multiply*Double.parseDouble(activity.getSalary_per_hour());
        return DoubleUtil.doubleKeep2ForString(salary);
    }

    public String calculateBySalary(double salary){
        double multiply=salary/Double.parseDouble(activity.getSalary_per_hour());
        return DoubleUtil.doubleKeep2ForString(multiply);
    }

    public void onSalaryPerHourChange(double salaryPerHour){;

        //activity.setBasic_salary("0");
        //clearMultiply();
        changeItem(salaryPerHour);
    }

    public void onBasicSalaryChange(double basicSalary){
        double salaryPerHour=DoubleUtil.doubleKeep2(basicSalary/21.75/8);
        activity.setSalary_per_hour(Double.toString(salaryPerHour));
        onSalaryPerHourChange(salaryPerHour);

        //activity.setSalary_per_hour("0");
        //setMultiply(basicSalary);
        //changeItem(salaryPerHour);
    }
    private void changeItem(double salaryPerHour){
        double normalSalary=Double.parseDouble(activity.getNormal_multiply())*salaryPerHour;
        activity.setNormal_salary(DoubleUtil.doubleKeep2ForString(normalSalary));

        double weekendSalary=Double.parseDouble(activity.getWeekend_multiply())*salaryPerHour;
        activity.setWeekend_salary(DoubleUtil.doubleKeep2ForString(weekendSalary));

        double festivalSalary=Double.parseDouble(activity.getFestival_multiply())*salaryPerHour;
        activity.setFestival_salary(DoubleUtil.doubleKeep2ForString(festivalSalary));
    }

    public static OverTimeSalarySettingBean getDefBean(String date){
        OverTimeSalarySettingBean bean=new OverTimeSalarySettingBean();
        bean.setDate_ym(date);
        bean.setBasic_salary(2000);
        double salaryPerHour=11.49;
        bean.setSalary_per_hour(salaryPerHour);
        bean.setNormal_multiply(1.5);
        bean.setNormal_salary(DoubleUtil.doubleKeep2(salaryPerHour*1.5));
        bean.setWeekend_multiply(2);
        bean.setWeekend_salary(DoubleUtil.doubleKeep2(salaryPerHour*2));
        bean.setFestival_multiply(3);
        bean.setFestival_salary(DoubleUtil.doubleKeep2(salaryPerHour*3));
        return bean;
    }

    private void setMultiply(double basicSalary){
        double salaryPerHour=basicSalary/21.75/8;
        activity.setWeekend_salary(DoubleUtil.doubleKeep2ForString(salaryPerHour*2));
        activity.setWeekend_multiply("2.0");
        activity.setFestival_multiply("3.0");
        activity.setFestival_salary(DoubleUtil.doubleKeep2ForString(salaryPerHour*3));
        activity.setNormal_multiply("1.5");
        activity.setNormal_salary(DoubleUtil.doubleKeep2ForString(salaryPerHour*1.5));
    }

    private void clearMultiply(){
        activity.setWeekend_salary("0");
        activity.setWeekend_multiply("0");
        activity.setFestival_multiply("0");
        activity.setFestival_salary("0");
        activity.setNormal_multiply("0");
        activity.setNormal_salary("0");
    }

}
