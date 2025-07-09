package com.example.kltn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kltn.R;

import java.util.ArrayList;
import java.util.List;

public class TeacherDashboardActivity extends AppCompatActivity {

    private TextView tvWelcomeMessage, tvTotalStudents, tvTotalClasses;
    private Button btnManageStudents, btnAssignLesson, btnViewProgress, btnEvaluateStudents, btnProfile;
    
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("user_email");

        initViews();
        setupData();
        setupClickListeners();
    }

    private void initViews() {
        tvWelcomeMessage = findViewById(R.id.tv_welcome_message);
        tvTotalStudents = findViewById(R.id.tv_total_students);
        tvTotalClasses = findViewById(R.id.tv_total_classes);
        
        btnManageStudents = findViewById(R.id.btn_manage_students);
        btnAssignLesson = findViewById(R.id.btn_assign_lesson);
        btnViewProgress = findViewById(R.id.btn_view_progress);
        btnEvaluateStudents = findViewById(R.id.btn_evaluate_students);
        btnProfile = findViewById(R.id.btn_profile);
    }

    private void setupData() {
        // Set welcome message
        String teacherName = userEmail != null ? userEmail.split("@")[0] : "Teacher";
        tvWelcomeMessage.setText("Welcome back, " + teacherName + "!");
        
        // Set statistics
        tvTotalStudents.setText("45");
        tvTotalClasses.setText("8");
    }

    private void setupClickListeners() {
        // Action buttons
        btnManageStudents.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherDashboardActivity.this, ManageStudentsActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        btnAssignLesson.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherDashboardActivity.this, AssignLessonActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        btnViewProgress.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherDashboardActivity.this, ViewStudentProgressActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        btnEvaluateStudents.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherDashboardActivity.this, EvaluateStudentActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(TeacherDashboardActivity.this, ProfileUpdateActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });
    }
} 