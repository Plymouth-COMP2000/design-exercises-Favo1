package com.restaurant.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestockItemAdapter extends RecyclerView.Adapter<RestockItemAdapter.ViewHolder> {

    private List<MenuItem> items;

    public RestockItemAdapter(List<MenuItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restock, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem item = items.get(position);
        holder.tvItemName.setText(item.getName());
        holder.tvStockQty.setText(String.format("Stock: %d", item.getStockQuantity()));
        
        String note = item.getRestockNote();
        if (note != null && !note.isEmpty()) {
            holder.tvRestockNote.setVisibility(View.VISIBLE);
            holder.tvRestockNote.setText(note);
        } else {
            holder.tvRestockNote.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvStockQty, tvRestockNote;

        ViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvStockQty = itemView.findViewById(R.id.tvStockQty);
            tvRestockNote = itemView.findViewById(R.id.tvRestockNote);
        }
    }
}
