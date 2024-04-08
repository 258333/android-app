package com.example.metroapp.fragment.frag3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metroapp.DataBase.CRUD;
import com.example.metroapp.R;
public class MapFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private EditText mEt1;
    private ImageButton mBtn1;
    private TextView mTv1;
    public MapFragment() {
    }
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEt1  = view.findViewById(R.id.map_et_1);
        mBtn1    = view.findViewById(R.id.map_btn_1);
        mTv1= view.findViewById(R.id.map_tv_1);

        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text1=mEt1.getText().toString();
                CRUD crud = new CRUD(getContext());
                String text2 = crud.getLine(text1);
                if (text2 != null) {
                    mTv1.setText(text1+" 位于 "+text2);
                }else {
                    Toast.makeText(getContext(), "输入为空或查不到此数据", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}