package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Product;
import com.example.myapplication.R;

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

        if (product != null) {
            holder.itemView.setTag(product);
            holder.tvProductName.setText(product.getName());
            holder.tvProductPrice.setText(String.valueOf(product.getPrice()));
            int resourceId = context.getResources().getIdentifier(product.getImgUrl(), "drawable", context.getPackageName());
            if (resourceId != 0) {
                holder.tvProductImgUrl.setImageResource(resourceId);
            } else {
                // If the resource does not exist, you can set a default image or handle it accordingly
                holder.tvProductImgUrl.setImageResource(R.drawable.baseline_add_24); // Replace 'default_image' with your default image resource
            }
            holder.tvProductBrand.setText(product.getBrand());
            holder.tvProductAmount.setText(String.valueOf(product.getAmount()));
            holder.tvProductCapacity.setText(product.getCapacity());
        } else {
            holder.itemView.setTag(null); // Important to avoid NullPointerException
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvProductName, tvProductPrice, tvProductBrand, tvProductAmount, tvProductCapacity;
        ImageView tvProductImgUrl;
        OnProductClickListener onProductClickListener;

        public ProductViewHolder(@NonNull View itemView, OnProductClickListener onProductClickListener) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductBrand = itemView.findViewById(R.id.tvProductBrand); // Assuming you have this TextView in your layout
            tvProductAmount = itemView.findViewById(R.id.tvProductAmount); // Assuming you have this TextView in your layout
            tvProductCapacity = itemView.findViewById(R.id.tvProductCapacity);
            tvProductImgUrl=itemView.findViewById(R.id.tvProductImgUrl);
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
