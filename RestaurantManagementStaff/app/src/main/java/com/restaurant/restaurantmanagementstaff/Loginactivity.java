//package com.restaurant.restaurantmanagementstaff;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private EditText etEmail, etPassword;
//    private Button btnLogin;
//    private TextView tvRegisterLink;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        // Initialize views
//        etEmail = findViewById(R.id.etEmail);
//        etPassword = findViewById(R.id.etPassword);
//        btnLogin = findViewById(R.id.btnLogin);
//        tvRegisterLink = findViewById(R.id.tvRegisterLink);
//
//        // Login button click listener
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = etEmail.getText().toString().trim();
//                String password = etPassword.getText().toString().trim();
//
//                if (validateInput(email, password)) {
//                    // TODO: Implement authentication logic
//                    // For now, just navigate to Dashboard
//                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });
//
//        // Register link click listener
//        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private boolean validateInput(String email, String password) {
//        if (email.isEmpty()) {
//            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (password.isEmpty()) {
//            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        return true;
//    }
//}