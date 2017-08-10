package com.hzu.jpg.commonwork.utils.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hzu.jpg.commonwork.enity.SearchHistoryVo;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    public SQLiteDatabase getDb() {
        return db;
    }

    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    // 添加搜索记录
    public synchronized void addSearchResult(SearchHistoryVo result) {
        db.beginTransaction(); // 开始事务
        try {
            db.execSQL("REPLACE INTO history(content,searchtime)VALUES(?,?)", new Object[]{result.content,
                    result.searchtime});
            db.setTransactionSuccessful(); // 设置事务成功完成
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction(); // 结束事务
        }
    }

    // 删除一条搜索记录
    public synchronized void deleteSearchResultItem(SearchHistoryVo result) {
        db.beginTransaction(); // 开始事务
        try {
            db.execSQL("DELETE FROM history WHERE content = '" + result.getContent() + "'");
            db.setTransactionSuccessful(); // 设置事务成功完成
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction(); // 结束事务
        }
    }

    // 查询搜索记录
    public List<SearchHistoryVo> querySearchHistory() {
        List<SearchHistoryVo> list = new ArrayList<SearchHistoryVo>();
        Cursor cursor = db.rawQuery("select * from history", null);
        while (cursor.moveToNext()) {
            SearchHistoryVo item = new SearchHistoryVo();
            item.content = cursor.getString(cursor.getColumnIndex("content"));
            item.searchtime = cursor.getString(cursor.getColumnIndex("searchtime"));
            list.add(item);
        }
        cursor.close();
        return list;
    }

    // 删除history表
    public synchronized void deleteSearchHistory() {
        db.beginTransaction();
        db.execSQL("DELETE FROM history");
        System.out.println("DELETE");
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
