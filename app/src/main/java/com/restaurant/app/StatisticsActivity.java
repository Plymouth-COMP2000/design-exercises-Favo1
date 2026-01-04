package com.restaurant.app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tvTotalMenuItems;
    private TextView tvTotalReservations;
    private TextView tvPendingReservations;
    private TextView tvConfirmedReservations;
    private TextView tvCancelledReservations;
    private TextView tvTotalRevenue;
    private TextView tvDailyRevenue;
    private TextView tvRestockCount;
    private androidx.recyclerview.widget.RecyclerView recyclerRestockItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Statistics Dashboard");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initViews();
        loadStatistics();
    }

    private void initViews() {
        tvTotalMenuItems = findViewById(R.id.tvTotalMenuItems);
        tvTotalReservations = findViewById(R.id.tvTotalReservations);
        tvPendingReservations = findViewById(R.id.tvPendingReservations);
        tvConfirmedReservations = findViewById(R.id.tvConfirmedReservations);
        tvCancelledReservations = findViewById(R.id.tvCancelledReservations);
        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        tvDailyRevenue = findViewById(R.id.tvDailyRevenue);
        tvRestockCount = findViewById(R.id.tvRestockCount);
        recyclerRestockItems = findViewById(R.id.recyclerRestockItems);

        // Setup RecyclerView for restock items
        recyclerRestockItems.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
    }

    private void loadStatistics() {
        MenuViewModel menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        ReservationViewModel reservationViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);
        OrderViewModel orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        // Get today's date
        String today = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(new java.util.Date());

        // Load menu statistics
        menuViewModel.getAllMenuItems().observe(this, menuItems -> {
            tvTotalMenuItems.setText(String.valueOf(menuItems.size()));
            
            // Calculate potential revenue (total of all menu prices)
            double totalRevenue = menuItems.stream()
                    .mapToDouble(MenuItem::getPrice)
                    .sum();
            tvTotalRevenue.setText(String.format("$%.2f", totalRevenue));
        });

        // Load reservation statistics
        reservationViewModel.getAllReservations().observe(this, reservations -> {
            tvTotalReservations.setText(String.valueOf(reservations.size()));
            
            long pending = reservations.stream()
                    .filter(r -> "Pending".equals(r.getStatus()))
                    .count();
            tvPendingReservations.setText(String.valueOf(pending));
            
            long confirmed = reservations.stream()
                    .filter(r -> "Confirmed".equals(r.getStatus()))
                    .count();
            tvConfirmedReservations.setText(String.valueOf(confirmed));
            
            long cancelled = reservations.stream()
                    .filter(r -> "Cancelled".equals(r.getStatus()))
                    .count();
            tvCancelledReservations.setText(String.valueOf(cancelled));
        });

        // Load daily revenue
        orderViewModel.getDailyRevenue(today).observe(this, revenue -> {
            double dailyRev = revenue != null ? revenue : 0.0;
            tvDailyRevenue.setText(String.format("$%.2f", dailyRev));
        });

        // Load restock reminders
        menuViewModel.getItemsNeedingRestock().observe(this, restockItems -> {
            tvRestockCount.setText(String.valueOf(restockItems.size()));
            
            // Display restock items in RecyclerView
            RestockItemAdapter adapter = new RestockItemAdapter(restockItems);
            recyclerRestockItems.setAdapter(adapter);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
