package com.mad.trafficclient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mad.trafficclient.httppost.HttpRequest;
import com.mad.trafficclient.model.RechargeLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class RechargeLogMan {
    public SQLiteDatabase db;

    public RechargeLogMan(Context context) {
        db = new Helper(context).getWritableDatabase();
    }

    public void add(RechargeLog log) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("loginName", log.getLoginName());
        contentValues.put("userName", log.getUserName());
        contentValues.put("number", log.getNumber());
        contentValues.put("money", log.getMoney());
        contentValues.put("balance", log.getBalance());
        contentValues.put("date", log.getDate());
        db.insert("log", null, contentValues);
    }

    public List<RechargeLog> query() {

        List<RechargeLog> data = new ArrayList<RechargeLog>();
        Cursor c = db.query("log", null, "loginName=?", new String[]{HttpRequest.getUserName()}, null, null, "date DESC");
        if (!c.moveToFirst()) {
            return data;
        }

        do {
            RechargeLog log = new RechargeLog(
                    c.getString(c.getColumnIndex("loginName")),
                    c.getString(c.getColumnIndex("userName")),
                    c.getInt(c.getColumnIndex("money")),
                    c.getInt(c.getColumnIndex("balance")),
                    c.getLong(c.getColumnIndex("date")),
                    c.getInt(c.getColumnIndex("number"))
            );

            data.add(log);
            c.moveToNext();
        } while (!c.isAfterLast());
        return data;
    }

    public static class Helper extends SQLiteOpenHelper {


        public Helper(Context context) {
            super(context, "relog.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table log(loginName text, userName text,number integer,money integer,balance integer,date long)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
