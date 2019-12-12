package com.example.assht1.Tag_Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assht1.Adapter.NoiDungLoaiChi_Adapter;
import com.example.assht1.Model.LoaiChi;
import com.example.assht1.R;
import com.example.assht1.database.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;


public class tab_LoaiChi_Fragment extends Fragment {
    public static List<LoaiChi> arrayList = new ArrayList<>();
    public static NoiDungLoaiChi_Adapter adapter;
    Database database;
    private ListView listView;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        database = new Database(getActivity());
        View view = inflater.inflate(R.layout.tab_loaichi_fragment, container, false);
        fab = view.findViewById(R.id.fabloaithu);
        listView = view.findViewById(R.id.lvloaiThu);
        getDataList(listView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogThem(listView);

            }
        });


        return view;
    }

    public void dialogThem(final ListView lv) {
        final Dialog dialog = new Dialog(getActivity());
        database = new Database(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_loaichi);

        //Dialog Full SCreen
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        //btn Huỷ
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        //btn Thêm
        Button btnThem = (Button) dialog.findViewById(R.id.btnThem);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView txtMaLC = dialog.findViewById(R.id.txtMaLC);
                TextView txtTenLC = dialog.findViewById(R.id.txtTenLC);

                String maLC = txtMaLC.getText().toString();
                String tenLC = txtTenLC.getText().toString();
//
                // lấy thông tin nhâập
                LoaiChi lc = new LoaiChi(maLC,tenLC);
                //id, mã,ten, the loai, ngay
                arrayList.add(lc);
                if (database.addDataLC(lc) >0) {
                    if (lc.getMaLC().toString().equals("") &&
                            lc.getTenLC().toString().equals("")) {
                        Toast.makeText(getActivity(), "Empty data!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                getDataList(lv);
                dialog.cancel();

            }
        });
    }

    public void getDataList(ListView lv) {
        database = new Database(getActivity());
        adapter = new NoiDungLoaiChi_Adapter(database.getDataLC(), this, R.layout.item_loaichi_fragment, lv);
        lv.setAdapter(adapter);
    }
}




