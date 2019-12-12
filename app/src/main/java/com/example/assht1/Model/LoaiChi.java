package com.example.assht1.Model;

public class LoaiChi {
    private int IdLoaiChi;
    private String MaLC;
    private String TenLC;

    public LoaiChi() {
    }

    public LoaiChi(String maLC, String tenLC) {
        MaLC = maLC;
        TenLC = tenLC;
    }

    public int getIdLoaiChi() {
        return IdLoaiChi;
    }

    public void setIdLoaiChi(int idLoaiChi) {
        IdLoaiChi = idLoaiChi;
    }

    public String getMaLC() {
        return MaLC;
    }

    public void setMaLC(String maLC) {
        MaLC = maLC;
    }

    public String getTenLC() {
        return TenLC;
    }

    public void setTenLC(String tenLC) {
        TenLC = tenLC;
    }

    @Override
    public String toString() {
        return "LoaiChi{" +
                ", MaLC='" + MaLC + '\'' +
                ", TenLC='" + TenLC + '\'' +
                '}';
    }
}
