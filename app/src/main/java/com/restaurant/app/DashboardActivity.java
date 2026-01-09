package com.restaurant.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.restaurant.app.data.SampleDataHelper;


public class DashboardActivity extends AppCompatActivity {

    private String userType;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get user info from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        userType = prefs.getString("usertype", "guest");
        userName = prefs.getString("username", "User");

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Setup toolbar UI
        TextView tvDashboardTitle = findViewById(R.id.tvDashboardTitle);
        TextView tvUserWelcome = findViewById(R.id.tvUserWelcome);
        ImageView ivUserProfile = findViewById(R.id.ivUserProfile);
        
        tvDashboardTitle.setText(userType.equals("staff") ? "Staff Dashboard" : "Guest Dashboard");
        tvUserWelcome.setText("Welcome, " + userName);
        
        // Profile icon click - show menu with options
        ivUserProfile.setOnClickListener(v -> {
            showProfileMenu();
        });

        // Add sample menu items on first launch
        SampleDataHelper.addSampleMenuItems(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MenuFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // No 3-dot menu needed - using profile icon instead
        return true;
    }

    private void showProfileMenu() {
        // Build menu items based on user type
        String[] menuItems;
        if (userType.equals("staff")) {
            menuItems = new String[]{"Profile", "Statistics", "Logout"};
        } else {
            menuItems = new String[]{"Profile", "Logout"};
        }

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Menu")
                .setItems(menuItems, (dialog, which) -> {
                    if (userType.equals("staff")) {
                        switch (which) {
                            case 0: // Profile
                                startActivity(new Intent(this, SettingsActivity.class));
                                break;
                            case 1: // Statistics
                                startActivity(new Intent(this, StatisticsActivity.class));
                                break;
                            case 2: // Logout
                                logout();
                                break;
                        }
                    } else {
                        switch (which) {
                            case 0: // Profile
                                startActivity(new Intent(this, SettingsActivity.class));
                                break;
                            case 1: // Logout
                                logout();
                                break;
                        }
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private final BottomNavigationView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_menu) {
                    selectedFragment = new MenuFragment();
                } else if (itemId == R.id.nav_tables) {
                    selectedFragment = new TablesFragment();
                } else if (itemId == R.id.nav_reservations) {
                    selectedFragment = new ReservationsFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                }
                return true;
            };

    public String getUserType() {
        return userType;
    }
}