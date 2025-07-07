package com.example.kltn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    
    // Test Result data class
    private static class TestResult {
        private String testName;
        private int score;
        private String date;
        private String scoreDetail;
        private String duration;
        
        public TestResult(String testName, int score, String date, String scoreDetail, String duration) {
            this.testName = testName;
            this.score = score;
            this.date = date;
            this.scoreDetail = scoreDetail;
            this.duration = duration;
        }
        
        public String getTestName() { return testName; }
        public int getScore() { return score; }
        public String getDate() { return date; }
        public String getScoreDetail() { return scoreDetail; }
        public String getDuration() { return duration; }
    }
    
    // Activity Item data class
    private static class ActivityItem {
        private String description;
        private String topic;
        private String timeAgo;
        private String status;
        
        public ActivityItem(String description, String topic, String timeAgo, String status) {
            this.description = description;
            this.topic = topic;
            this.timeAgo = timeAgo;
            this.status = status;
        }
        
        public String getDescription() { return description; }
        public String getTopic() { return topic; }
        public String getTimeAgo() { return timeAgo; }
        public String getStatus() { return status; }
    }
    
    // Test Result RecyclerView Adapter
    private static class TestResultAdapter extends RecyclerView.Adapter<TestResultAdapter.TestResultViewHolder> {
        private List<TestResult> testResults;
        
        public TestResultAdapter(List<TestResult> testResults) {
            this.testResults = testResults;
        }
        
        @NonNull
        @Override
        public TestResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_test_result, parent, false);
            return new TestResultViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull TestResultViewHolder holder, int position) {
            TestResult testResult = testResults.get(position);
            holder.bind(testResult);
        }
        
        @Override
        public int getItemCount() {
            return testResults.size();
        }
        
        class TestResultViewHolder extends RecyclerView.ViewHolder {
            private TextView tvTestName, tvTestDate, tvTestDuration, tvTestScore;
            
            public TestResultViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTestName = itemView.findViewById(R.id.tvTestName);
                tvTestDate = itemView.findViewById(R.id.tvTestDate);
                tvTestDuration = itemView.findViewById(R.id.tvTestDuration);
                tvTestScore = itemView.findViewById(R.id.tvTestScore);
            }
            
            public void bind(TestResult testResult) {
                tvTestName.setText(testResult.getTestName());
                tvTestDate.setText(testResult.getDate());
                tvTestDuration.setText("Duration: " + testResult.getDuration());
                tvTestScore.setText(testResult.getScore() + "%");
                
                // Set score color based on performance
                if (testResult.getScore() >= 90) {
                    tvTestScore.setTextColor(itemView.getContext().getColor(R.color.success_green));
                } else if (testResult.getScore() >= 80) {
                    tvTestScore.setTextColor(itemView.getContext().getColor(R.color.warning_orange));
                } else {
                    tvTestScore.setTextColor(itemView.getContext().getColor(R.color.error_red));
                }
            }
        }
    }
    
    // Activity RecyclerView Adapter
    private static class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
        private List<ActivityItem> activities;
        
        public ActivityAdapter(List<ActivityItem> activities) {
            this.activities = activities;
        }
        
        @NonNull
        @Override
        public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity_card, parent, false);
            return new ActivityViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
            ActivityItem activity = activities.get(position);
            holder.bind(activity);
        }
        
        @Override
        public int getItemCount() {
            return activities.size();
        }
        
        class ActivityViewHolder extends RecyclerView.ViewHolder {
            private TextView tvActivityDescription, tvActivityTime;
            
            public ActivityViewHolder(@NonNull View itemView) {
                super(itemView);
                tvActivityDescription = itemView.findViewById(R.id.tv_description);
                tvActivityTime = itemView.findViewById(R.id.tv_time_ago);
            }
            
            public void bind(ActivityItem activity) {
                tvActivityDescription.setText(activity.getDescription() + " - " + activity.getTopic() + " (" + activity.getStatus() + ")");
                tvActivityTime.setText(activity.getTimeAgo());
            }
        }
    }
} 