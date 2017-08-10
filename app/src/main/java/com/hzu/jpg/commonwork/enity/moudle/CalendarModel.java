package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hzu.jpg.commonwork.HourWork.Model.HourWorkAddRecordModel;
import com.hzu.jpg.commonwork.db.DaoTemplate;
import com.hzu.jpg.commonwork.db.OTRecordSQLiteOpenHelper;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;
import com.hzu.jpg.commonwork.utils.DoubleUtil;
import com.hzu.jpg.commonwork.utils.TimeUtil;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/12.
 */

public class CalendarModel {

    DaoTemplate template;
    SQLiteOpenHelper helper;

    public CalendarModel(Context context){
        helper=new OTRecordSQLiteOpenHelper(context);
        template=new DaoTemplate(context);
    }

    public OverTimeRecordMonthBean getMonthSalary(String date){
        return (OverTimeRecordMonthBean) template.query(OverTimeRecordMonthModel.TABLE_PART,"date_ym=?",new String[]{date},OverTimeRecordMonthBean.class);
    }

    public void getDayBeans(String date,Map<Integer,Double> map){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query(false,AddOverTimeRecordModel.TABLE,new String[]{"date_ymd","ot_hours","ot_minutes"},"date_ymd like ?",new String[]{date+"%"},null,null,null,null);
        while(cursor.moveToNext()){
            String date_ymd=cursor.getString(cursor.getColumnIndex("date_ymd"));
            int hours=cursor.getInt(cursor.getColumnIndex("ot_hours"));
            int minutes=cursor.getInt(cursor.getColumnIndex("ot_minutes"));
            int day=Integer.parseInt(date_ymd.substring(date_ymd.length()-2),date_ymd.length());
            double time= TimeUtil.Time2Double(hours,minutes);
            map.put(day,time);
        }
    }

    public void getHourWorkBean(String date,Map<Integer,Double> map){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query(false, HourWorkAddRecordModel.TABLE,new String[]{"date_ymd","hours","minutes"},"date_ymd like ?",new String[]{date+"%"},null,null,null,null);
        while(cursor.moveToNext()){
            String date_ymd=cursor.getString(cursor.getColumnIndex("date_ymd"));
            int hours=cursor.getInt(cursor.getColumnIndex("hours"));
            int minutes=cursor.getInt(cursor.getColumnIndex("minutes"));
            int day=Integer.parseInt(date_ymd.substring(date_ymd.length()-2),date_ymd.length());
            double time= TimeUtil.Time2Double(hours,minutes);
            map.put(day,time);
        }
    }
}
