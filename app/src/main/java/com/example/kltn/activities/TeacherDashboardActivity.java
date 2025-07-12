package com.example.kltn.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.widget.Toast;
import android.content.Intent;
import com.example.kltn.activities.ManageStudentsActivity;
import com.example.kltn.activities.ProfileDetail;
import com.example.kltn.activities.SettingsActivity;
import com.example.kltn.activities.AssignLessonActivity;
import com.example.kltn.activities.ViewStudentProgressActivity;
import com.example.kltn.activities.EvaluateStudentActivity;


import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;


public class TeacherDashboardActivity extends AppCompatActivity {

    private void setupDashboardMenu(View item, int iconRes, int titleRes, int subtitleRes) {
        ImageView icon = item.findViewById(R.id.menu_icon);
        TextView title = item.findViewById(R.id.menu_title);
        TextView subtitle = item.findViewById(R.id.menu_subtitle);
        icon.setImageResource(iconRes);
        title.setText(titleRes);
        subtitle.setText(subtitleRes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        setupDashboardMenu(findViewById(R.id.itemManageStudents), R.drawable.tc_class, R.string.manage_students, R.string.manage_students_sub);
        setupDashboardMenu(findViewById(R.id.itemAssignLessons), R.drawable.tc_asign, R.string.assign_lessons, R.string.assign_lessons_sub);
        setupDashboardMenu(findViewById(R.id.itemViewProgress), R.drawable.tc_theodoi, R.string.view_progress, R.string.view_progress_sub);
        setupDashboardMenu(findViewById(R.id.itemEvaluateStudents), R.drawable.tc_danhgia, R.string.evaluate_students, R.string.evaluate_students_sub);

        // Handle bottom navigation clicks
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                // 1. Ấn Home: vẫn ở TeacherDashboardActivity (không làm gì)
                return true;
            } else if (id == R.id.nav_classes) {
                // 2. Ấn Classes: chuyển sang ManageStudentsActivity
                Intent intent = new Intent(this, ManageStudentsActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_profile) {
                // 3. Ấn Profile: chuyển sang ProfileDetail
                Intent intent = new Intent(this, ProfileDetail.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_setting) {
                // 4. Ấn Setting: chuyển sang SettingsActivity
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        // Handle menu item clicks
        // 1. Manage Students
        View itemManageStudents = findViewById(R.id.itemManageStudents);
        itemManageStudents.setOnClickListener(v -> {
            Intent intent = new Intent(this, ManageStudentsActivity.class);
            startActivity(intent);
        });

        // 2. Assign Lessons
        View itemAssignLessons = findViewById(R.id.itemAssignLessons);
        itemAssignLessons.setOnClickListener(v -> {
            Intent intent = new Intent(this, AssignLessonActivity.class);
            startActivity(intent);
        });

        // 3. View Progress
        View itemViewProgress = findViewById(R.id.itemViewProgress);
        itemViewProgress.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewStudentProgressActivity.class);
            startActivity(intent);
        });

        // 4. Evaluate Students
        View itemEvaluateStudents = findViewById(R.id.itemEvaluateStudents);
        itemEvaluateStudents.setOnClickListener(v -> {
            Intent intent = new Intent(this, EvaluateStudentActivity.class);
            startActivity(intent);
        });
    }

} 