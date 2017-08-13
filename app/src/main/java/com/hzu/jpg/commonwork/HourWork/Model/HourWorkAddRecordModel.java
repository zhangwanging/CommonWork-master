package com.hzu.jpg.commonwork.HourWork.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hzu.jpg.commonwork.HourWork.Bean.HourWorkRecordBean;
import com.hzu.jpg.commonwork.db.DaoTemplate;
import com.hzu.jpg.commonwork.db.OTRecordSQLiteOpenHelper;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;
import com.hzu.jpg.commonwork.enity.moudle.OverTimeRecordMonthModel;
import com.hzu.jpg.commonwork.utils.DoubleUtil;
import com.hzu.jpg.commonwork.utils.SharedPreferencesUtil;
import com.hzu.jpg.commonwork.utils.TimeUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class HourWorkAddRecordModel {

    private DaoTemplate template;
    public static final String TABLE="hour_work_record";
    SQLiteOpenHelper helper;

    public HourWorkAddRecordModel(Context context){
        template=new DaoTemplate(context);
        helper=new OTRecordSQLiteOpenHelper(context);
    }

    public double getSetting(){
        return Double.parseDouble(SharedPreferencesUtil.getSalarySetting());
    }
    public HourWorkRecordBean getRecord(String date_ymd){
        return (HourWorkRecordBean) template.query(TABLE,"date_ymd=?",new String[]{date_ymd},HourWorkRecordBean.class);
    }

    public void getRvData(String date, List<HourWorkRecordBean> list, int count){
        template.query(TABLE,"date_ymd like ?",new String[]{date+"%"},HourWorkRecordBean.class,Integer.toString(count),list);
    }

    public void save(HourWorkRecordBean bean){
        template.save(bean,TABLE);
        updateMonth(bean.getDate_ymd());
    }

    public void delete(String date_ymd){
        template.delete(TABLE,"date_ymd=?",new String[]{date_ymd});
        updateMonth(date_ymd);
    }

    public void updateMonth(String date){
        String date_ym=date.substring(0,date.length()-3);
        Log.d("date",date_ym);
        SQLiteDatabase db = helper.getReadableDatabase();
        OverTimeRecordMonthBean monthBean = (OverTimeRecordMonthBean)
                template.query(OverTimeRecordMonthModel.TABLE_PART, "date_ym=?", new String[]{date_ym}, OverTimeRecordMonthBean.class);
        double basicSalary ;
        double hours=0;
        double TotalDaySalary=0;
        if (monthBean == null) {
            monthBean = createMonthItem(date_ym,template);
            basicSalary=monthBean.getBasic_salary();
        } else if(monthBean.getHour_work()==1){
            basicSalary=DoubleUtil.DoubleSubtract(DoubleUtil.DoubleSubtract(monthBean.getSalary(),monthBean.getOt_salary()),monthBean.getBasic_salary());
        } else {
            basicSalary = DoubleUtil.DoubleSubtract(monthBean.getSalary(), monthBean.getOt_salary());
        }
        Cursor cursor=db.query(TABLE,null,
                "date_ymd like ?",new String[]{date_ym+"%"},null,null,null,null);
        while(cursor.moveToNext()){
            double daySalary=cursor.getDouble(cursor.getColumnIndex("salary"));
            TotalDaySalary+=daySalary;
            basicSalary+=daySalary;
            hours+= TimeUtil.Time2Double(cursor.getInt(cursor.getColumnIndex("hours")),cursor.getInt(cursor.getColumnIndex("minutes")));
        }
        monthBean.setOt_salary(DoubleUtil.doubleKeep2(TotalDaySalary));
        monthBean.setOt_hours(DoubleUtil.doubleKeep2(hours));
        monthBean.setSalary(DoubleUtil.doubleKeep2(basicSalary));
        monthBean.setDate_ym(date_ym);
        monthBean.setHour_work(0);
        template.update(OverTimeRecordMonthModel.TABLE_PART,"date_ym=?",new String[]{date_ym},monthBean);
        cursor.close();
    }

    public static OverTimeRecordMonthBean createMonthItem(String date,DaoTemplate template) {
        OverTimeRecordMonthBean bean = new OverTimeRecordMonthBean();
        bean.setDate_ym(date);
        bean.setSalary(0);
        bean.setOt_salary(0.0);
        bean.setOt_hours(0);
        bean.setBasic_salary(0);
        bean.setHour_work(0);
        template.save(bean, OverTimeRecordMonthModel.TABLE_PART);
        return bean;
    }




}
