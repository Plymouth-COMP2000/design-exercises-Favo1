package com.restaurant.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ReservationsFragment extends Fragment implements ReservationAdapter.OnItemClickListener {

    private ReservationViewModel reservationViewModel;
    private ReservationAdapter reservationAdapter;

    public static final int ADD_RESERVATION_REQUEST = 1;
    public static final int EDIT_RESERVATION_REQUEST = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_reservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reservationAdapter = new ReservationAdapter(new ArrayList<>());
        recyclerView.setAdapter(reservationAdapter);
        reservationAdapter.setOnItemClickListener(this);

        reservationViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);
        reservationViewModel.getAllReservations().observe(getViewLifecycleOwner(), reservations -> {
            reservationAdapter.setReservations(reservations);
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_reservation);
        fab.setOnClickListener(this::onClick);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == Activity.RESULT_OK) {
            String customerName = data.getStringExtra(AddEditReservationActivity.EXTRA_CUSTOMER_NAME);
            String date = data.getStringExtra(AddEditReservationActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddEditReservationActivity.EXTRA_TIME);
            int guests = data.getIntExtra(AddEditReservationActivity.EXTRA_NUMBER_OF_GUESTS, 1);
            String status = data.getStringExtra(AddEditReservationActivity.EXTRA_STATUS);

            if (requestCode == ADD_RESERVATION_REQUEST) {
                Reservation reservation = new Reservation(customerName, date, time, guests, status);
                reservationViewModel.insert(reservation);
                Toast.makeText(getContext(), "Reservation added", Toast.LENGTH_SHORT).show();
            } else if (requestCode == EDIT_RESERVATION_REQUEST) {
                int id = data.getIntExtra(AddEditReservationActivity.EXTRA_ID, -1);
                if (id == -1) {
                    Toast.makeText(getContext(), "Reservation can't be updated", Toast.LENGTH_SHORT).show();
                    return;
                }
                Reservation reservation = new Reservation(customerName, date, time, guests, status);
                reservation.setId(id);
                reservationViewModel.update(reservation);
                Toast.makeText(getContext(), "Reservation updated", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Reservation not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Reservation reservation) {
        // For now, let's allow cancelling a reservation directly from a click
        new AlertDialog.Builder(getContext())
                .setTitle("Cancel Reservation")
                .setMessage("Are you sure you want to cancel the reservation for " + reservation.getCustomerName() + " on " + reservation.getDate() + " at " + reservation.getTime() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    reservation.setStatus("Cancelled"); // Update status to Cancelled
                    reservationViewModel.update(reservation);
                    Toast.makeText(getContext(), "Reservation cancelled", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void onClick(View v) {
        Intent intent = new Intent(getContext(), AddEditReservationActivity.class);
        startActivityForResult(intent, ADD_RESERVATION_REQUEST);
    }
}