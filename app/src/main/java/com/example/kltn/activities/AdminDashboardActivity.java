package com.example.kltn.activities;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.widget.LinearLayout;

public class AdminDashboardActivity extends AppCompatActivity {

    private LinearLayout actionManageUsers, actionManageClasses, actionManageContent, actionManageReports, actionSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_admin);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                // Stay on this activity
                return true;
            } else if (id == R.id.nav_users) {
                Intent intent = new Intent(this, ManageUsersActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_classes) {
                Intent intent = new Intent(this, ManageClassesActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_content) {
                Intent intent = new Intent(this, ManageContentActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
        bottomNav.setSelectedItemId(R.id.nav_home);

        mapViews();
        setupQuickActionClicks();
    }

    private void mapViews() {
        actionManageUsers = findViewById(R.id.action_manage_users);
        actionManageClasses = findViewById(R.id.action_manage_classes);

        actionManageContent = findViewById(R.id.action_manage_content);
        actionManageReports = findViewById(R.id.action_manage_reports);
        actionSettings = findViewById(R.id.action_settings);
    }

    private void setupQuickActionClicks() {
        actionManageUsers.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageUsersActivity.class));
        });
        actionManageClasses.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageClassesActivity.class));
        });
        actionManageContent.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageContentActivity.class));
        });
        actionManageReports.setOnClickListener(v -> {
            startActivity(new Intent(this, SystemReportsActivity.class));
        });
        actionSettings.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });
    }


} 