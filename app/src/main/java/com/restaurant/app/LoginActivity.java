package com.restaurant.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import com.restaurant.app.api.RestaurantApiService;
import com.restaurant.app.api.RetrofitClient;
import com.restaurant.app.api.User;
import com.restaurant.app.api.UserListResponse;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private TextInputEditText etPassword;
    private final String STUDENT_ID = "student_10933506";
    
    private final ExecutorService networkExecutor = Executors.newFixedThreadPool(1);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegisterLink = findViewById(R.id.tvRegisterLink);

        // Login button click listener
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = Objects.requireNonNull(etPassword.getText()).toString().trim();

            if (validateInput(email, password)) {
                authenticateUser(email, password);
            }
        });

        // Register link click listener
        tvRegisterLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void authenticateUser(String email, String password) {
        RestaurantApiService apiService = RetrofitClient.getApiService();

        networkExecutor.execute(() -> {
            Call<UserListResponse> call = apiService.readAllUsers(STUDENT_ID);
            call.enqueue(new Callback<UserListResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserListResponse> call, @NonNull Response<UserListResponse> response) {
                    mainHandler.post(() -> {
                        if (response.isSuccessful() && response.body() != null) {
                            List<User> users = response.body().getUsers();
                            boolean authenticated = false;

                            for (User user : users) {
                                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                                    authenticated = true;
                                    saveUserSession(user);
                                    break;
                                }
                            }

                            if (authenticated) {
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(@NonNull Call<UserListResponse> call, @NonNull Throwable t) {
                    mainHandler.post(() -> {
                        Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    });
                }
            });
        });
    }

    private void saveUserSession(User user) {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", user.getUsername());
        editor.putString("email", user.getEmail());
        editor.putString("usertype", user.getUserType());
        editor.putBoolean("is_logged_in", true);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkExecutor.shutdown();
    }
}