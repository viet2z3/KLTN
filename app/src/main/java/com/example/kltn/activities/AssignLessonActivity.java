package com.example.kltn.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssignLessonActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTitle;
    private EditText etLessonTitle;
    private EditText etLessonDescription;
    private RadioGroup radioGroupLessonType;
    private RadioButton radioButtonFlashcards;
    private RadioButton radioButtonVideo;
    private RadioButton radioButtonTest;
    private TextView tvDueDate;
    private Button btnSelectDueDate;
    private Button btnAssign;
    private Button btnBack;
    
    // Date picker
    private Calendar selectedDate;
    private SimpleDateFormat dateFormat;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_lesson);
        
        initializeViews();
        setupDatePicker();
        setupEventHandlers();
    }
    
    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        etLessonTitle = findViewById(R.id.etLessonTitle);
        etLessonDescription = findViewById(R.id.etLessonDescription);
        radioGroupLessonType = findViewById(R.id.radioGroupLessonType);
        radioButtonFlashcards = findViewById(R.id.radioButtonFlashcards);
        radioButtonVideo = findViewById(R.id.radioButtonVideo);
        radioButtonTest = findViewById(R.id.radioButtonTest);
        tvDueDate = findViewById(R.id.tvDueDate);
        btnSelectDueDate = findViewById(R.id.btnSelectDueDate);
        btnAssign = findViewById(R.id.btnAssign);
        btnBack = findViewById(R.id.btnBack);
        
        // Set title
        tvTitle.setText(R.string.assign_lesson_title);
        
        // Set default lesson type
        radioButtonFlashcards.setChecked(true);
    }
    
    private void setupDatePicker() {
        selectedDate = Calendar.getInstance();
        selectedDate.add(Calendar.DAY_OF_MONTH, 7); // Default to 1 week from now
        dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        updateDueDateDisplay();
    }
    
    private void setupEventHandlers() {
        btnSelectDueDate.setOnClickListener(v -> showDatePicker());
        btnAssign.setOnClickListener(v -> assignLesson());
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                selectedDate.set(year, month, dayOfMonth);
                updateDueDateDisplay();
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        
        // Set minimum date to today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    
    private void updateDueDateDisplay() {
        tvDueDate.setText(dateFormat.format(selectedDate.getTime()));
    }
    
    private void assignLesson() {
        String lessonTitle = etLessonTitle.getText().toString().trim();
        String lessonDescription = etLessonDescription.getText().toString().trim();
        
        // Clear previous errors
        etLessonTitle.setError(null);
        etLessonDescription.setError(null);
        
        // Validate lesson title
        if (TextUtils.isEmpty(lessonTitle)) {
            etLessonTitle.setError("Please enter a lesson title");
            etLessonTitle.requestFocus();
            return;
        }
        
        if (lessonTitle.length() < 3) {
            etLessonTitle.setError("Lesson title must be at least 3 characters");
            etLessonTitle.requestFocus();
            return;
        }
        
        // Validate lesson description
        if (TextUtils.isEmpty(lessonDescription)) {
            etLessonDescription.setError("Please enter a lesson description");
            etLessonDescription.requestFocus();
            return;
        }
        
        if (lessonDescription.length() < 10) {
            etLessonDescription.setError("Lesson description must be at least 10 characters");
            etLessonDescription.requestFocus();
            return;
        }
        
        // Get selected lesson type
        String lessonType = "";
        int selectedId = radioGroupLessonType.getCheckedRadioButtonId();
        if (selectedId == R.id.radioButtonFlashcards) {
            lessonType = "Flashcards";
        } else if (selectedId == R.id.radioButtonVideo) {
            lessonType = "Video";
        } else if (selectedId == R.id.radioButtonTest) {
            lessonType = "Test";
        }
        
        if (TextUtils.isEmpty(lessonType)) {
            Toast.makeText(this, "Please select a lesson type", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Validate due date
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        
        if (selectedDate.before(today)) {
            Toast.makeText(this, "Due date cannot be in the past", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Assign lesson
        performLessonAssignment(lessonTitle, lessonDescription, lessonType);
    }
    
    private void performLessonAssignment(String title, String description, String type) {
        // Simulate API call delay
        btnAssign.setEnabled(false);
        btnAssign.setText(R.string.loading);
        
        // Simulate network delay
        new android.os.Handler().postDelayed(() -> {
            // Show success message
            Toast.makeText(this, R.string.lesson_assigned_success, Toast.LENGTH_LONG).show();
            
            // Reset form
            etLessonTitle.setText("");
            etLessonDescription.setText("");
            radioButtonFlashcards.setChecked(true);
            setupDatePicker(); // Reset to default date
            
            // Reset button
            btnAssign.setEnabled(true);
            btnAssign.setText(R.string.btn_assign);
            
            // Close activity
            finish();
        }, 1500);
    }
    
    @Override
    public void onBackPressed() {
        // Check if any field has been modified
        if (!TextUtils.isEmpty(etLessonTitle.getText()) ||
            !TextUtils.isEmpty(etLessonDescription.getText())) {
            
            new android.app.AlertDialog.Builder(this)
                .setTitle("Discard Changes")
                .setMessage("Are you sure you want to discard your changes?")
                .setPositiveButton("Yes", (dialog, which) -> super.onBackPressed())
                .setNegativeButton("No", null)
                .show();
        } else {
            super.onBackPressed();
        }
    }
} 