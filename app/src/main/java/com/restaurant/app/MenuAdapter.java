package com.restaurant.app;

import static java.lang.String.*;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuItemHolder> {

    private List<MenuItem> menuItems;
    private OnItemClickListener listener;
    private final boolean isStaff;

    public MenuAdapter(List<MenuItem> menuItems, boolean isStaff) {
        this.menuItems = menuItems;
        this.isStaff = isStaff;
    }

    public interface OnItemClickListener {
        void onItemClick(MenuItem menuItem);
        void onEditClick(MenuItem menuItem);
        void onDeleteClick(MenuItem menuItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);
        return new MenuItemHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull MenuItemHolder holder, int position) {
        MenuItem currentItem = menuItems.get(position);
        holder.textViewName.setText(currentItem.getName());
        holder.textViewPrice.setText(format("$%.2f", currentItem.getPrice()));

        // Load image using Glide if imagePath is available
        if (currentItem.getImagePath() != null && !currentItem.getImagePath().isEmpty())
            Glide.with(holder.itemView.getContext())
                    .load(currentItem.getImagePath())
                    .placeholder(R.drawable.ic_menu) // Placeholder image
                    .into(holder.imageViewItem);
        else {
            holder.imageViewItem.setImageResource(R.drawable.ic_menu); // Default image
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentItem);
            }
        });

        // Only show edit/delete buttons for staff
        if (isStaff) {
            holder.imageViewEdit.setVisibility(View.VISIBLE);
            holder.imageViewDelete.setVisibility(View.VISIBLE);
            
            holder.imageViewEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditClick(currentItem);
                }
            });

            holder.imageViewDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(currentItem);
                }
            });
        } else {
            holder.imageViewEdit.setVisibility(View.GONE);
            holder.imageViewDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return menuItems != null ? menuItems.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
        notifyDataSetChanged();
    }

    static class MenuItemHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewPrice;
        private final ImageView imageViewItem;
        private final ImageView imageViewEdit;
        private final ImageView imageViewDelete;

        public MenuItemHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewMenuItemName);
            textViewPrice = itemView.findViewById(R.id.textViewMenuItemPrice);
            imageViewItem = itemView.findViewById(R.id.imageViewMenuItem);
            imageViewEdit = itemView.findViewById(R.id.imageViewEdit);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }
    }
}