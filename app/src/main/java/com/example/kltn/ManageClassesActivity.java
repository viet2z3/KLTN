package com.example.kltn;

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
    
    // Class Info Data Class
    public static class ClassInfo {
        private String name;
        private String description;
        private int capacity;
        private int currentStudents;
        private String teacherName;
        private boolean isActive;
        
        public ClassInfo(String name, String description, int capacity, int currentStudents, String teacherName, boolean isActive) {
            this.name = name;
            this.description = description;
            this.capacity = capacity;
            this.currentStudents = currentStudents;
            this.teacherName = teacherName;
            this.isActive = isActive;
        }
        
        public String getName() { return name; }
        public String getDescription() { return description; }
        public int getCapacity() { return capacity; }
        public int getCurrentStudents() { return currentStudents; }
        public String getTeacherName() { return teacherName; }
        public boolean isActive() { return isActive; }
        public void setActive(boolean active) { isActive = active; }
    }
    
    // Class Adapter
    private static class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {
        private List<ClassInfo> classes;
        private OnClassActionListener listener;
        
        public interface OnClassActionListener {
            void onClassAction(ClassInfo classInfo, String action);
        }
        
        public ClassAdapter(List<ClassInfo> classes, OnClassActionListener listener) {
            this.classes = classes;
            this.listener = listener;
        }
        
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_class_card, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ClassInfo classInfo = classes.get(position);
            holder.bind(classInfo);
        }
        
        @Override
        public int getItemCount() {
            return classes.size();
        }
        
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvClassName, tvDescription, tvCapacity, tvTeacher, tvStatus;
            Button btnEdit, btnDelete, btnToggle;
            
            ViewHolder(View itemView) {
                super(itemView);
                tvClassName = itemView.findViewById(R.id.tvClassName);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                tvCapacity = itemView.findViewById(R.id.tvCapacity);
                tvTeacher = itemView.findViewById(R.id.tvTeacher);
                tvStatus = itemView.findViewById(R.id.tvStatus);
                btnEdit = itemView.findViewById(R.id.btnEdit);
                btnDelete = itemView.findViewById(R.id.btnDelete);
                btnToggle = itemView.findViewById(R.id.btnToggleStatus);
            }
            
            void bind(ClassInfo classInfo) {
                tvClassName.setText(classInfo.getName());
                tvDescription.setText(classInfo.getDescription());
                tvCapacity.setText(classInfo.getCurrentStudents() + "/" + classInfo.getCapacity() + " students");
                tvTeacher.setText(classInfo.getTeacherName());
                tvStatus.setText(classInfo.isActive() ? "Active" : "Inactive");
                
                btnEdit.setOnClickListener(v -> {
                    if (listener != null) listener.onClassAction(classInfo, "edit");
                });
                btnDelete.setOnClickListener(v -> {
                    if (listener != null) listener.onClassAction(classInfo, "delete");
                });
                btnToggle.setOnClickListener(v -> {
                    if (listener != null) listener.onClassAction(classInfo, "toggle");
                });
            }
        }
    }
} 