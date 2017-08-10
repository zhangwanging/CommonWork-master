package com.hzu.jpg.commonwork.enity.moudle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hzu.jpg.commonwork.db.DaoTemplate;
import com.hzu.jpg.commonwork.db.OTRecordSQLiteOpenHelper;
import com.hzu.jpg.commonwork.utils.DoubleUtil;


/**
 * Created by Administrator on 2017/3/9.
 */

public class AddStatisticsItemModel {

    DaoTemplate template;
    OTRecordSQLiteOpenHelper helper;

    public AddStatisticsItemModel(Context context) {
        helper=new OTRecordSQLiteOpenHelper(context);
        template=new DaoTemplate(context);
    }

    public Object getData(String date,String table,Class<?> clazz){
       return template.query(table,"date_ym=?",new String[]{date},clazz);
    }

    public void save(Object bean,String table){

        template.save(bean,table);
    }

    public void update(Object obj,String date,String table){
        template.update(table,"date_ym=?",new String[]{date},obj);
    }

    public void deleteAllowance(String date,double value){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query(false, OverTimeRecordMonthModel.TABLE_PART,new String[]{"salary"},"date_ym=?",new String[]{date},null,null,null,null);
        cursor.moveToNext();
        double salary=cursor.getDouble(cursor.getColumnIndex("salary"));
        salary= DoubleUtil.DoubleSubtract(salary,value);
        ContentValues values=new ContentValues();
        values.put("salary",salary);
        db.update(OverTimeRecordMonthModel.TABLE_PART,values,"date_ym=?",new String[]{date});
        cursor.close();
        db.close();
    }
    public void deleteCut(String date,double value){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query(false, OverTimeRecordMonthModel.TABLE_PART,new String[]{"salary"},"date_ym=?",new String[]{date},null,null,null,null);
        cursor.moveToNext();
        double salary=cursor.getDouble(cursor.getColumnIndex("salary"));
        salary+=value;
        ContentValues values=new ContentValues();
        values.put("salary",salary);
        db.update(OverTimeRecordMonthModel.TABLE_PART,values,"date_ym=?",new String[]{date});
        cursor.close();
        db.close();
    }


}
