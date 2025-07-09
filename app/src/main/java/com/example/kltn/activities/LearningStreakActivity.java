package com.example.kltn.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;

public class LearningStreakActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvCurrentStreak;
    private Button btnBack;
    
    // Streak data
    private int currentStreak = 12;
    private int longestStreak = 15;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_streak);
        
        initializeViews();
        setupEventHandlers();
        displayStreakData();
    }
    
    private void initializeViews() {
        tvCurrentStreak = findViewById(R.id.tv_current_streak);
        btnBack = findViewById(R.id.btn_back);
    }
    
    private void setupEventHandlers() {
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void displayStreakData() {
        // Display current streak
        tvCurrentStreak.setText(String.valueOf(currentStreak));
        
        // Update motivational message based on streak
        updateMotivationalMessage();
    }
    
    private void updateMotivationalMessage() {
        // The motivational message is already set in the layout
        // We can update it programmatically if needed
        if (currentStreak > 10) {
            // High streak - keep the default "Keep up the great work!"
        } else if (currentStreak > 5) {
            // Medium streak
        } else if (currentStreak > 0) {
            // Low streak
        } else {
            // No streak
        }
    }
    
    // Method to update streak (called from other activities)
    public void updateStreak(int newStreak) {
        this.currentStreak = newStreak;
        if (newStreak > longestStreak) {
            this.longestStreak = newStreak;
        }
        displayStreakData();
    }
} 