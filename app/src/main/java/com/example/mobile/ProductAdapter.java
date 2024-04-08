package com.example.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private OnProductClickListener onProductClickListener;

    public ProductAdapter(Context context, List<Product> productList, OnProductClickListener onProductClickListener) {
        this.context = context;
        this.productList = productList;
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view, onProductClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvProductName, tvProductPrice;
        OnProductClickListener onProductClickListener;

        public ProductViewHolder(@NonNull View itemView, OnProductClickListener onProductClickListener) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            this.onProductClickListener = onProductClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProductClickListener.onProductClick((Product) v.getTag());
        }
    }

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
}
