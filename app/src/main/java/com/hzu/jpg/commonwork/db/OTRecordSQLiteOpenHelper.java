package com.hzu.jpg.commonwork.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/3/5.
 */

public class OTRecordSQLiteOpenHelper extends SQLiteOpenHelper {
    public OTRecordSQLiteOpenHelper(Context context) {
        super(context, "ot_record.db", null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table ot_record_month(\n" +
                "date_ym varchar(20) primary key,\n" +
                "salary double,\n" +
                "ot_salary double,\n" +
                "ot_hours double,\n" +
                "hour_work boolean,\n" +
                "basic_salary double\n"+
                ");");
        db.execSQL("create table ot_record(\n" +
                "date_ymd varchar(20) primary key,\n" +
                "week varchar(20),\n" +
                "basic_hours integer,\n" +
                "basic_minutes integer,\n" +
                "ot_salary double,\n" +
                "ot_hours integer,\n" +
                "ot_minutes integer,\n" +
                "ot_salary_multiple double,\n" +
                "ot_salary_per_hour double,\n" +
                "work_type varchar(20),\n" +
                "remark varchar(200)\n" +
                ");");
        db.execSQL("create table ot_record_month_allowance(\n" +
                "date_ym varchar(20) primary key,\n" +
                "attendance_bonus double,\n" +
                "position double,\n" +
                "board_wages double,\n" +
                "live double,\n" +
                "high_temperature double,\n" +
                "level double,\n" +
                "environment double,\n" +
                "traffic double,\n" +
                "performance double,\n" +
                "other double,\n" +
                "allowance_total double,\n"+
                "foreign key(date_ym) references ot_record_month(date_ym)\n" +
                ");");
        db.execSQL("create table ot_record_month_cut(\n" +
                "date_ym varchar(20) primary key,\n" +
                "event_leave double,\n" +
                "ill_leave double,\n" +
                "canteen double,\n" +
                "water_electricity double,\n" +
                "dormitory double, \n" +
                "cut_total double, \n"+
                "foreign key(date_ym) references ot_record_month(date_ym)\n" +
                ");");
        db.execSQL("create table ot_record_month_help_payment(\n" +
                "date_ym varchar(20) primary key,\n" +
                "social_security double,\n" +
                "accumulation_fund double,\n" +
                "income_tax double,\n" +
                "help_payment_total double,\n"+
                "foreign key(date_ym) references ot_record_month(date_ym)\n" +
                ");");

        db.execSQL("create table ot_salary_setting(\n" +
                "date_ym varchar(20) primary key,\n" +
                "basic_salary double,\n" +
                "salary_per_hour double,\n" +
                "normal_multiply double,\n" +
                "normal_salary double,\n" +
                "weekend_multiply double,\n" +
                "weekend_salary double,\n" +
                "festival_multiply double,\n" +
                "festival_salary double,\n" +
                "foreign key(date_ym) references ot_record_month(date_ym)\n" +
                ");");
        db.execSQL("create table hour_work_record(\n" +
                "date_ymd varchar(20) primary key,\n" +
                "hours integer,\n" +
                "minutes integer,\n" +
                "salary double,\n" +
                "remark varchar(200),\n" +
                "week varchar(20)\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
