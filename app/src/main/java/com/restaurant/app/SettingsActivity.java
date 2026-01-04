package com.restaurant.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private SwitchMaterial switchNotifications;
    private SwitchMaterial switchReservationUpdates;
    private SwitchMaterial switchNewReservations;
    private TextView tvUsername;
    private TextView tvEmail;
    private TextView tvUserType;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize views
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvUserType = findViewById(R.id.tvUserType);
        switchNotifications = findViewById(R.id.switchNotifications);
        switchReservationUpdates = findViewById(R.id.switchReservationUpdates);
        switchNewReservations = findViewById(R.id.switchNewReservations);

        loadUserInfo();
        loadNotificationSettings();
        setupListeners();
    }

    private void loadUserInfo() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String username = prefs.getString("username", "User");
        String email = prefs.getString("email", "");
        userType = prefs.getString("usertype", "guest");

        tvUsername.setText(username);
        tvEmail.setText(email);
        tvUserType.setText(userType.equals("staff") ? "Staff Member" : "Guest");

        // Show/hide staff-specific notifications
        if (userType.equals("staff")) {
            switchNewReservations.setVisibility(android.view.View.VISIBLE);
            findViewById(R.id.labelNewReservations).setVisibility(android.view.View.VISIBLE);
        } else {
            switchNewReservations.setVisibility(android.view.View.GONE);
            findViewById(R.id.labelNewReservations).setVisibility(android.view.View.GONE);
        }
    }

    private void loadNotificationSettings() {
        SharedPreferences prefs = getSharedPreferences("notification_prefs", MODE_PRIVATE);
        switchNotifications.setChecked(prefs.getBoolean("notifications_enabled", true));
        switchReservationUpdates.setChecked(prefs.getBoolean("reservation_updates", true));
        switchNewReservations.setChecked(prefs.getBoolean("new_reservations", true));
    }

    private void setupListeners() {
        SharedPreferences prefs = getSharedPreferences("notification_prefs", MODE_PRIVATE);

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("notifications_enabled", isChecked).apply();
            // Enable/disable other switches based on master switch
            switchReservationUpdates.setEnabled(isChecked);
            switchNewReservations.setEnabled(isChecked);
        });

        switchReservationUpdates.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("reservation_updates", isChecked).apply();
        });

        switchNewReservations.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("new_reservations", isChecked).apply();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
