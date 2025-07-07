package com.example.kltn;

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

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnResetPassword, btnBack;
    private ProgressBar progressBar;
    private TextView tvSuccessMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        btnBack = findViewById(R.id.btn_back);
        progressBar = findViewById(R.id.progress_bar);
        tvSuccessMessage = findViewById(R.id.tv_success_message);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnResetPassword.setOnClickListener(v -> attemptSendResetLink());
    }

    private void attemptSendResetLink() {
        // Clear previous errors
        etEmail.setError(null);

        // Get email
        String email = etEmail.getText().toString().trim();

        // Validate email
        if (!validateEmail(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return;
        }

        // Show loading
        showLoading(true);

        // Simulate sending reset link
        sendResetLink(email);
    }

    private boolean validateEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void sendResetLink(String email) {
        // Simulate network delay
        btnResetPassword.postDelayed(() -> {
            showLoading(false);
            showSuccessMessage();
        }, 2000);
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnResetPassword.setEnabled(!show);
        etEmail.setEnabled(!show);
    }

    private void showSuccessMessage() {
        tvSuccessMessage.setVisibility(View.VISIBLE);
        btnResetPassword.setVisibility(View.GONE);
        
        Toast.makeText(this, getString(R.string.forgot_password_success), Toast.LENGTH_LONG).show();
    }
} 