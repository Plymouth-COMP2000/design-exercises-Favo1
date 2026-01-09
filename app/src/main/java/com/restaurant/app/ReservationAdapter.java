package com.restaurant.app;

import static java.lang.String.*;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationHolder> {
    private List<Reservation> reservations;
    private OnItemClickListener listener;
    private boolean isStaff;

    public ReservationAdapter(List<Reservation> reservations, boolean isStaff) {
        this.reservations = reservations;
        this.isStaff = isStaff;
    }

    public interface OnItemClickListener {
        void onItemClick(Reservation reservation);
        void onCancelClick(Reservation reservation);
        void onEditClick(Reservation reservation);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReservationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation, parent, false);
        return new ReservationHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ReservationHolder holder, int position) {
        Reservation currentReservation = reservations.get(position);
        holder.textViewCustomerName.setText(currentReservation.getCustomerName());
        holder.textViewDateTime.setText(format("%s - %s", currentReservation.getDate(), currentReservation.getTime()));
        holder.textViewGuests.setText(format("Guests: %d", currentReservation.getNumberOfGuests()));
        holder.textViewStatus.setText(currentReservation.getStatus());

        // Set status color
        String status = currentReservation.getStatus();
        if ("Confirmed".equals(status)) {
            holder.textViewStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_confirmed));
        } else if ("Pending".equals(status)) {
            holder.textViewStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_pending));
        } else if ("Cancelled".equals(status)) {
            holder.textViewStatus.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_cancelled));
        } else {
            holder.textViewStatus.setBackgroundColor(Color.GRAY);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentReservation);
            }
        });

        // Show/hide action buttons based on role and status
        if (isStaff || !"Cancelled".equals(status)) {
            holder.actionButtons.setVisibility(View.VISIBLE);
            
            holder.imageViewEdit.setOnClickListener(v -> {
                if (listener != null && !"Cancelled".equals(status)) {
                    listener.onEditClick(currentReservation);
                }
            });

            holder.imageViewCancel.setOnClickListener(v -> {
                if (listener != null && !"Cancelled".equals(status)) {
                    listener.onCancelClick(currentReservation);
                }
            });
        } else {
            holder.actionButtons.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reservations != null ? reservations.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        notifyDataSetChanged();
    }

    static class ReservationHolder extends RecyclerView.ViewHolder {
        private final TextView textViewCustomerName;
        private final TextView textViewDateTime;
        private final TextView textViewGuests;
        private final TextView textViewStatus;
        private final LinearLayout actionButtons;
        private final ImageView imageViewEdit;
        private final ImageView imageViewCancel;

        public ReservationHolder(@NonNull View itemView) {
            super(itemView);
            textViewCustomerName = itemView.findViewById(R.id.textViewCustomerName);
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
            textViewGuests = itemView.findViewById(R.id.textViewGuests);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            actionButtons = itemView.findViewById(R.id.actionButtons);
            imageViewEdit = itemView.findViewById(R.id.imageViewEdit);
            imageViewCancel = itemView.findViewById(R.id.imageViewCancel);
        }
    }
}