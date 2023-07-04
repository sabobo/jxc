package com.dgby.jxc.activity.count;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgby.jxc.R;

import java.util.ArrayList;
import java.util.List;

public class CountAdapter extends RecyclerView.Adapter<CountAdapter.CountViewHolder> {

    private List<CountItem> mData;

    public CountAdapter() {
        mData = new ArrayList<>();
    }

    public void setData(List<CountItem> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_count_item, parent, false);
        return new CountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CountViewHolder holder, int position) {
        CountItem item = mData.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class CountViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTextView;
        private TextView mValueTextView;

        public CountViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.name_textview);
            mValueTextView = itemView.findViewById(R.id.value_textview);
        }

        public void bind(CountItem item) {
            if(0==item.getType()){
                mNameTextView.setText("总库存数：");
                mValueTextView.setText(item.getValue()+"箱");
            }else if(1==item.getType()){
                mNameTextView.setText("产品名："+item.getName());
                mValueTextView.setText("库存："+item.getValue()+"箱");
            }else if(2==item.getType()){
                mNameTextView.setText("供应商名称："+item.getName());
                mValueTextView.setText("供应商名称："+item.getValue());
            }else if(3==item.getType()){
                mNameTextView.setText("产品名："+item.getName());
                mValueTextView.setText("产品类型："+item.getValue()+"个");
            }else if(4==item.getType()){
                mNameTextView.setText("价格区间："+item.getName());
                mValueTextView.setText("区间数量："+item.getValue()+"个");
            }

        }
    }
}

