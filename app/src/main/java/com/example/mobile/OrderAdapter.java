package com.example.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;
    private OnOrderClickListener onOrderClickListener;

    public OrderAdapter(Context context, List<Order> orderList, OnOrderClickListener onOrderClickListener) {
        this.context = context;
        this.orderList = orderList;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view, onOrderClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderName.setText(order.getName());
        holder.tvOrderTotal.setText(String.valueOf(order.getTotal()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvOrderName, tvOrderTotal;
        OnOrderClickListener onOrderClickListener;

        public OrderViewHolder(@NonNull View itemView, OnOrderClickListener onOrderClickListener) {
            super(itemView);
            tvOrderName = itemView.findViewById(R.id.tvOrderName);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            this.onOrderClickListener = onOrderClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onOrderClickListener.onOrderClick((Order) v.getTag());
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }
}
