package com.example.kltn.activities;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.widget.LinearLayout;

public class AdminDashboardActivity extends AppCompatActivity {

    private LinearLayout actionManageUsers, actionManageClasses, actionManageContent, actionSettings, actionManageCourses;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        userId = getIntent().getStringExtra("user_id");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_admin);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                // Stay on this activity
                return true;
            } else if (id == R.id.nav_users) {
                Intent intent = new Intent(this, ManageUsersActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_classes) {
                Intent intent = new Intent(this, ManageClassesActivity.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_content) {
                Intent intent = new Intent(this, ManageContentActivity.class);
                intent.putExtra("user_id", userId);
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
        actionSettings = findViewById(R.id.action_settings);
        actionManageCourses = findViewById(R.id.action_manage_courses);
    }

    private void setupQuickActionClicks() {
        actionManageUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageUsersActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });
        actionManageClasses.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageClassesActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });
        actionManageContent.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageContentActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });
        actionSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });
        actionManageCourses.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageCoursesActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });
    }


} 