package com.example.assht1.Tag_Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assht1.Adapter.NoiDungKhoangChi_Adapter;
import com.example.assht1.Model.KhoanChi;
import com.example.assht1.Model.LoaiChi;
import com.example.assht1.R;
import com.example.assht1.database.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.fragment.app.Fragment;

public class tab_KhoanChi_Fragment extends Fragment {
    Database database;
    NoiDungKhoangChi_Adapter adapter;
    List<KhoanChi> khoanChiList;
    ListView lvKhoanChi;
    TextView txtNgay;
    private Spinner spLoaiChi;
    private List<LoaiChi> loaiChiList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_khoanchi_fragment, container, false);

        khoanChiList = new ArrayList<>();
        lvKhoanChi = view.findViewById(R.id.lvKhoanChi);
        getDataList(lvKhoanChi);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThem();
            }
        });
        return view;

    }

    public void dialogThem() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_khoanchi);
        //Dialog Full SCreen
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        // lấy dữ liệu Spiner
        database = new Database(getActivity());
        loaiChiList = database.getDataLC();
        spLoaiChi = (Spinner) dialog.findViewById(R.id.spLoaiChi);

        ArrayAdapter<LoaiChi> adapterLoaiChi = new ArrayAdapter<LoaiChi>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                loaiChiList);
        adapterLoaiChi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaiChi.setAdapter(adapterLoaiChi);
        // bắt sự kiện nút get date
        Button btnGetDate = dialog.findViewById(R.id.btnDate);
        btnGetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNgay = dialog.findViewById(R.id.txtNgay);
                final Calendar cl = Calendar.getInstance();
                int day = cl.get(Calendar.DATE);
                int month = cl.get(Calendar.MONTH);
                int year = cl.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        cl.set(i, i1, i2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        txtNgay.setText(simpleDateFormat.format(cl.getTime()));

                    }
                }, year, month, day);
                datePickerDialog.show();
            }

        });
        //btn Huỷ
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        //bắt sự kiện nút Thêm
        Button btnThem = (Button) dialog.findViewById(R.id.btnThem);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtmaKC = dialog.findViewById(R.id.txtMaKC);
                TextView txtTenKC = dialog.findViewById(R.id.txtTenKC);
                TextView txtTheLoai = dialog.findViewById(R.id.txtTheLoai);
                txtNgay = dialog.findViewById(R.id.txtNgay);
                String maKC = txtmaKC.getText().toString();
                String tenKC = txtTenKC.getText().toString();
                String theLoai = txtTheLoai.getText().toString();
                String nGay = txtNgay.getText().toString();

                final KhoanChi khoanChi = new KhoanChi(maKC, tenKC, theLoai, nGay);

                khoanChiList.add(khoanChi);
                if (database.addDataKC(khoanChi) > 0) {
                    if (khoanChi.getMaKC().toString().equals("") &&
                            khoanChi.getTenKC().toString().equals("") &&
                            khoanChi.getNgay().toString().equals("") &&
                            khoanChi.getTheLoai().toString().equals(""))
                    {
                        Toast.makeText(getActivity(), "Empty data!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                }
                getDataList(lvKhoanChi);
                dialog.cancel();
            }
        });
    }

    public void getDataList(ListView lv) {
        database = new Database(getActivity());
        adapter = new NoiDungKhoangChi_Adapter(database.getDataKC(), this, R.layout.item_khoanchi_fragment, lv);
        lv.setAdapter(adapter);
    }
}
