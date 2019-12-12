package com.example.assht1.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assht1.R;

import androidx.fragment.app.Fragment;

public class GioiThieuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.gioithieu_fragment,container,false);
        return view;
    }
}
