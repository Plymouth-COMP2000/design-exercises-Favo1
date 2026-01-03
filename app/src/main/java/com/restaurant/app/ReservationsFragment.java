package com.restaurant.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationsFragment extends Fragment implements ReservationAdapter.OnItemClickListener {

    private ReservationViewModel reservationViewModel;
    private ReservationAdapter reservationAdapter;
    private String userType;
    private String currentUsername;
    private List<Reservation> allReservations = new ArrayList<>();
    private SearchView searchView;

    public static final int ADD_RESERVATION_REQUEST = 1;
    public static final int EDIT_RESERVATION_REQUEST = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);

        // Get user info from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("app_prefs", Activity.MODE_PRIVATE);
        userType = prefs.getString("usertype", "guest");
        currentUsername = prefs.getString("username", "");

        searchView = view.findViewById(R.id.searchViewReservations);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_reservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reservationAdapter = new ReservationAdapter(new ArrayList<>(), userType.equals("staff"));
        recyclerView.setAdapter(reservationAdapter);
        reservationAdapter.setOnItemClickListener(this);

        reservationViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);
        reservationViewModel.getAllReservations().observe(getViewLifecycleOwner(), reservations -> {
            // Filter reservations based on user type
            if (userType.equals("guest")) {
                // Guests only see their own reservations
                allReservations = reservations.stream()
                        .filter(r -> currentUsername.equals(r.getCreatedBy()))
                        .collect(Collectors.toList());
            } else {
                // Staff see all reservations
                allReservations = reservations;
            }
            reservationAdapter.setReservations(allReservations);
        });

        // Setup search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterReservations(newText);
                return true;
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_reservation);
        fab.setOnClickListener(this::onClick);

        return view;
    }

    private void filterReservations(String query) {
        if (query.isEmpty()) {
            reservationAdapter.setReservations(allReservations);
        } else {
            List<Reservation> filtered = allReservations.stream()
                    .filter(r -> r.getCustomerName().toLowerCase().contains(query.toLowerCase()) ||
                            r.getDate().contains(query) ||
                            r.getStatus().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
            reservationAdapter.setReservations(filtered);
        }
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
                reservation.setCreatedBy(currentUsername);
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
                reservation.setCreatedBy(currentUsername);
                reservationViewModel.update(reservation);
                Toast.makeText(getContext(), "Reservation updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(Reservation reservation) {
        // Show reservation details
        new AlertDialog.Builder(getContext())
                .setTitle("Reservation Details")
                .setMessage("Customer: " + reservation.getCustomerName() + "\n" +
                        "Date: " + reservation.getDate() + "\n" +
                        "Time: " + reservation.getTime() + "\n" +
                        "Guests: " + reservation.getNumberOfGuests() + "\n" +
                        "Status: " + reservation.getStatus())
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public void onCancelClick(Reservation reservation) {
        String title = userType.equals("staff") ? "Cancel Reservation" : "Cancel My Reservation";
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage("Are you sure you want to cancel this reservation?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    reservation.setStatus("Cancelled");
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