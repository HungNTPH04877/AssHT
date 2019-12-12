package com.example.assht1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.assht1.Model.KhoanChi;
import com.example.assht1.database.Database;

import java.util.ArrayList;
import java.util.List;

public class KhoanChiDAO {
    private SQLiteDatabase sqliteDB;
    private Database db;
    private Context context;

    public KhoanChiDAO(Context context) {
        this.context = context;
        db = new Database(context);
        sqliteDB = db.getWritableDatabase();
    }
    public int addDataKC(KhoanChi kc) {
        SQLiteDatabase sqliteDB = db.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("maKC", kc.getTenKC());
        value.put("tenKC", kc.getMaKC());
        value.put("theLoai", kc.getTheLoai());
        value.put("ngay", kc.getNgay());
        if (sqliteDB.insert("KhoangChi", null, value) == -1) {
            return -1;
        }
        return 1;
    }

    public List<KhoanChi> getDataKC() {
        List<KhoanChi> list = new ArrayList<>();
        SQLiteDatabase sqliteDB = db.getWritableDatabase();
        Cursor c = sqliteDB.query("KhoangChi", null, null, null, null, null, null);
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
}
