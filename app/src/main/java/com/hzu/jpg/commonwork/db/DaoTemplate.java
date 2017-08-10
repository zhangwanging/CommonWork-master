package com.hzu.jpg.commonwork.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/5.
 */

public class DaoTemplate {

    private OTRecordSQLiteOpenHelper helper;

    public DaoTemplate(Context context) {
        helper = new OTRecordSQLiteOpenHelper(context);
    }

    public void save(Object obj, String table) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            if ("serialVersionUID".equalsIgnoreCase(fields[i].getName()) || "$change".equalsIgnoreCase(fields[i].getName())) {
                continue;
            }
            try {
                Method m = clazz.getDeclaredMethod(methodName);
                Class type = m.getReturnType();
                if (type.getName().equals("java.lang.String")) {
                    String s = (String) m.invoke(obj);
                    values.put(fieldName, s);
                    Log.d("save", fieldName + "-" + s);
                } else if (type.getName().equals("int")) {
                    int j = (int) m.invoke(obj);
                    values.put(fieldName, j);
                    Log.d("save", fieldName + "-" + Double.toString(j));
                } else if (type.getName().equals("float")) {
                    float f = (float) m.invoke(obj);
                    values.put(fields[i].getName(), f);
                    Log.d("save", fieldName + "-" + Double.toString(f));
                } else if (type.getName().equals("double")) {
                    double d = (Double) m.invoke(obj);
                    values.put(fieldName, d);
                    Log.d("save", fieldName + "-" + Double.toString(d));
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            db.beginTransaction();
            //db.insert(table, null, values);
            db.replace(table, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public Object query(String table, String selection, String[] selectionArgs, Class<? extends Object> clazz) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Field[] fields = clazz.getDeclaredFields();
        String[] columns = new String[fields.length];
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            columns[i] = fields[i].getName();
        }
        Cursor cursor = db.query(false, table, null, selection, selectionArgs, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                Object obj = clazz.newInstance();
                for (int i = 0; i < fields.length; i++) {
                    String methodName = "set" + Character.toUpperCase(fields[i].getName().charAt(0)) + fields[i].getName().substring(1);
                    if ("serialVersionUID".equalsIgnoreCase(fields[i].getName()) || "$change".equalsIgnoreCase(fields[i].getName())) {
                        continue;
                    }
                    Method method = clazz.getDeclaredMethod(methodName, fields[i].getType());
                    if (fields[i].getType().getName().equals("java.lang.String")) {
                        String s = cursor.getString(cursor.getColumnIndex(fields[i].getName()));
                        method.invoke(obj, s);
                    } else if (fields[i].getType().getName().equals("int")) {
                        int j = cursor.getInt(cursor.getColumnIndex(fields[i].getName()));
                        method.invoke(obj, j);
                    } else if (fields[i].getType().getName().equals("float")) {
                        float f = cursor.getFloat(cursor.getColumnIndex(fields[i].getName()));
                        method.invoke(obj, f);
                    } else if (fields[i].getType().getName().equals("double")) {
                        double d = cursor.getDouble(cursor.getColumnIndex(fields[i].getName()));
                        method.invoke(obj, d);
                    }
                }
                list.add(obj);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return list.size() > 0 ? list.get(0) : null;
    }

    public void query(String table, String selection, String[] selectionArgs, Class<? extends Object> clazz, String count, List list) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Field[] fields = clazz.getDeclaredFields();
        String[] columns = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columns[i] = fields[i].getName();
        }
        Cursor cursor = db.query(false, table, null, selection, selectionArgs, null, null, null, count);
        try {
            while (cursor.moveToNext()) {
                Object obj = clazz.newInstance();
                for (int i = 0; i < fields.length; i++) {
                    String methodName = "set" + Character.toUpperCase(fields[i].getName().charAt(0)) + fields[i].getName().substring(1);
                    if ("serialVersionUID".equalsIgnoreCase(fields[i].getName()) || "$change".equalsIgnoreCase(fields[i].getName())) {
                        continue;
                    }
                    Method method = clazz.getDeclaredMethod(methodName, fields[i].getType());
                    if (fields[i].getType().getName().equals("java.lang.String")) {
                        String s = cursor.getString(cursor.getColumnIndex(fields[i].getName()));
                        method.invoke(obj, s);
                    } else if (fields[i].getType().getName().equals("int")) {
                        int j = cursor.getInt(cursor.getColumnIndex(fields[i].getName()));
                        method.invoke(obj, j);
                    } else if (fields[i].getType().getName().equals("float")) {
                        float f = cursor.getFloat(cursor.getColumnIndex(fields[i].getName()));
                        method.invoke(obj, f);
                    } else if (fields[i].getType().getName().equals("double")) {
                        double d = cursor.getDouble(cursor.getColumnIndex(fields[i].getName()));
                        method.invoke(obj, d);
                    }
                }
                list.add(obj);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public void delete(String table, String where, String[] whereArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(table, where, whereArgs);
        db.close();
    }

    public void update(String table, String where, String[] whereArgs, Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            if ("serialVersionUID".equalsIgnoreCase(fields[i].getName()) || "$change".equalsIgnoreCase(fields[i].getName())) {
                continue;
            }
            try {
                Method m = clazz.getDeclaredMethod(methodName);
                Class type = m.getReturnType();
                if (type.getName().equals("java.lang.String")) {
                    String s = (String) m.invoke(obj);
                    values.put(fields[i].getName(), s);
                } else if (type.getName().equals("int")) {
                    int j = (int) m.invoke(obj);
                    values.put(fields[i].getName(), j);
                } else if (type.getName().equals("float")) {
                    float f = (float) m.invoke(obj);
                    values.put(fields[i].getName(), f);
                } else if (type.getName().equals("double")) {
                    double d = (Double) m.invoke(obj);
                    values.put(fields[i].getName(), d);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            db.beginTransaction();
            db.update(table, values, where, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
