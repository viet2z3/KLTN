package com.example.kltn.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;

public class ManageRolesActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTitle;
    private TextView tvRoleStudent, tvRoleTeacher, tvRoleAdmin;
    private CheckBox cbStudentCreateUsers, cbStudentManageClasses, cbStudentViewReports, cbStudentAssignLessons;
    private CheckBox cbTeacherCreateUsers, cbTeacherManageClasses, cbTeacherViewReports, cbTeacherAssignLessons;
    private CheckBox cbAdminCreateUsers, cbAdminManageClasses, cbAdminViewReports, cbAdminAssignLessons;
    private Button btnSaveRoles;
    private Button btnBack;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_roles);
        
        initializeViews();
        setupEventHandlers();
        loadRolePermissions();
    }
    
    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        tvRoleStudent = findViewById(R.id.tvRoleStudent);
        tvRoleTeacher = findViewById(R.id.tvRoleTeacher);
        tvRoleAdmin = findViewById(R.id.tvRoleAdmin);
        
        // Student permissions
        cbStudentCreateUsers = findViewById(R.id.cbStudentCreateUsers);
        cbStudentManageClasses = findViewById(R.id.cbStudentManageClasses);
        cbStudentViewReports = findViewById(R.id.cbStudentViewReports);
        cbStudentAssignLessons = findViewById(R.id.cbStudentAssignLessons);
        
        // Teacher permissions
        cbTeacherCreateUsers = findViewById(R.id.cbTeacherCreateUsers);
        cbTeacherManageClasses = findViewById(R.id.cbTeacherManageClasses);
        cbTeacherViewReports = findViewById(R.id.cbTeacherViewReports);
        cbTeacherAssignLessons = findViewById(R.id.cbTeacherAssignLessons);
        
        // Admin permissions
        cbAdminCreateUsers = findViewById(R.id.cbAdminCreateUsers);
        cbAdminManageClasses = findViewById(R.id.cbAdminManageClasses);
        cbAdminViewReports = findViewById(R.id.cbAdminViewReports);
        cbAdminAssignLessons = findViewById(R.id.cbAdminAssignLessons);
        
        btnSaveRoles = findViewById(R.id.btnSaveRoles);
        btnBack = findViewById(R.id.btnBack);
        
        // Set title
        tvTitle.setText(R.string.manage_roles_title);
    }
    
    private void setupEventHandlers() {
        btnSaveRoles.setOnClickListener(v -> saveRoles());
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void loadRolePermissions() {
        // Load default permissions for each role
        
        // Student permissions (minimal)
        cbStudentCreateUsers.setChecked(false);
        cbStudentManageClasses.setChecked(false);
        cbStudentViewReports.setChecked(false);
        cbStudentAssignLessons.setChecked(false);
        
        // Teacher permissions (moderate)
        cbTeacherCreateUsers.setChecked(false);
        cbTeacherManageClasses.setChecked(false);
        cbTeacherViewReports.setChecked(true);
        cbTeacherAssignLessons.setChecked(true);
        
        // Admin permissions (full)
        cbAdminCreateUsers.setChecked(true);
        cbAdminManageClasses.setChecked(true);
        cbAdminViewReports.setChecked(true);
        cbAdminAssignLessons.setChecked(true);
    }
    
    private void saveRoles() {
        // Simulate saving role permissions
        btnSaveRoles.setEnabled(false);
        btnSaveRoles.setText(R.string.loading);
        
        // Simulate API call delay
        new android.os.Handler().postDelayed(() -> {
            // Show success message
            Toast.makeText(this, R.string.btn_save_roles, Toast.LENGTH_LONG).show();
            
            // Reset button
            btnSaveRoles.setEnabled(true);
            btnSaveRoles.setText(R.string.btn_save_roles);
            
            // Close activity
            finish();
        }, 1500);
    }
} 