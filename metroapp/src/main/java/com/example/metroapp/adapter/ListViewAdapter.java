package com.example.metroapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metroapp.Infomation.SubwayBean;
import com.example.metroapp.R;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.InnerHolder> {


    private final List<SubwayBean> mData;

    public ListViewAdapter(List<SubwayBean> data){this.mData = data;}

    //创建View
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =View.inflate(parent.getContext(), R.layout.item_list_view,null);

        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {

        holder.setData(mData.get(position));

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }


    public class InnerHolder extends RecyclerView.ViewHolder{

        private TextView m1,m2,m3,m4;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);

            m1 = itemView.findViewById(R.id.list_1);
            m2 = itemView.findViewById(R.id.list_2);
            m3  = itemView.findViewById(R.id.list_3);
            m4 = itemView.findViewById(R.id.list_4);
        }

        public void setData(SubwayBean subwayBean) {

            m1.setText(subwayBean._StartStation);
            m2.setText(subwayBean._EndStation);
            m3.setText("经过站数："+(CharSequence) subwayBean._Number);
            m4.setText(subwayBean._Path);
        }
    }

}
