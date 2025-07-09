package com.example.kltn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;

public class LoginActivity extends AppCompatActivity {

    // UI Components
    private EditText etEmail, etPassword;
    private ProgressBar progressBar;
    private TextView tvForgotPassword,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        initViews();
        setupClickListeners();
        setupBackPressHandler();
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progress_bar);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
    }

    private void setupClickListeners() {
        // Login button click listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        // Forgot password click listener
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupBackPressHandler() {
        // Handle back press for Android API 33+
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Prevent going back from login screen
                moveTaskToBack(true);
            }
        });
    }

    private void attemptLogin() {
        // Get input values
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Simple validation
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            return;
        }

        // Show loading
        showLoading(true);

        // Simulate login process
        btnLogin.postDelayed(new Runnable() {
            @Override
            public void run() {
                // For demo purposes, accept any valid email/password combination
                if (isValidCredentials(email, password)) {
                    showLoading(false);
                    navigateToHome(email);
                } else {
                    showLoading(false);
                    Toast.makeText(LoginActivity.this,
                            "Login failed. Please check your credentials.",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, 1500);
    }

    private boolean isValidCredentials(String email, String password) {
        // Demo credentials - in real app, this would check against backend
        if (email.contains("student")) {
            return password.length() >= 6;
        } else if (email.contains("teacher")) {
            return password.length() >= 6;
        } else if (email.contains("admin")) {
            return password.length() >= 6;
        }
        return false;
    }

    private void navigateToHome(String email) {
        Intent intent;

        if (email.contains("student")) {
            intent = new Intent(LoginActivity.this, StudentHomeActivity.class);
        } else if (email.contains("teacher")) {
            intent = new Intent(LoginActivity.this, TeacherDashboardActivity.class);
        } else if (email.contains("admin")) {
            intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
        } else {
            // Default to student home
            intent = new Intent(LoginActivity.this, StudentHomeActivity.class);
        }

        intent.putExtra("user_email", email);
        startActivity(intent);
        finish(); // Close login activity
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(View.GONE);
        btnLogin.setEnabled(!show);
        etEmail.setEnabled(!show);
        etPassword.setEnabled(!show);
    }
}