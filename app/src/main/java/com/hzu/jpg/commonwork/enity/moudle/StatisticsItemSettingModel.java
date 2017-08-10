package com.hzu.jpg.commonwork.enity.moudle;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hzu.jpg.commonwork.db.DaoTemplate;
import com.hzu.jpg.commonwork.db.OTRecordSQLiteOpenHelper;
import com.hzu.jpg.commonwork.enity.Bean.HelpPaymentBean;
import com.hzu.jpg.commonwork.utils.DoubleUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by Administrator on 2017/3/10.
 */

public class StatisticsItemSettingModel {

    SQLiteOpenHelper helper;
    Context context;
    DaoTemplate template;

    public StatisticsItemSettingModel(Context context) {
        helper = new OTRecordSQLiteOpenHelper(context);
        this.context = context;
        template = new DaoTemplate(context);
    }

    public void update(String column, String date, double money, String table,Class clazz) {
        SQLiteDatabase db = helper.getWritableDatabase();
        if(template.query(table,"date_ym=?",new String[]{date},clazz)==null){
            ContentValues values = new ContentValues();
            values.put(column, money);
            values.put("date_ym",date);
            db.insert(table,null,values);
        }else{
            ContentValues values = new ContentValues();
            values.put(column, money);
            db.update(table, values, "date_ym=?", new String[]{date});}

        db.close();
    }
    public void updateSalary(String date,double oldValue, double newValue,String table,String column){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(false, OverTimeRecordMonthModel.TABLE_PART, new String[]{"salary"}, "date_ym=?", new String[]{date}, null, null, null, null);
        double salary = 0;
        if (!cursor.moveToNext()) {
            SharedPreferences sp=context.getSharedPreferences("setting",Context.MODE_PRIVATE);
            salary = OverTimeRecordMonthModel.createMonthItem(date,template,helper).getSalary();
        } else {
             salary=Double.parseDouble(cursor.getString(cursor.getColumnIndex("salary")));
        }
        double result = calculate(salary, DoubleUtil.DoubleSubtract(newValue,oldValue),table);
        ContentValues values = new ContentValues();
        values.put("salary", result);
        db.update(OverTimeRecordMonthModel.TABLE_PART, values, "date_ym=?", new String[]{date});
        updateItemTotal(table, oldValue, newValue, column, date);
        cursor.close();;
        db.close();
    }
    public double calculate(double salary,double value,String table){
        if(table==StatisticsModel.TABLE_ALLOWANCE){
            return DoubleUtil.doubleAdd(salary,value);
        }else if(table == StatisticsModel.TABLE_CUT){
            return DoubleUtil.DoubleSubtract(salary, value) ;
        }
        return salary;
    }

    public void updateSalaryHelpPayment(String column, double oldValue, double newValue, String date) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(false, OverTimeRecordMonthModel.TABLE_PART, new String[]{"salary"}, "date_ym=?", new String[]{date}, null, null, null, null);
        double salary = 0;
        if (!cursor.moveToNext()) {
            SharedPreferences sp=context.getSharedPreferences("setting",Context.MODE_PRIVATE);
            salary = OverTimeRecordMonthModel.createMonthItem(date,template,helper).getSalary();
        } else {
            salary = Double.parseDouble(cursor.getString(cursor.getColumnIndex("salary")));
        }
        double result = DoubleUtil.doubleAdd(salary,DoubleUtil.DoubleSubtract(oldValue , newValue));
        ContentValues values = new ContentValues();
        values.put("salary", result);
        db.update(OverTimeRecordMonthModel.TABLE_PART, values, "date_ym=?", new String[]{date});

        cursor = db.query(false, StatisticsModel.TABLE_HELP, new String[]{"help_payment_total"}, "date_ym=?", new String[]{date}, null, null, null, null);
        if (!cursor.moveToNext()) {
            HelpPaymentBean bean = new HelpPaymentBean();
            Class<?> clazz = bean.getClass();
            String methodName = "set" + Character.toUpperCase(column.charAt(0)) + column.substring(1);
            try {
                Method method = clazz.getDeclaredMethod(methodName, Double.TYPE);
                method.invoke(bean, newValue);
                bean.setDate_ym(date);
                bean.setHelp_payment_total(newValue);
                template.save(bean, StatisticsModel.TABLE_HELP);
                return;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
                db.close();
            }
        } else {
            cursor.close();
            db.close();
            update(column,date,newValue,StatisticsModel.TABLE_HELP,HelpPaymentBean.class);
            updateItemTotal(StatisticsModel.TABLE_HELP, oldValue, newValue, "help_payment_total", date);

        }
    }

    public void updateItemTotal(String table, double oldValue, double newValue, String column, String date) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(false, table, new String[]{column}, "date_ym=?", new String[]{date}, null, null, null, null);
        if(cursor.moveToNext()) {
            String total=cursor.getString(cursor.getColumnIndex(column));
            double cut_total=0.0;
            if(total!=null){
                cut_total = Double.parseDouble(total);
            }
            double result = DoubleUtil.doubleAdd(DoubleUtil.DoubleSubtract(cut_total, oldValue) , newValue);
            ContentValues values = new ContentValues();
            values.put(column, result);
            db = helper.getWritableDatabase();
            db.update(table, values, "date_ym=?", new String[]{date});
        }
        cursor.close();
        db.close();
    }
}
