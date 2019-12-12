package com.example.assht1.Model;

public class KhoanChi {
    private int IdKhoanChi;
    private String MaKC;
    private String TenKC;
    private String TheLoai;
    private String Ngay;

    public KhoanChi() {
    }

    public KhoanChi(String maKC, String tenKC, String theLoai, String ngay) {
        MaKC = maKC;
        TenKC = tenKC;
        TheLoai = theLoai;
        Ngay = ngay;
    }

    public int getIdKhoanChi() {
        return IdKhoanChi;
    }

    public void setIdKhoanChi(int idKhoanChi) {
        IdKhoanChi = idKhoanChi;
    }

    public String getMaKC() {
        return MaKC;
    }

    public void setMaKC(String maKC) {
        MaKC = maKC;
    }

    public String getTenKC() {
        return TenKC;
    }

    public void setTenKC(String tenKC) {
        TenKC = tenKC;
    }

    public String getTheLoai() {
        return TheLoai;
    }

    public void setTheLoai(String theLoai) {
        TheLoai = theLoai;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }
}
