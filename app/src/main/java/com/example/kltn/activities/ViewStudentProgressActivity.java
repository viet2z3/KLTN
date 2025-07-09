package com.example.kltn.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kltn.R;
import com.example.kltn.models.TestResult;
import com.example.kltn.models.ActivityItem;
import com.example.kltn.adapters.TestResultAdapter;
import com.example.kltn.adapters.ActivityAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentProgressActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTitle, tvStudentName, tvStudentClass;
    private TextView tvCompletionPercentage, tvAverageScore;
    private RecyclerView rvTestResults, rvRecentActivities;
    
    // Data
    private String studentName = "John Doe";
    private String studentClass = "English 101";
    private int completionPercentage = 75;
    private int averageScore = 85;
    private List<TestResult> testResults;
    private List<ActivityItem> recentActivities;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_progress);
        
        // Get student data from intent
        studentName = getIntent().getStringExtra("student_name");
        if (studentName == null) {
            studentName = "John Doe";
        }
        
        initializeViews();
        setupData();
        setupRecyclerViews();
        displayStudentData();
    }
    
    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        tvStudentName = findViewById(R.id.tvStudentName);
        tvStudentClass = findViewById(R.id.tvStudentClass);
        tvCompletionPercentage = findViewById(R.id.tvCompletionPercentage);
        tvAverageScore = findViewById(R.id.tvAverageScore);
        rvTestResults = findViewById(R.id.rvTestResults);
        rvRecentActivities = findViewById(R.id.rvRecentActivities);
    }
    
    private void setupData() {
        // Setup test results
        testResults = new ArrayList<>();
        testResults.add(new TestResult("Vocabulary Test - Animals", 95, "December 15, 2024", "19/20", "15 minutes"));
        testResults.add(new TestResult("Grammar Quiz - Present Simple", 88, "December 12, 2024", "17/20", "20 minutes"));
        testResults.add(new TestResult("Reading Comprehension", 92, "December 10, 2024", "18/20", "25 minutes"));
        testResults.add(new TestResult("Listening Test - Colors", 85, "December 8, 2024", "17/20", "10 minutes"));
        
        // Setup recent activities
        recentActivities = new ArrayList<>();
        recentActivities.add(new ActivityItem("Completed Vocabulary Lesson", "Colors", "2 hours ago", "Completed"));
        recentActivities.add(new ActivityItem("Watched Video Lesson", "Animal Names", "1 day ago", "Completed"));
        recentActivities.add(new ActivityItem("Completed Exercise", "Numbers 1-10", "2 days ago", "Completed"));
        recentActivities.add(new ActivityItem("Earned Badge", "First Lesson", "3 days ago", "Earned"));
        recentActivities.add(new ActivityItem("Completed Test", "Basic Greetings", "1 week ago", "Completed"));
    }
    
    private void setupRecyclerViews() {
        // Setup test results recycler view
        TestResultAdapter testAdapter = new TestResultAdapter(testResults);
        rvTestResults.setLayoutManager(new LinearLayoutManager(this));
        rvTestResults.setAdapter(testAdapter);
        
        // Setup recent activities recycler view
        ActivityAdapter activityAdapter = new ActivityAdapter(recentActivities);
        rvRecentActivities.setLayoutManager(new LinearLayoutManager(this));
        rvRecentActivities.setAdapter(activityAdapter);
    }
    
    private void displayStudentData() {
        tvStudentName.setText("Student Name: " + studentName);
        tvStudentClass.setText("Class: " + studentClass);
        tvCompletionPercentage.setText(completionPercentage + "%");
        tvAverageScore.setText(String.valueOf(averageScore));
    }
} 