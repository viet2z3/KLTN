package com.example.kltn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;

public class AdminDashboardActivity extends AppCompatActivity {

    private TextView tvWelcomeMessage, tvSystemHealth, tvTotalUsers, tvTotalClasses;
    private Button btnManageUsers, btnManageRoles, btnManageClasses, btnManageContent, btnSystemReports;
    
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("user_email");

        initViews();
        setupData();
        setupClickListeners();
    }

    private void initViews() {
        tvWelcomeMessage = findViewById(R.id.tv_welcome_message);
        tvSystemHealth = findViewById(R.id.tv_system_status);
        tvTotalUsers = findViewById(R.id.tv_total_users);
        tvTotalClasses = findViewById(R.id.tv_total_classes);
        
        btnManageUsers = findViewById(R.id.btn_manage_users);
        btnManageRoles = findViewById(R.id.btn_manage_roles);
        btnManageClasses = findViewById(R.id.btn_manage_classes);
        btnManageContent = findViewById(R.id.btn_manage_content);
        btnSystemReports = findViewById(R.id.btn_system_reports);
    }

    private void setupData() {
        // Set welcome message
        tvWelcomeMessage.setText(getString(R.string.admin_welcome));
        
        // Set system health
        tvSystemHealth.setText("Good");
        
        // Set statistics
        tvTotalUsers.setText("156");
        tvTotalClasses.setText("24");
    }

    private void setupClickListeners() {
        // Management buttons
        btnManageUsers.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, ManageUsersActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        btnManageRoles.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, ManageRolesActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        btnManageClasses.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, ManageClassesActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        btnManageContent.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, ManageContentActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        btnSystemReports.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, SystemReportsActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });
    }
} 