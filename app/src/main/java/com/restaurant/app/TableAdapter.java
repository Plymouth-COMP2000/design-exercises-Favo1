package com.restaurant.app;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableHolder> {
    private List<Table> tables;
    private OnItemClickListener listener;
    private boolean isStaff;

    public TableAdapter(List<Table> tables, boolean isStaff) {
        this.tables = tables;
        this.isStaff = isStaff;
    }

    public interface OnItemClickListener {
        void onItemClick(Table table);
        void onStatusChange(Table table);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table, parent, false);
        return new TableHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull TableHolder holder, int position) {
        Table currentTable = tables.get(position);
        holder.textViewTableName.setText(currentTable.getTableName());
        holder.textViewItemCount.setText(String.format("Capacity: %d", currentTable.getCapacity()));
        holder.textViewOrderStatus.setText(currentTable.getStatus());

        // Set status color
        String status = currentTable.getStatus();
        if ("Available".equals(status)) {
            holder.textViewOrderStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_confirmed));
        } else if ("Occupied".equals(status)) {
            holder.textViewOrderStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_cancelled));
        } else if ("Reserved".equals(status)) {
            holder.textViewOrderStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_pending));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentTable);
            }
        });

        holder.textViewOrderStatus.setOnClickListener(v -> {
            if (listener != null && isStaff) {
                listener.onStatusChange(currentTable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tables != null ? tables.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTables(List<Table> tables) {
        this.tables = tables;
        notifyDataSetChanged();
    }

    static class TableHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTableName;
        private final TextView textViewItemCount;
        private final TextView textViewOrderStatus;

        public TableHolder(@NonNull View itemView) {
            super(itemView);
            textViewTableName = itemView.findViewById(R.id.textViewTableName);
            textViewItemCount = itemView.findViewById(R.id.textViewItemCount);
            textViewOrderStatus = itemView.findViewById(R.id.textViewOrderStatus);
        }
    }
}
