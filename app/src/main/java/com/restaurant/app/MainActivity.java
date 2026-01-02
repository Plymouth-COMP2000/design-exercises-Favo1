package com.restaurant.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge display
        setContentView(R.layout.activity_main); // Set the splash screen layout

        // Apply window insets to handle system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Use a Handler to delay the navigation to the next activity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            /*
             * TODO: Implement actual user session check here.
             * In a real application, you would check if a user is already logged in
             * (e.g., by checking SharedPreferences for a stored token or user ID).
             *
             * Example logic (commented out for now):
             * boolean isLoggedIn = checkLoginStatus(); // A method to check login status
             * if (isLoggedIn) {
             *     // If logged in, go directly to Dashboard
             *     Intent mainIntent = new Intent(MainActivity.this, DashboardActivity.class);
             *     startActivity(mainIntent);
             * } else {
             *     // If not logged in, go to Login
             *     Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
             *     startActivity(loginIntent);
             * }
             */

            //always navigate to LoginActivity as session management is not yet implemented.
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);

            // Finish MainActivity so the user cannot navigate back to the splash screen
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

    // Placeholder method for checking user login status
    // This method would contain the logic to determine if a user has an active session.
    private boolean checkLoginStatus() {
        // Example: Check SharedPreferences for a login token
        // SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        // return prefs.getString("auth_token", null) != null;
        return false; // Currently always returns false, directing to LoginActivity
    }
}