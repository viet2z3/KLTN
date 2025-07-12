package com.example.kltn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kltn.R;
import com.example.kltn.adapters.ClassSimpleAdapter;
import com.example.kltn.models.ClassInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ManageClassesActivity extends AppCompatActivity implements ClassSimpleAdapter.OnClassClickListener {

    private RecyclerView rvClasses;
    private EditText etSearch;
    private TextView tvTotalClasses;
    private FloatingActionButton fabAddClass;
    private ImageButton btnBack;
    
    private ClassSimpleAdapter adapter;
    private List<ClassInfo> allClasses;
    private List<ClassInfo> filteredClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_classes);
        
        initViews();
        setupListeners();
        loadSampleData();
        setupRecyclerView();
        updateTotalClasses();
    }
    
    private void initViews() {
        rvClasses = findViewById(R.id.rv_classes);
        etSearch = findViewById(R.id.et_search);
        tvTotalClasses = findViewById(R.id.tv_total_classes);
        fabAddClass = findViewById(R.id.fab_add_class);
        btnBack = findViewById(R.id.btn_back);
    }
    
    private void setupListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());
        
        fabAddClass.setOnClickListener(v -> showAddClassDialog());
        
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterClasses(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    
    private void loadSampleData() {
        allClasses = new ArrayList<>();
        
        // Sample data matching the HTML design
        Calendar cal = Calendar.getInstance();
        
        cal.set(2024, 0, 15); // January 15, 2024
        allClasses.add(new ClassInfo("Class 1A", "Beginner English", 20, 15, "Teacher A", true, cal.getTime()));
        
        cal.set(2024, 1, 20); // February 20, 2024
        allClasses.add(new ClassInfo("Class 2B", "Intermediate English", 25, 20, "Teacher B", true, cal.getTime()));
        
        cal.set(2024, 2, 10); // March 10, 2024
        allClasses.add(new ClassInfo("Class 3C", "Advanced English", 18, 18, "Teacher C", true, cal.getTime()));
        
        cal.set(2024, 3, 5); // April 5, 2024
        allClasses.add(new ClassInfo("Class 4D", "Business English", 30, 22, "Teacher D", true, cal.getTime()));
        
        cal.set(2024, 4, 22); // May 22, 2024
        allClasses.add(new ClassInfo("Class 5E", "Conversation English", 20, 16, "Teacher E", true, cal.getTime()));
        
        filteredClasses = new ArrayList<>(allClasses);
    }
    
    private void setupRecyclerView() {
        adapter = new ClassSimpleAdapter(this, filteredClasses, this);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));
        rvClasses.setAdapter(adapter);
    }
    
    private void updateTotalClasses() {
        if (tvTotalClasses != null) {
            tvTotalClasses.setText("Total Classes: " + allClasses.size());
        }
    }
    
    private void filterClasses(String query) {
        if (query.isEmpty()) {
            filteredClasses = new ArrayList<>(allClasses);
        } else {
            filteredClasses = allClasses.stream()
                    .filter(classInfo -> classInfo.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        adapter.filterData(filteredClasses);
    }
    
    private void showAddClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit_class, null);
        builder.setView(dialogView);
        
        AlertDialog dialog = builder.create();
        
        // Get dialog views
        TextView tvTitle = dialogView.findViewById(R.id.tv_dialog_title);
        EditText etClassName = dialogView.findViewById(R.id.et_class_name);
        EditText etDescription = dialogView.findViewById(R.id.et_description);
        EditText etCapacity = dialogView.findViewById(R.id.et_capacity);
        EditText etTeacherName = dialogView.findViewById(R.id.et_teacher_name);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnSave = dialogView.findViewById(R.id.btn_save);
        
        // Set title
        tvTitle.setText("Add New Class");
        
        // Set click listeners
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        
        btnSave.setOnClickListener(v -> {
            String className = etClassName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String capacityStr = etCapacity.getText().toString().trim();
            String teacherName = etTeacherName.getText().toString().trim();
            
            // Validation
            if (className.isEmpty()) {
                etClassName.setError("Class name is required");
                return;
            }
            
            if (description.isEmpty()) {
                etDescription.setError("Description is required");
                return;
            }
            
            if (capacityStr.isEmpty()) {
                etCapacity.setError("Capacity is required");
                return;
            }
            
            int capacity;
            try {
                capacity = Integer.parseInt(capacityStr);
                if (capacity <= 0) {
                    etCapacity.setError("Capacity must be greater than 0");
                    return;
                }
            } catch (NumberFormatException e) {
                etCapacity.setError("Invalid capacity");
                return;
            }
            
            if (teacherName.isEmpty()) {
                etTeacherName.setError("Teacher name is required");
                return;
            }
            
            // Create new class
            ClassInfo newClass = new ClassInfo(className, description, capacity, 0, teacherName, true, new Date());
            allClasses.add(newClass);
            filteredClasses.add(newClass);
            adapter.updateData(filteredClasses);
            updateTotalClasses();
            
            dialog.dismiss();
            Toast.makeText(this, "Class added successfully", Toast.LENGTH_SHORT).show();
        });
        
        dialog.show();
    }
    
    private void showEditClassDialog(ClassInfo classInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit_class, null);
        builder.setView(dialogView);
        
        AlertDialog dialog = builder.create();
        
        // Get dialog views
        TextView tvTitle = dialogView.findViewById(R.id.tv_dialog_title);
        EditText etClassName = dialogView.findViewById(R.id.et_class_name);
        EditText etDescription = dialogView.findViewById(R.id.et_description);
        EditText etCapacity = dialogView.findViewById(R.id.et_capacity);
        EditText etTeacherName = dialogView.findViewById(R.id.et_teacher_name);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnSave = dialogView.findViewById(R.id.btn_save);
        
        // Set title and populate fields
        tvTitle.setText("Edit Class");
        etClassName.setText(classInfo.getName());
        etDescription.setText(classInfo.getDescription());
        etCapacity.setText(String.valueOf(classInfo.getCapacity()));
        etTeacherName.setText(classInfo.getTeacherName());
        
        // Set click listeners
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        
        btnSave.setOnClickListener(v -> {
            String className = etClassName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String capacityStr = etCapacity.getText().toString().trim();
            String teacherName = etTeacherName.getText().toString().trim();
            
            // Validation
            if (className.isEmpty()) {
                etClassName.setError("Class name is required");
                return;
            }
            
            if (description.isEmpty()) {
                etDescription.setError("Description is required");
                return;
            }
            
            if (capacityStr.isEmpty()) {
                etCapacity.setError("Capacity is required");
                return;
            }
            
            int capacity;
            try {
                capacity = Integer.parseInt(capacityStr);
                if (capacity <= 0) {
                    etCapacity.setError("Capacity must be greater than 0");
                    return;
                }
            } catch (NumberFormatException e) {
                etCapacity.setError("Invalid capacity");
                return;
            }
            
            if (teacherName.isEmpty()) {
                etTeacherName.setError("Teacher name is required");
                return;
            }
            
            // Update class info
            // Note: Since ClassInfo is immutable, we'll remove and add a new one
            int index = allClasses.indexOf(classInfo);
            if (index != -1) {
                ClassInfo updatedClass = new ClassInfo(className, description, capacity, 
                    classInfo.getCurrentStudents(), teacherName, classInfo.isActive(), classInfo.getCreationDate());
                allClasses.set(index, updatedClass);
                
                // Update filtered list
                int filteredIndex = filteredClasses.indexOf(classInfo);
                if (filteredIndex != -1) {
                    filteredClasses.set(filteredIndex, updatedClass);
                }
                
                adapter.updateData(filteredClasses);
                dialog.dismiss();
                Toast.makeText(this, "Class updated successfully", Toast.LENGTH_SHORT).show();
            }
        });
        
        dialog.show();
    }
    
    private void showDeleteClassDialog(ClassInfo classInfo) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Class")
                .setMessage("Are you sure you want to delete " + classInfo.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    allClasses.remove(classInfo);
                    filteredClasses.remove(classInfo);
                    adapter.updateData(filteredClasses);
                    updateTotalClasses();
                    Toast.makeText(this, "Class deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onClassClick(ClassInfo classInfo) {
        // TODO: Navigate to class detail screen
        Toast.makeText(this, "Selected: " + classInfo.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditClick(ClassInfo classInfo) {
        showEditOptionsDialog(classInfo);
    }
    
    private void showEditOptionsDialog(ClassInfo classInfo) {
        String[] options = {"Edit", "Delete"};
        
        new AlertDialog.Builder(this)
                .setTitle("Class Options")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0: // Edit
                            showEditClassDialog(classInfo);
                            break;
                        case 1: // Delete
                            showDeleteClassDialog(classInfo);
                            break;
                    }
                })
                .show();
    }
} 