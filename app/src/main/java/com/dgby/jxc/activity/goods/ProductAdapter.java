package com.dgby.jxc.activity.goods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgby.jxc.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> mProductList;
    private OnProductClickListener mOnProductClickListener;

    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        mProductList = productList;
        mOnProductClickListener = listener;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = mProductList.get(position);

        holder.nameTextView.setText("名称："+product.getName());
        holder.priceTextView.setText("价格：¥"+String.format(Locale.getDefault(), "%.2f", product.getPrice()));
        holder.stockTextView.setText("库存："+String.valueOf(product.getStock()));

        holder.itemView.setOnClickListener(v -> {
            if (mOnProductClickListener != null) {
                mOnProductClickListener.onProductClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView priceTextView;
        public TextView stockTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tv_product_name);
            priceTextView = itemView.findViewById(R.id.tv_product_price);
            stockTextView = itemView.findViewById(R.id.tv_product_stock);
        }
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public void removeProduct(int position) {
        mProductList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateProduct(Product product, int position) {
        mProductList.set(position, product);
        notifyItemChanged(position);
    }
}
