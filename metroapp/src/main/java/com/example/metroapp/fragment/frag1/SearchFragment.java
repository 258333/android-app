package com.example.metroapp.fragment.frag1;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.metroapp.DataBase.CRUD;
import com.example.metroapp.Infomation.SubwayBean;
import com.example.metroapp.R;
import com.example.metroapp.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1,mParam2;
    private EditText mEt_start,mEt_end;
    private ImageButton mBtn_search;
    private RecyclerView mList;
    private List<SubwayBean> mData;
    private String start,end;

    public SearchFragment() {

    }
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

        return inflater.inflate(R.layout.fragment_search, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEt_start  = view.findViewById(R.id.frag_et_start);
        mEt_end    = view.findViewById(R.id.frag_et_end);
        mBtn_search= view.findViewById(R.id.frag_btn_find);

        mList = view.findViewById(R.id.frag_recycle_show);



        mBtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start=mEt_start.getText().toString();
                end=mEt_end.getText().toString();
                CRUD crud = new CRUD(getContext());
                if (crud.ArrayTransferStation(start,end)==null){
                    Toast.makeText(getContext(), "输入为空或查不到此数据", Toast.LENGTH_SHORT).show();
                }else {
                    initData();
                }

            }
        });



    }

    private void initData() {

            mData = new ArrayList<SubwayBean>();
            CRUD crud = new CRUD(getContext());
            //导入数据
            ArrayList<SubwayBean> subList = crud.ArrayTransferStation(start,end);

            for (int i = 0; i < subList.size(); i++) {

                SubwayBean subway = new SubwayBean();
                SubwayBean subData =subList.get(i);
                subway._StartStation = subData._StartStation;
                subway._EndStation = subData._EndStation;
                subway._Number = subData._Number;
                subway._Path = subData._Path;
                mData.add(subway);
            }
            //设置布局管理器
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            mList.setLayoutManager(linearLayoutManager);
            //创建适配器
            ListViewAdapter adapter = new ListViewAdapter(mData);
            //设置到listview里
            mList.setAdapter(adapter);
    }

}