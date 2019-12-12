package com.example.assht1.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assht1.Model.KhoanChi;
import com.example.assht1.R;
import com.example.assht1.Tag_Fragment.tab_KhoanChi_Fragment;
import com.example.assht1.database.Database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class NoiDungKhoangChi_Adapter extends BaseAdapter {

    public static List<KhoanChi> khoangChiList;
    private tab_KhoanChi_Fragment mCcontext;
    private int layout;
    ListView lv;
    Database db;
    EditText txtNhapNgay;

    public NoiDungKhoangChi_Adapter(List<KhoanChi> KhoangChiList, tab_KhoanChi_Fragment mCcontext, int layout, ListView lv) {
        this.mCcontext = mCcontext;
        this.layout = layout;
        this.khoangChiList = KhoangChiList;
        this.lv = lv;

    }

    @Override
    public int getCount() {
        return khoangChiList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder {
        Button btnEdit, btnDelete;
        TextView maKC,tenKC, Theloai, Ngay;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        db = new Database(mCcontext.getContext());
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mCcontext.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.maKC = (TextView) view.findViewById(R.id.tvMaKC);
            holder.tenKC = (TextView) view.findViewById(R.id.tvTenKC);
            holder.Ngay = (TextView) view.findViewById(R.id.tvNgay);
            holder.Theloai = (TextView) view.findViewById(R.id.tvTheLoai);
            holder.btnDelete = (Button) view.findViewById(R.id.btnDeleteKhoanChi);
            holder.btnEdit = (Button) view.findViewById(R.id.btnEditKhoanChi);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final KhoanChi khoanChi = khoangChiList.get(i);
        holder.maKC.setText(khoanChi.getMaKC());
        holder.tenKC.setText("" + (khoanChi.getTenKC()));
        holder.Theloai.setText(khoanChi.getTheLoai());
        holder.Ngay.setText(khoanChi.getNgay());


//        Bat Su kien Delete
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final KhoanChi kc = (KhoanChi) khoangChiList.get(i);
//                Toast.makeText(mCcontext.getActivity(), "" + nd.getNoiDung() + nd.getIdNoiDung(), Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder dialogXoa = new AlertDialog.Builder(mCcontext.getActivity());
                dialogXoa.setMessage("Do you want delete this?");
                dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.QueryData("DELETE FROM KhoangChi WHERE id = " + kc.getIdKhoanChi() + " ");
                        Toast.makeText(mCcontext.getActivity(), "Đã Xoá", Toast.LENGTH_SHORT).show();
                        mCcontext.getDataList(lv);
                    }
                });
                dialogXoa.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialogXoa.show();
            }
        });
        // bat su kien edit
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(mCcontext.getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_update);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                final EditText txtMaKC = dialog.findViewById(R.id.editMaKC);
                final EditText txtTenKC = dialog.findViewById(R.id.editTenKC);
                final EditText txtTheLoai = dialog.findViewById(R.id.editTheLoai);
                txtNhapNgay = dialog.findViewById(R.id.editNgay);
                Button btnGetDate = dialog.findViewById(R.id.btnDate);
                btnGetDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtNhapNgay = dialog.findViewById(R.id.editNgay);
                        final Calendar cl = Calendar.getInstance();
                        int day = cl.get(Calendar.DATE);
                        int month = cl.get(Calendar.MONTH);
                        int year = cl.get(Calendar.YEAR);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(mCcontext.getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                cl.set(i, i1, i2);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                txtNhapNgay.setText(simpleDateFormat.format(cl.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }

                });
                txtMaKC.setText(khoanChi.getMaKC());
                txtTenKC.setText(String.valueOf(khoanChi.getTenKC()));
                txtTheLoai.setText(khoanChi.getTheLoai());
                txtNhapNgay.setText(khoanChi.getNgay());
                Button btnHuy = dialog.findViewById(R.id.btnHuyUpdate);
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String makhoanchiNew = txtMaKC.getText().toString();
                        String tenkhoanchiNew = txtTenKC.getText().toString();
                        String TheLoaiNew = txtTheLoai.getText().toString();
                        String NgayNew = txtNhapNgay.getText().toString();
                        db.QueryData("UPDATE KhoangChi SET maKC= '" + makhoanchiNew + "',tenKC= '" + tenkhoanchiNew + "' WHERE Id = '" + khoanChi.getIdKhoanChi() + "' ");
                        db.QueryData("UPDATE KhoangChi SET tenKC= '" + tenkhoanchiNew + "' WHERE Id = '" + khoanChi.getIdKhoanChi() + "' ");
                        db.QueryData("UPDATE KhoangChi SET theLoai= '" + TheLoaiNew + "' WHERE Id = '" + khoanChi.getIdKhoanChi() + "' ");
                        db.QueryData("UPDATE KhoangChi SET ngay= '" + NgayNew + "' WHERE Id = '" + khoanChi.getIdKhoanChi() + "' ");
                        Toast.makeText(mCcontext.getActivity(), "Update Thành Công", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        mCcontext.getDataList(lv);
                    }
                });
                dialog.show();
            }
        });
        return view;
    }
}

