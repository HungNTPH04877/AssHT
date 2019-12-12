package com.example.assht1.Adapter;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assht1.Model.LoaiChi;
import com.example.assht1.R;
import com.example.assht1.Tag_Fragment.tab_LoaiChi_Fragment;
import com.example.assht1.database.Database;

import java.util.List;

public class NoiDungLoaiChi_Adapter extends BaseAdapter {
    public static List<LoaiChi> loaiChiList;
    private tab_LoaiChi_Fragment mCcontext;
    private int layout;
    private ListView mListView;
    EditText txtNhapNgay;
    Database db;

    public NoiDungLoaiChi_Adapter(List<LoaiChi> loaiChiList, tab_LoaiChi_Fragment context, int layout, ListView lv) {
        this.loaiChiList = loaiChiList;
        this.mCcontext = context;
        this.layout = layout;
        this.mListView = lv;
    }

    @Override
    public int getCount() {
        return loaiChiList.size();
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
        TextView maLC,tenLC;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        db = new Database(mCcontext.getActivity());
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mCcontext.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.maLC = (TextView) view.findViewById(R.id.tvMaLC);
            holder.tenLC = view.findViewById(R.id.tvTenLC);
            holder.btnDelete =  (Button) view.findViewById(R.id.btnDeleteLoaiChi);
            holder.btnEdit = (Button) view.findViewById(R.id.btnEditLoaiChi);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final LoaiChi loaiChi = loaiChiList.get(i);
        holder.maLC.setText(loaiChi.getMaLC());
        holder.tenLC.setText("" + (loaiChi.getTenLC()));
//        Bat Su kien Delete
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LoaiChi lc = (LoaiChi) loaiChiList.get(i);
//                Toast.makeText(mCcontext.getActivity(), "" + nd.getNoiDung() + nd.getIdNoiDung(), Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder dialogXoa = new AlertDialog.Builder(mCcontext.getActivity());
                dialogXoa.setMessage("Do you want delete this?");
                dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.QueryData("DELETE FROM LoaiChi WHERE id = " + lc.getIdLoaiChi() + " ");
                        Toast.makeText(mCcontext.getActivity(), "Đã Xoá", Toast.LENGTH_SHORT).show();
                        mCcontext.getDataList(mListView);
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
                dialog.setContentView(R.layout.dialog_update_loaichi);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                final EditText txtMaLC = dialog.findViewById(R.id.editMaLC);
                final EditText txtTenLC = dialog.findViewById(R.id.editTenLC);

                txtMaLC.setText(loaiChi.getMaLC());
                txtTenLC.setText(loaiChi.getTenLC());
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
                        final String maloaichiNew = txtMaLC.getText().toString();
                        String TenLoaiChiNew = txtTenLC.getText().toString();
                        db.QueryData("UPDATE LoaiChi SET maLC= '" + maloaichiNew + "',tenLC= '" + TenLoaiChiNew + "' WHERE Id = '" + loaiChi.getIdLoaiChi() + "' ");
                        db.QueryData("UPDATE LoaiChi SET tenLC= '" + TenLoaiChiNew + "' WHERE Id = '" + loaiChi.getIdLoaiChi() + "' ");
                        Toast.makeText(mCcontext.getActivity(), "Update Thành Công", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        mCcontext.getDataList(mListView);
                    }
                });
                dialog.show();
            }
        });
        return view;
    }


}
