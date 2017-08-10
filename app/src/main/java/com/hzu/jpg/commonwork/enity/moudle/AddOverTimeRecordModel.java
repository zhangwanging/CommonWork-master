package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hzu.jpg.commonwork.Presenter.OverTimeSalarySettingPresenter;
import com.hzu.jpg.commonwork.db.DaoTemplate;
import com.hzu.jpg.commonwork.db.OTRecordSQLiteOpenHelper;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordBean;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeSalarySettingBean;
import com.hzu.jpg.commonwork.enity.Bean.SettingBean;
import com.hzu.jpg.commonwork.utils.DoubleUtil;
import com.hzu.jpg.commonwork.utils.TimeUtil;


/**
 * Created by Administrator on 2017/3/6.
 */

public class AddOverTimeRecordModel {

    DaoTemplate template;
    public final static String TABLE="ot_record";
    SQLiteOpenHelper helper;

    public AddOverTimeRecordModel(Context context){
        helper=new OTRecordSQLiteOpenHelper(context);
        template=new DaoTemplate(context);
    }

    public OverTimeRecordBean getRecord(String date){
        return (OverTimeRecordBean) template.query(TABLE,"date_ymd=?",new String[]{date},OverTimeRecordBean.class);
    }

    public void save(OverTimeRecordBean bean){
        template.save(bean,TABLE);
        updateMonthSalary(bean.getDate_ymd());
    }

    public void delete(String date){
        template.delete(TABLE,"date_ymd=?",new String[]{date});
        updateMonthSalary(date);
    }

    public void update(OverTimeRecordBean bean,String date){
        template.update(TABLE,"date_ymd=?",new String[]{date},bean);
        updateMonthSalary(date);
    }

    private void updateMonthSalary(String date) {
        String date_ym=date.substring(0,date.length()-3);
        Log.d("date",date_ym);
        SQLiteDatabase db = helper.getReadableDatabase();
        OverTimeRecordMonthBean monthBean = (OverTimeRecordMonthBean)
                template.query(OverTimeRecordMonthModel.TABLE_PART, "date_ym=?", new String[]{date_ym}, OverTimeRecordMonthBean.class);
        double basicSalary ;
        double hours=0;
        double TotalOtSalary=0;
        if (monthBean == null) {
            monthBean = OverTimeRecordMonthModel.createMonthItem(date_ym, template,helper);
            basicSalary=monthBean.getBasic_salary();
        }else if(monthBean.getHour_work()==0) {
            basicSalary=DoubleUtil.DoubleSubtract(monthBean.getSalary(),monthBean.getOt_salary());
            basicSalary= DoubleUtil.doubleAdd(basicSalary,getSetting(date_ym).getBasic_salary());
        } else {
            basicSalary = DoubleUtil.DoubleSubtract(monthBean.getSalary(), monthBean.getOt_salary());
        }
        Cursor cursor=db.query(false,AddOverTimeRecordModel.TABLE,new String[]{"ot_hours","ot_minutes","ot_salary"},
                "date_ymd like ?",new String[]{date_ym+"%"},null,null,null,null);
        while(cursor.moveToNext()){
            double otSalary=cursor.getDouble(cursor.getColumnIndex("ot_salary"));
            TotalOtSalary+=otSalary;
            basicSalary+=otSalary;
            hours+= TimeUtil.Time2Double(cursor.getInt(cursor.getColumnIndex("ot_hours")),cursor.getInt(cursor.getColumnIndex("ot_minutes")));
        }
        monthBean.setOt_salary(DoubleUtil.doubleKeep2(TotalOtSalary));
        monthBean.setOt_hours(DoubleUtil.doubleKeep2(hours));
        monthBean.setSalary(DoubleUtil.doubleKeep2(basicSalary));
        monthBean.setDate_ym(date_ym);
        template.update(OverTimeRecordMonthModel.TABLE_PART,"date_ym=?",new String[]{date_ym},monthBean);
    }

    public OverTimeSalarySettingBean getSetting(String date){
        OverTimeSalarySettingBean bean= (OverTimeSalarySettingBean) template.query(OverTimeSalarySettingModel.TABLE_SETTING,"date_ym=?",new String[]{date},OverTimeSalarySettingBean.class);
        if(bean==null) bean= OverTimeSalarySettingPresenter.getDefBean(date);
        return bean;
    }


}
