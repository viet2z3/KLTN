package com.example.kltn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;

public class LearningStreakActivity extends AppCompatActivity {

    private Button btnContinueLearning;
    private String userEmail;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_streak);
        
        // Get user email from intent
        userEmail = getIntent().getStringExtra("user_email");
        
        // Initialize views
        initViews();
        
        // Setup click listeners
        setupClickListeners();
    }
    
    private void initViews() {
        btnContinueLearning = findViewById(R.id.btn_continue_learning);
    }
    
    private void setupClickListeners() {
        btnContinueLearning.setOnClickListener(v -> {
            // Chuyển về StudentHomeActivity
            Intent intent = new Intent(this, StudentHomeActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
            // Đóng activity hiện tại để không stack lại
            finish();
        });
    }

} 