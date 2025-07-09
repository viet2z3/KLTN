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
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    // private Button  btnBack; // Không có Button back, dùng ImageView
    private ProgressBar progressBar;
    private TextView btnResetPassword; // btn_reset_password là TextView
    // private TextView tvSuccessMessage; // Không có tv_success_message trong layout
    private ImageView btnBack; // ImageView back
    private View sendLinkLayout; // LinearLayout chứa nút gửi link

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.r6b37qz1lfx3); // EditText Email
        btnResetPassword = findViewById(R.id.btn_reset_password); // TextView Send Link
        btnBack = findViewById(R.id.back); // ImageView Back (trong FrameLayout mới)
        progressBar = findViewById(R.id.progress_bar);
        sendLinkLayout = findViewById(R.id.r8zwox2wry25); // LinearLayout chứa nút gửi link
        // Không cần thay đổi logic, chỉ đảm bảo đúng id và layout mới
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        sendLinkLayout.setOnClickListener(v -> attemptSendResetLink());
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
        btnResetPassword.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        sendLinkLayout.setEnabled(!show);
        btnResetPassword.setEnabled(!show);
        etEmail.setEnabled(!show);
        sendLinkLayout.setAlpha(show ? 0.5f : 1f);
    }

    private void showSuccessMessage() {
        btnResetPassword.setText(R.string.forgot_password_success);
        sendLinkLayout.setEnabled(false);
        btnResetPassword.setEnabled(false);
        etEmail.setEnabled(false);
        btnResetPassword.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getString(R.string.forgot_password_success), Toast.LENGTH_LONG).show();
    }
} 