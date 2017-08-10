package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hzu.jpg.commonwork.db.DaoTemplate;
import com.hzu.jpg.commonwork.db.OTRecordSQLiteOpenHelper;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordBean;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;
import com.hzu.jpg.commonwork.utils.DoubleUtil;
import com.hzu.jpg.commonwork.utils.TimeUtil;

import java.util.List;


/**
 * Created by Administrator on 2017/3/5.
 */

public class OverTimeRecordMonthModel {

    public SQLiteOpenHelper helper;
    public DaoTemplate template;

    public final static String TABLE_PART="ot_record_month";

    public OverTimeRecordMonthModel(Context context){
        helper= new OTRecordSQLiteOpenHelper(context);
        template=new DaoTemplate(context);
    }

    public OverTimeRecordMonthBean getOTRecordMonthSalary(String date){
        return (OverTimeRecordMonthBean) template.query(TABLE_PART,"date_ym=?",new String[]{date}, OverTimeRecordMonthBean.class);
    }

    public static OverTimeRecordMonthBean createMonthItem(String date,DaoTemplate template,SQLiteOpenHelper helper) {
        OverTimeRecordMonthBean bean = new OverTimeRecordMonthBean();
        bean.setDate_ym(date);
        double basicSalary=getBasicSalary(date,helper);
        bean.setSalary(basicSalary);
        bean.setOt_salary(0.0);
        bean.setOt_hours(0);
        bean.setBasic_salary(basicSalary);
        bean.setHour_work(1);
        template.save(bean, OverTimeRecordMonthModel.TABLE_PART);
        return bean;
    }

    public void getRvData(String date, List<OverTimeRecordBean> list, int count){
        template.query(AddOverTimeRecordModel.TABLE,"date_ymd like ?",new String[]{date+"%"},OverTimeRecordBean.class,Integer.toString(count),list);
    }

    public static double getBasicSalary(String date,SQLiteOpenHelper helper){
       SQLiteDatabase db= helper.getReadableDatabase();
        Cursor cursor=db.query(false,OverTimeSalarySettingModel.TABLE_SETTING,new String[]{"basic_salary"},"date_ym=?",new String[]{date},null,null,null,null);
        if(cursor.moveToNext()){
            return cursor.getDouble(cursor.getColumnIndex("basic_salary"));
        }
        cursor.close();
        return 0;
    }
    public double getBasicSalary(String date){
        SQLiteDatabase db= helper.getReadableDatabase();
        Cursor cursor=db.query(false,OverTimeSalarySettingModel.TABLE_SETTING,new String[]{"basic_salary"},"date_ym=?",new String[]{date},null,null,null,null);
        if(cursor.moveToNext()){
            return cursor.getDouble(cursor.getColumnIndex("basic_salary"));
        }
        cursor.close();
        return 2000;
    }
    public void updateMonthBean(OverTimeRecordMonthBean monthBean){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query(false,AddOverTimeRecordModel.TABLE,new String[]{"ot_hours","ot_minutes","ot_salary"},
                "date_ymd like ?",new String[]{monthBean.getDate_ym()+"%"},null,null,null,null);
        double basicSalary=DoubleUtil.DoubleSubtract(monthBean.getSalary(),monthBean.getOt_salary());
        basicSalary=DoubleUtil.doubleAdd(basicSalary,getBasicSalary(monthBean.getDate_ym()));
        double TotalOtSalary=0;
        double hours=0;
        while(cursor.moveToNext()){
            double otSalary=cursor.getDouble(cursor.getColumnIndex("ot_salary"));
            TotalOtSalary+=otSalary;
            basicSalary+=otSalary;
            hours+= TimeUtil.Time2Double(cursor.getInt(cursor.getColumnIndex("ot_hours")),cursor.getInt(cursor.getColumnIndex("ot_minutes")));
        }
        cursor.close();
        monthBean.setOt_salary(DoubleUtil.doubleKeep2(TotalOtSalary));
        monthBean.setOt_hours(DoubleUtil.doubleKeep2(hours));
        monthBean.setSalary(DoubleUtil.doubleKeep2(basicSalary));
        monthBean.setBasic_salary(getBasicSalary(monthBean.getDate_ym()));
        monthBean.setHour_work(1);
        template.update(OverTimeRecordMonthModel.TABLE_PART,"date_ym=?",new String[]{monthBean.getDate_ym()},monthBean);

    }
}
