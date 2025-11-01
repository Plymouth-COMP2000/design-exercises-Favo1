//package com.restaurant.restaurantmanagementstaff;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class RegisterActivity extends AppCompatActivity {
//
//    private EditText etName, etEmail, etPassword, etRetypePassword;
//    private Button btnRegister;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        // Initialize views
//        etName = findViewById(R.id.etName);
//        etEmail = findViewById(R.id.etEmail);
//        etPassword = findViewById(R.id.etPassword);
//        etRetypePassword = findViewById(R.id.etRetypePassword);
//        btnRegister = findViewById(R.id.btnRegister);
//
//        // Register button click listener
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = etName.getText().toString().trim();
//                String email = etEmail.getText().toString().trim();
//                String password = etPassword.getText().toString().trim();
//                String retypePassword = etRetypePassword.getText().toString().trim();
//
//                if (validateInput(name, email, password, retypePassword)) {
//                    // TODO: Implement registration logic
//                    Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
//
//                    // Navigate back to login
//                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });
//    }
//
//    private boolean validateInput(String name, String email, String password, String retypePassword) {
//        if (name.isEmpty()) {
//            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (email.isEmpty()) {
//            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (password.isEmpty()) {
//            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (password.length() < 6) {
//            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (!password.equals(retypePassword)) {
//            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        return true;
//    }
//}
