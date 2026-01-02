package com.restaurant.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.restaurant.app.R;
import com.restaurant.app.api.ApiResponse;
import com.restaurant.app.api.RestaurantApiService;
import com.restaurant.app.api.RetrofitClient;
import com.restaurant.app.api.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etRetypePassword, etFirstName, etLastName, etEmail, etContact;

    //student_id
    private final String STUDENT_ID = "student_10933506";

    // Use a fixed thread pool for network operations
    private final ExecutorService networkExecutor = Executors.newFixedThreadPool(1);
    private final Handler mainHandler = new Handler(Looper.getMainLooper()); // To post results back to UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components (IDs must exist in activity_register.xml)
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRetypePassword = findViewById(R.id.etRetypePassword);
        etFirstName = findViewById(R.id.etName); // Using existing name field
        etLastName = findViewById(R.id.etLastName); // New field for last name
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact); // New field for contact
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> registerUser());

        findViewById(R.id.tvLoginLink).setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String retypePassword = etRetypePassword.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String userType = "staff"; // Defaulting to staff for this prototype

        if (username.isEmpty() || password.isEmpty() || retypePassword.isEmpty() || firstName.isEmpty() || email.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(retypePassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(username, password, firstName, lastName, email, contact, userType);
        RestaurantApiService apiService = RetrofitClient.getApiService();

        networkExecutor.execute(() -> {
            Call<ApiResponse> call = apiService.createUser(STUDENT_ID, newUser);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                    mainHandler.post(() -> {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(RegisterActivity.this, "Registration successful: " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            String errorMsg = "Registration failed: " + response.code();
                            if (response.errorBody() != null) {
                                try {
                                    errorMsg += " - " + response.errorBody().string();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(RegisterActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                    mainHandler.post(() -> {
                        Toast.makeText(RegisterActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    });
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkExecutor.shutdown(); // Shut down the executor when activity is destroyed
    }
}