package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hzu.jpg.commonwork.db.DaoTemplate;
import com.hzu.jpg.commonwork.db.OTRecordSQLiteOpenHelper;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;


/**
 * Created by Administrator on 2017/3/8.
 */

public class StatisticsModel {

    DaoTemplate template;
    SQLiteOpenHelper helper;
    public final static String TABLE_ALLOWANCE="ot_record_month_allowance";
    public final static String TABLE_CUT="ot_record_month_cut";
    public final static String TABLE_HELP="ot_record_month_help_payment";

    public StatisticsModel(Context context){
        template=new DaoTemplate(context);
        helper=new OTRecordSQLiteOpenHelper(context);
    }

    public OverTimeRecordMonthBean getBasicData(String date){
        return (OverTimeRecordMonthBean) template.query(OverTimeRecordMonthModel.TABLE_PART,"date_ym=?",new String[]{date}, OverTimeRecordMonthBean.class);
    }

    public Object getData(String date,String table,Class<?> clazz){
        return template.query(table,"date_ym=?",new String[]{date}, clazz);
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

}
