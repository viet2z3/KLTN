package com.example.kltn;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EvaluateStudentActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTitle;
    private TextView tvStudentName;
    private RatingBar ratingBarParticipation;
    private RatingBar ratingBarUnderstanding;
    private RatingBar ratingBarProgress;
    private EditText etComments;
    private Button btnSaveEvaluation;
    private Button btnBack;
    
    // Data
    private String studentName = "John Doe";
    private float participationRating = 3.0f;
    private float understandingRating = 3.0f;
    private float progressRating = 3.0f;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_student);
        
        // Get student data from intent
        studentName = getIntent().getStringExtra("student_name");
        if (studentName == null) {
            studentName = "John Doe";
        }
        
        initializeViews();
        setupEventHandlers();
        displayStudentData();
    }
    
    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        tvStudentName = findViewById(R.id.tvStudentName);
        ratingBarParticipation = findViewById(R.id.ratingParticipation);
        ratingBarUnderstanding = findViewById(R.id.ratingUnderstanding);
        ratingBarProgress = findViewById(R.id.ratingProgress);
        etComments = findViewById(R.id.etComments);
        btnSaveEvaluation = findViewById(R.id.btnSaveEvaluation);
        btnBack = findViewById(R.id.btnBack);
        
        // Set title
        tvTitle.setText(R.string.evaluate_student_title);
    }
    
    private void setupEventHandlers() {
        // Rating bar listeners
        ratingBarParticipation.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            participationRating = rating;
        });
        
        ratingBarUnderstanding.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            understandingRating = rating;
        });
        
        ratingBarProgress.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            progressRating = rating;
        });
        
        btnSaveEvaluation.setOnClickListener(v -> saveEvaluation());
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void displayStudentData() {
        tvStudentName.setText("Student: " + studentName);
        
        // Set initial values
        ratingBarParticipation.setRating(participationRating);
        ratingBarUnderstanding.setRating(understandingRating);
        ratingBarProgress.setRating(progressRating);
    }
    
    private boolean validateScore() {
        // Score validation is removed as per the new layout
        return true;
    }
    
    private void saveEvaluation() {
        // Clear previous errors
        etComments.setError(null);
        
        // Validate comments
        String comments = etComments.getText().toString().trim();
        if (TextUtils.isEmpty(comments)) {
            etComments.setError("Comments are required");
            etComments.requestFocus();
            return;
        }
        
        if (comments.length() < 10) {
            etComments.setError("Comments must be at least 10 characters");
            etComments.requestFocus();
            return;
        }
        
        // Validate ratings
        if (participationRating == 0 || understandingRating == 0 || progressRating == 0) {
            Toast.makeText(this, "Please rate all criteria", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Save evaluation
        performEvaluationSave();
    }
    
    private void performEvaluationSave() {
        // Simulate API call delay
        btnSaveEvaluation.setEnabled(false);
        btnSaveEvaluation.setText(R.string.loading);
        
        // Simulate network delay
        new android.os.Handler().postDelayed(() -> {
            // Show success message
            Toast.makeText(this, R.string.evaluation_saved, Toast.LENGTH_LONG).show();
            
            // Clear form
            etComments.setText("");
            ratingBarParticipation.setRating(3.0f);
            ratingBarUnderstanding.setRating(3.0f);
            ratingBarProgress.setRating(3.0f);
            participationRating = 3.0f;
            understandingRating = 3.0f;
            progressRating = 3.0f;
            
            // Reset button
            btnSaveEvaluation.setEnabled(true);
            btnSaveEvaluation.setText(R.string.btn_save_evaluation);
            
            // Close activity
            finish();
        }, 1500);
    }
    
    @Override
    public void onBackPressed() {
        // Check if any field has been modified
        String comments = etComments.getText().toString().trim();
        
        if (!TextUtils.isEmpty(comments) || 
            participationRating != 3.0f || 
            understandingRating != 3.0f || 
            progressRating != 3.0f) {
            
            new android.app.AlertDialog.Builder(this)
                .setTitle("Discard Changes")
                .setMessage("Are you sure you want to discard your evaluation?")
                .setPositiveButton("Yes", (dialog, which) -> super.onBackPressed())
                .setNegativeButton("No", null)
                .show();
        } else {
            super.onBackPressed();
        }
    }
} 