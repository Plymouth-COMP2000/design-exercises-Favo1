package com.restaurant.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {
    
    private List<CreateOrderActivity.OrderItem> items;
    private OnQuantityChangeListener listener;

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    public OrderItemAdapter(List<CreateOrderActivity.OrderItem> items, OnQuantityChangeListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CreateOrderActivity.OrderItem item = items.get(position);
        holder.tvItemName.setText(item.getMenuItem().getName());
        holder.tvItemPrice.setText(String.format(Locale.getDefault(), "$%.2f", item.getMenuItem().getPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 0) {
                item.setQuantity(item.getQuantity() - 1);
                holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
                if (listener != null) {
                    listener.onQuantityChanged();
                }
            }
        });

        holder.btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
            if (listener != null) {
                listener.onQuantityChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemPrice, tvQuantity;
        ImageButton btnMinus, btnPlus;

        ViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
        }
    }
}
