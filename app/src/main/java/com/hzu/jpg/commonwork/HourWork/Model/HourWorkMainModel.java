package com.hzu.jpg.commonwork.HourWork.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hzu.jpg.commonwork.db.DaoTemplate;
import com.hzu.jpg.commonwork.db.OTRecordSQLiteOpenHelper;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;
import com.hzu.jpg.commonwork.enity.moudle.OverTimeRecordMonthModel;
import com.hzu.jpg.commonwork.utils.DoubleUtil;
import com.hzu.jpg.commonwork.utils.TimeUtil;

/**
 * Created by Administrator on 2017/5/11.
 */

public class HourWorkMainModel {

    DaoTemplate template;
    SQLiteOpenHelper helper;

    public HourWorkMainModel(Context context){
        template=new DaoTemplate(context);
        helper=new OTRecordSQLiteOpenHelper(context);
    }

    public OverTimeRecordMonthBean getBean(String date_ym) {
        return (OverTimeRecordMonthBean) template.query(OverTimeRecordMonthModel.TABLE_PART, "date_ym=?", new String[]{date_ym}, OverTimeRecordMonthBean.class);
    }

    public OverTimeRecordMonthBean getDef() {
        return HourWorkAddRecordModel.createMonthItem(TimeUtil.getDateYM(), template);
    }

    public OverTimeRecordMonthBean updateMonth(final String date_ym) {
        SQLiteDatabase db = helper.getReadableDatabase();
        OverTimeRecordMonthBean monthBean = (OverTimeRecordMonthBean)
                template.query(OverTimeRecordMonthModel.TABLE_PART, "date_ym=?", new String[]{date_ym}, OverTimeRecordMonthBean.class);
        double hours = 0;
        double TotalDaySalary = 0;
        double allowance=DoubleUtil.DoubleSubtract(DoubleUtil.DoubleSubtract(monthBean.getSalary(),monthBean.getBasic_salary()),monthBean.getOt_salary());
        Cursor cursor = db.query(HourWorkAddRecordModel.TABLE, null,
                "date_ymd like ?", new String[]{date_ym + "%"}, null, null, null, null);
        while (cursor.moveToNext()) {
            double daySalary = cursor.getDouble(cursor.getColumnIndex("salary"));
            TotalDaySalary += daySalary;
            allowance += daySalary;
            hours += TimeUtil.Time2Double(cursor.getInt(cursor.getColumnIndex("hours")), cursor.getInt(cursor.getColumnIndex("minutes")));
        }
        cursor.close();
        monthBean.setOt_salary(DoubleUtil.doubleKeep2(TotalDaySalary));
        monthBean.setOt_hours(DoubleUtil.doubleKeep2(hours));
        monthBean.setSalary(DoubleUtil.doubleKeep2(allowance));
        monthBean.setDate_ym(date_ym);
        monthBean.setHour_work(0);
        template.update(OverTimeRecordMonthModel.TABLE_PART, "date_ym=?", new String[]{date_ym}, monthBean);
        return monthBean;
    }

}
