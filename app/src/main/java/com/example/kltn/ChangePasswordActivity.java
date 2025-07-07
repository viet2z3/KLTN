package com.example.kltn;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTitle;
    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private Button btnChangePassword;
    private Button btnBack;
    
    // Current password (in real app, this would be stored securely)
    private String currentPassword = "password123";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        
        initializeViews();
        setupEventHandlers();
    }
    
    private void initializeViews() {
        tvTitle = findViewById(R.id.tv_change_password_title);
        etOldPassword = findViewById(R.id.et_current_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnBack = findViewById(R.id.btn_back);
        
        // Set title
        tvTitle.setText(R.string.change_password_title);
    }
    
    private void setupEventHandlers() {
        btnChangePassword.setOnClickListener(v -> changePassword());
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void changePassword() {
        String oldPassword = etOldPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        
        // Clear previous errors
        etOldPassword.setError(null);
        etNewPassword.setError(null);
        etConfirmPassword.setError(null);
        
        // Validate old password
        if (TextUtils.isEmpty(oldPassword)) {
            etOldPassword.setError("Please enter your current password");
            etOldPassword.requestFocus();
            return;
        }
        
        if (!oldPassword.equals(currentPassword)) {
            etOldPassword.setError(getString(R.string.error_old_password_incorrect));
            etOldPassword.requestFocus();
            return;
        }
        
        // Validate new password
        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("Please enter a new password");
            etNewPassword.requestFocus();
            return;
        }
        
        if (newPassword.length() < 6) {
            etNewPassword.setError("Password must be at least 6 characters");
            etNewPassword.requestFocus();
            return;
        }
        
        if (newPassword.equals(oldPassword)) {
            etNewPassword.setError("New password must be different from current password");
            etNewPassword.requestFocus();
            return;
        }
        
        // Validate confirm password
        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError("Please confirm your new password");
            etConfirmPassword.requestFocus();
            return;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            etConfirmPassword.setError(getString(R.string.error_passwords_dont_match));
            etConfirmPassword.requestFocus();
            return;
        }
        
        // Simulate password change
        // In a real app, this would make an API call to change the password
        performPasswordChange(newPassword);
    }
    
    private void performPasswordChange(String newPassword) {
        // Simulate API call delay
        btnChangePassword.setEnabled(false);
        btnChangePassword.setText(R.string.loading);
        
        // Simulate network delay
        new android.os.Handler().postDelayed(() -> {
            // Update current password
            currentPassword = newPassword;
            
            // Show success message
            Toast.makeText(this, R.string.password_change_success, Toast.LENGTH_LONG).show();
            
            // Clear form
            etOldPassword.setText("");
            etNewPassword.setText("");
            etConfirmPassword.setText("");
            
            // Reset button
            btnChangePassword.setEnabled(true);
            btnChangePassword.setText(R.string.btn_change_password);
            
            // Close activity
            finish();
        }, 1500);
    }
    
    @Override
    public void onBackPressed() {
        // Check if any field has been modified
        if (!TextUtils.isEmpty(etOldPassword.getText()) ||
            !TextUtils.isEmpty(etNewPassword.getText()) ||
            !TextUtils.isEmpty(etConfirmPassword.getText())) {
            
            new android.app.AlertDialog.Builder(this)
                .setTitle("Discard Changes")
                .setMessage("Are you sure you want to discard your changes?")
                .setPositiveButton("Yes", (dialog, which) -> super.onBackPressed())
                .setNegativeButton("No", null)
                .show();
        } else {
            super.onBackPressed();
        }
    }
} 