package com.example.metroapp.fragment.frag2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.metroapp.DataBase.CRUD;
import com.example.metroapp.R;
import com.example.metroapp.adapter.LeftListAdapter;

import java.util.ArrayList;
import java.util.List;

public class LeftListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView mList,mList2;
    private List<String> mData,mData2;

    private LeftListAdapter mAdapter,mAdapter2;
    public LeftListFragment() {

    }
    public static LeftListFragment newInstance(String param1, String param2) {
        LeftListFragment fragment = new LeftListFragment();
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
        return inflater.inflate(R.layout.fragment_left_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = view.findViewById(R.id.frag_recycle_left);
        mList2 = view.findViewById(R.id.frag_recycle_right);
        initData1();

    }


    private void initData2(int position) {
        mData2 = new ArrayList<String>();
        CRUD crud = new CRUD(getContext());
        //导入数据
        ArrayList<String> subList = crud.PositionStation(position);

        for (int i = 0; i < subList.size(); i++) {

            String stationData = subList.get(i);
            mData2.add(stationData);
        }
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mList2.setLayoutManager(linearLayoutManager);
        //创建适配器
        mAdapter2 = new LeftListAdapter(mData2);
        //设置到listview里
        mList2.setAdapter(mAdapter2);
    }

    private void initData1() {
        mData = new ArrayList<String>();
        CRUD crud = new CRUD(getContext());
        //导入数据
        ArrayList<String> subList = crud.getArrayLine();

        for (int i = 0; i < subList.size(); i++) {

            String stationData = subList.get(i);
            mData.add(stationData);
        }
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mList.setLayoutManager(linearLayoutManager);
        //创建适配器
        mAdapter = new LeftListAdapter(mData);
        //设置到listview里
        mList.setAdapter(mAdapter);

        mAdapter.setOnItemClick1(new LeftListAdapter.OnItemClickListener1() {
            @Override
            public void OnItemClick1(int position) {
                initData2(position);
            }
        });

    }
}