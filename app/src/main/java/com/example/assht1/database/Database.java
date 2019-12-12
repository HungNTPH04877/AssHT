package com.example.assht1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assht1.Model.KhoanChi;
import com.example.assht1.Model.LoaiChi;

import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "dbChiTieu";
    private static final String TABLE = "CREATE TABLE LoaiChi(id INTEGER  PRIMARY KEY AUTOINCREMENT, \n" +
            "maLC TEXT,tenLC TEXT)";
    private static final String TABLE2 = "CREATE TABLE KhoangChi(id INTEGER  PRIMARY KEY AUTOINCREMENT, \n" +
            "maKC TEXT,tenKC TEXT,theLoai TEXT,ngay TEXT)";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public Cursor GetData(String sql) {

        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);

    }


    public void QueryData(String sql) {

        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);

    }



    public int addDataLC(LoaiChi lc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("maLC", lc.getMaLC());
        value.put("tenLC", lc.getTenLC());
        if (db.insert("LoaiChi", null, value) == -1) {
            return -1;
        }
        return 1;
    }

    public int addDataKC(KhoanChi kc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("theLoai", kc.getTheLoai());
        value.put("ngay", kc.getNgay());
        value.put("maKC", kc.getMaKC());
        value.put("tenKC", kc.getTenKC());
        if (db.insert("KhoangChi", null, value) == -1) {
            return -1;
        }
        return 1;
    }


    public List<LoaiChi> getDataLC() {
        List<LoaiChi> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query("LoaiChi", null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            LoaiChi lc = new LoaiChi();
            lc.setIdLoaiChi(Integer.parseInt(c.getString(0)));
            lc.setMaLC(c.getString(1));
            lc.setTenLC(c.getString(2));
            list.add(lc);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public List<KhoanChi> getDataKC() {
        List<KhoanChi> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query("KhoangChi", null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            KhoanChi kc = new KhoanChi();
            kc.setIdKhoanChi(Integer.parseInt(c.getString(0)));
            kc.setMaKC(c.getString(1));
            kc.setTenKC(c.getString(2));
            kc.setTheLoai(c.getString(3));
            kc.setNgay(c.getString(4));
            list.add(kc);
            c.moveToNext();
        }
        c.close();
        return list;
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE);
        db.execSQL(TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}