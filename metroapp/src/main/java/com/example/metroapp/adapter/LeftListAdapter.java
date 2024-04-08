package com.example.metroapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metroapp.Infomation.SubwayBean;
import com.example.metroapp.R;

import java.util.List;

public class LeftListAdapter extends RecyclerView.Adapter<LeftListAdapter.InnerHolder> {
    private final List<String> mData;
    private OnItemClickListener1 mOnItemClickListener1;

    public LeftListAdapter(List<String> data){this.mData = data;}

    public void  setOnItemClick1(OnItemClickListener1 listener1){
        this.mOnItemClickListener1 = listener1;
    }
    public interface OnItemClickListener1{
        void OnItemClick1(int position);
    }
    //创建View
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =View.inflate(parent.getContext(), R.layout.item_list_left,null);

        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {

        holder.setData(mData.get(position),position);

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }


    public class InnerHolder extends RecyclerView.ViewHolder{

        private TextView m1;
        private int mPosition;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);

            m1 = itemView.findViewById(R.id.item_left_station);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener1 != null) {
                        mOnItemClickListener1.OnItemClick1(mPosition);
                    }
                }
            });
        }

        public void setData(String station,int position) {

            this.mPosition = position;
            m1.setText(station);

        }
    }
}
