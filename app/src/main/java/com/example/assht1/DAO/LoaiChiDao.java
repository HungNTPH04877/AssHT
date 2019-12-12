package com.example.assht1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assht1.Model.LoaiChi;
import com.example.assht1.database.Database;

import java.util.ArrayList;
import java.util.List;

public class LoaiChiDao {
    private SQLiteDatabase sqliteDB;
    private Database db;
    private Context context;

    public LoaiChiDao(Context context) {
        this.context = context;
        db = new Database(context);
        sqliteDB = db.getWritableDatabase();
    }
    public int addDataLC(LoaiChi lc) {
        SQLiteDatabase sqliteDB = db.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("maLC", lc.getMaLC());
        value.put("tenLC", lc.getTenLC());
        if (sqliteDB.insert("LoaiChi", null, value) == -1) {
            return -1;
        }
        return 1;
    }

    public List<LoaiChi> getDataLC() {
        List<LoaiChi> list = new ArrayList<>();
        SQLiteDatabase sqliteDB = db.getWritableDatabase();
        Cursor c = sqliteDB.query("LoaiChi", null, null, null, null, null, null);
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
}

