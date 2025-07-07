package com.example.kltn;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SystemReportsActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTitle;
    private EditText etStartDate, etEndDate;
    private Button btnGenerateReport;
    private TextView tvTotalUsers, tvActiveUsers;
    
    // Data
    private Calendar startDate;
    private Calendar endDate;
    private SimpleDateFormat dateFormat;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_reports);
        
        initializeViews();
        setupDateRange();
        setupEventHandlers();
        loadSystemData();
    }
    
    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        btnGenerateReport = findViewById(R.id.btnGenerateReport);
        tvTotalUsers = findViewById(R.id.tvTotalUsers);
        tvActiveUsers = findViewById(R.id.tvActiveUsers);
        
        // Initialize date format
        dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    }
    
    private void setupDateRange() {
        // Set default date range (last 30 days)
        endDate = Calendar.getInstance();
        startDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_MONTH, -30);
        
        updateDateFields();
    }
    
    private void updateDateFields() {
        etStartDate.setText(dateFormat.format(startDate.getTime()));
        etEndDate.setText(dateFormat.format(endDate.getTime()));
    }
    
    private void setupEventHandlers() {
        etStartDate.setOnClickListener(v -> showDatePicker(true));
        etEndDate.setOnClickListener(v -> showDatePicker(false));
        btnGenerateReport.setOnClickListener(v -> generateReport());
    }
    
    private void showDatePicker(boolean isStartDate) {
        Calendar currentDate = isStartDate ? startDate : endDate;
        
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                if (isStartDate) {
                    startDate.set(year, month, dayOfMonth);
                    etStartDate.setText(dateFormat.format(startDate.getTime()));
                } else {
                    endDate.set(year, month, dayOfMonth);
                    etEndDate.setText(dateFormat.format(endDate.getTime()));
                }
                
                // Validate date range
                if (endDate.before(startDate)) {
                    Toast.makeText(this, "End date must be after start date", Toast.LENGTH_SHORT).show();
                    if (isStartDate) {
                        endDate = (Calendar) startDate.clone();
                        endDate.add(Calendar.DAY_OF_MONTH, 1);
                        etEndDate.setText(dateFormat.format(endDate.getTime()));
                    } else {
                        startDate = (Calendar) endDate.clone();
                        startDate.add(Calendar.DAY_OF_MONTH, -1);
                        etStartDate.setText(dateFormat.format(startDate.getTime()));
                    }
                }
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    
    private void generateReport() {
        if (TextUtils.isEmpty(etStartDate.getText()) || TextUtils.isEmpty(etEndDate.getText())) {
            Toast.makeText(this, "Please select both start and end dates", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (endDate.before(startDate)) {
            Toast.makeText(this, "End date must be after start date", Toast.LENGTH_SHORT).show();
            return;
        }
        
        loadSystemData();
        showReportGeneratedMessage();
    }
    
    private void loadSystemData() {
        // Simulate loading data based on date range
        int daysDiff = (int) ((endDate.getTimeInMillis() - startDate.getTimeInMillis()) / (1000 * 60 * 60 * 24));
        
        // Calculate data based on date range
        int totalUsers = 156 + (daysDiff / 30) * 10;
        int activeUsers = 89 + (daysDiff / 30) * 5;
        
        // Update UI
        tvTotalUsers.setText(String.valueOf(totalUsers));
        tvActiveUsers.setText(String.valueOf(activeUsers));
    }
    
    private void showReportGeneratedMessage() {
        new AlertDialog.Builder(this)
            .setTitle("Report Generated")
            .setMessage("System report has been generated successfully for the selected date range.")
            .setPositiveButton("OK", null)
            .show();
    }
} 