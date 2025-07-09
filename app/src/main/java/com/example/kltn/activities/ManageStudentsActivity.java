package com.example.kltn.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kltn.R;
import com.example.kltn.models.Student;
import com.example.kltn.adapters.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class ManageStudentsActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTitle, tvTotalStudents, tvActiveStudents;
    private EditText etSearch;
    private Button btnSearch, btnFilterAll, btnFilterActive, btnFilterInactive;
    private RecyclerView rvStudents;
    
    // Data
    private List<Student> allStudents;
    private List<Student> filteredStudents;
    private StudentAdapter studentAdapter;
    private String currentFilter = "all";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);
        
        initializeViews();
        setupStudentData();
        setupRecyclerView();
        setupEventHandlers();
        updateStatistics();
    }
    
    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnFilterAll = findViewById(R.id.btnFilterAll);
        btnFilterActive = findViewById(R.id.btnFilterActive);
        btnFilterInactive = findViewById(R.id.btnFilterInactive);
        tvTotalStudents = findViewById(R.id.tvTotalStudents);
        tvActiveStudents = findViewById(R.id.tvActiveStudents);
        rvStudents = findViewById(R.id.rvStudents);
    }
    
    private void setupStudentData() {
        allStudents = new ArrayList<>();
        allStudents.add(new Student("Alice Johnson", 8, "alice.johnson@example.com", "+1 234-567-8900", "English Class A", 85, 75, true));
        allStudents.add(new Student("Bob Smith", 7, "bob.smith@example.com", "+1 234-567-8901", "English Class B", 92, 88, true));
        allStudents.add(new Student("Charlie Brown", 9, "charlie.brown@example.com", "+1 234-567-8902", "English Class C", 78, 65, false));
        allStudents.add(new Student("Diana Prince", 6, "diana.prince@example.com", "+1 234-567-8903", "English Class A", 95, 92, true));
        
        filteredStudents = new ArrayList<>(allStudents);
    }
    
    private void setupRecyclerView() {
        studentAdapter = new StudentAdapter(filteredStudents, this::onStudentAction);
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setAdapter(studentAdapter);
    }
    
    private void setupEventHandlers() {
        btnSearch.setOnClickListener(v -> performSearch());
        btnFilterAll.setOnClickListener(v -> setFilter("all"));
        btnFilterActive.setOnClickListener(v -> setFilter("active"));
        btnFilterInactive.setOnClickListener(v -> setFilter("inactive"));
    }
    
    private void performSearch() {
        String searchTerm = etSearch.getText().toString().trim();
        applyFilters(searchTerm);
    }
    
    private void setFilter(String filter) {
        currentFilter = filter;
        
        // Update button states
        btnFilterAll.setBackground(getDrawable(R.drawable.button_secondary));
        btnFilterActive.setBackground(getDrawable(R.drawable.button_secondary));
        btnFilterInactive.setBackground(getDrawable(R.drawable.button_secondary));
        
        switch (filter) {
            case "all":
                btnFilterAll.setBackground(getDrawable(R.drawable.button_primary));
                break;
            case "active":
                btnFilterActive.setBackground(getDrawable(R.drawable.button_primary));
                break;
            case "inactive":
                btnFilterInactive.setBackground(getDrawable(R.drawable.button_primary));
                break;
        }
        
        applyFilters(etSearch.getText().toString().trim());
    }
    
    private void applyFilters(String searchTerm) {
        filteredStudents.clear();
        
        for (Student student : allStudents) {
            boolean matchesSearch = TextUtils.isEmpty(searchTerm) || 
                student.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                student.getEmail().toLowerCase().contains(searchTerm.toLowerCase()) ||
                student.getClassName().toLowerCase().contains(searchTerm.toLowerCase());
            
            boolean matchesFilter = true;
            switch (currentFilter) {
                case "active":
                    matchesFilter = student.isActive();
                    break;
                case "inactive":
                    matchesFilter = !student.isActive();
                    break;
            }
            
            if (matchesSearch && matchesFilter) {
                filteredStudents.add(student);
            }
        }
        
        studentAdapter.notifyDataSetChanged();
    }
    
    private void updateStatistics() {
        int totalStudents = allStudents.size();
        int activeStudents = 0;
        
        for (Student student : allStudents) {
            if (student.isActive()) {
                activeStudents++;
            }
        }
        
        tvTotalStudents.setText(String.valueOf(totalStudents));
        tvActiveStudents.setText(String.valueOf(activeStudents));
    }
    
    private void onStudentAction(Student student, String action) {
        switch (action) {
            case "edit":
                editStudent(student);
                break;
            case "view_progress":
                viewStudentProgress(student);
                break;
            case "delete":
                deleteStudent(student);
                break;
        }
    }
    
    private void editStudent(Student student) {
        Toast.makeText(this, "Edit student: " + student.getName(), Toast.LENGTH_SHORT).show();
    }
    
    private void viewStudentProgress(Student student) {
        Toast.makeText(this, "View progress for: " + student.getName(), Toast.LENGTH_SHORT).show();
    }
    
    private void deleteStudent(Student student) {
        new AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete " + student.getName() + "?")
            .setPositiveButton("Delete", (dialog, which) -> {
                allStudents.remove(student);
                applyFilters(etSearch.getText().toString().trim());
                updateStatistics();
                Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
} 