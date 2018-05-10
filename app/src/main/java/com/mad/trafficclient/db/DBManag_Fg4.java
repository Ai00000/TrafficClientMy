package com.mad.trafficclient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mad.trafficclient.model.CarPeccancyInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class DBManag_Fg4 {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManag_Fg4(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insert(CarPeccancyInfo info) {
        ContentValues values = new ContentValues();
        values.put("carnumber", info.getCarnumber());
        values.put("times", info.getTimes());
        values.put("pmoney", info.getPmoney());
        values.put("pscore", info.getPscore());
        return db.insert("carpeccancy_info", null, values);
    }

    public List<CarPeccancyInfo> query() {
        List<CarPeccancyInfo> lists = new ArrayList<CarPeccancyInfo>();
        Cursor cursor = db.query("carpeccancy_info", null, null, null, null, null, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                CarPeccancyInfo info = new CarPeccancyInfo(cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4));
                lists.add(info);
            } while (cursor.moveToNext());
            return lists;
        }
        return lists;
    }

    public int find(String carnumber) {
        Cursor cursor = db.query("carpeccancy_info", null, "carnumber=?", new String[]{carnumber}, null, null, null);
        if (cursor.getCount() != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public long delete(String carnumber) {
        return db.delete("carpeccancy_info", "carnumber=?", new String[]{carnumber});
    }

    public void closeDB(){
        if (db!=null){
            db.close();
        }
    }
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "traffic.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists carpeccancy_info(_id integer primary key,carnumber text not null," +
                    "times text not null,pmoney text not null,pscore text not null);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
