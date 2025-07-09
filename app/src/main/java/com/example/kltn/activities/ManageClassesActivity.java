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
import com.example.kltn.models.ClassInfo;
import com.example.kltn.adapters.ClassAdapter;

import java.util.ArrayList;
import java.util.List;

public class ManageClassesActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTitle, tvTotalClasses, tvActiveClasses;
    private EditText etSearch;
    private Button btnSearch, btnFilterAll, btnFilterActive, btnFilterInactive;
    private RecyclerView rvClasses;
    
    // Data
    private List<ClassInfo> allClasses;
    private List<ClassInfo> filteredClasses;
    private ClassAdapter classAdapter;
    private String currentFilter = "all";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_classes);
        
        initializeViews();
        setupClassData();
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
        tvTotalClasses = findViewById(R.id.tvTotalClasses);
        tvActiveClasses = findViewById(R.id.tvActiveClasses);
        rvClasses = findViewById(R.id.rvClasses);
    }
    
    private void setupClassData() {
        allClasses = new ArrayList<>();
        allClasses.add(new ClassInfo("English Class A", "Beginner level English for ages 6-7", 15, 12, "Diana Prince", true));
        allClasses.add(new ClassInfo("English Class B", "Intermediate level English for ages 8-9", 20, 18, "Eve Wilson", true));
        allClasses.add(new ClassInfo("English Class C", "Advanced level English for ages 9-10", 18, 15, "Frank Miller", false));
        allClasses.add(new ClassInfo("English Class D", "Mixed level English for ages 7-8", 16, 10, "Grace Lee", true));
        
        filteredClasses = new ArrayList<>(allClasses);
    }
    
    private void setupRecyclerView() {
        classAdapter = new ClassAdapter(filteredClasses, this::onClassAction);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));
        rvClasses.setAdapter(classAdapter);
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
        filteredClasses.clear();
        
        for (ClassInfo classInfo : allClasses) {
            boolean matchesSearch = TextUtils.isEmpty(searchTerm) || 
                classInfo.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                classInfo.getTeacherName().toLowerCase().contains(searchTerm.toLowerCase());
            
            boolean matchesFilter = true;
            switch (currentFilter) {
                case "active":
                    matchesFilter = classInfo.isActive();
                    break;
                case "inactive":
                    matchesFilter = !classInfo.isActive();
                    break;
            }
            
            if (matchesSearch && matchesFilter) {
                filteredClasses.add(classInfo);
            }
        }
        
        classAdapter.notifyDataSetChanged();
    }
    
    private void updateStatistics() {
        int totalClasses = allClasses.size();
        int activeClasses = 0;
        
        for (ClassInfo classInfo : allClasses) {
            if (classInfo.isActive()) {
                activeClasses++;
            }
        }
        
        tvTotalClasses.setText(String.valueOf(totalClasses));
        tvActiveClasses.setText(String.valueOf(activeClasses));
    }
    
    private void onClassAction(ClassInfo classInfo, String action) {
        switch (action) {
            case "edit":
                editClass(classInfo);
                break;
            case "delete":
                deleteClass(classInfo);
                break;
            case "toggle":
                toggleClassStatus(classInfo);
                break;
        }
    }
    
    private void editClass(ClassInfo classInfo) {
        Toast.makeText(this, "Edit class: " + classInfo.getName(), Toast.LENGTH_SHORT).show();
    }
    
    private void deleteClass(ClassInfo classInfo) {
        new AlertDialog.Builder(this)
            .setTitle("Delete Class")
            .setMessage("Are you sure you want to delete " + classInfo.getName() + "?")
            .setPositiveButton("Delete", (dialog, which) -> {
                allClasses.remove(classInfo);
                applyFilters(etSearch.getText().toString().trim());
                updateStatistics();
                Toast.makeText(this, "Class deleted", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
    
    private void toggleClassStatus(ClassInfo classInfo) {
        classInfo.setActive(!classInfo.isActive());
        applyFilters(etSearch.getText().toString().trim());
        updateStatistics();
        Toast.makeText(this, "Class status updated", Toast.LENGTH_SHORT).show();
    }
} 