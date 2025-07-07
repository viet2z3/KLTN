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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class JoinLeaveClassActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTitle;
    private EditText etClassCode;
    private Button btnJoinClass, btnBack;
    private RecyclerView rvMyClasses;
    
    private List<ClassItem> myClasses;
    private ClassAdapter classAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_leave_class);
        
        initializeViews();
        setupMyClasses();
        setupEventHandlers();
    }
    
    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        etClassCode = findViewById(R.id.etClassCode);
        btnJoinClass = findViewById(R.id.btnJoinClass);
        btnBack = findViewById(R.id.btnBack);
        rvMyClasses = findViewById(R.id.rvMyClasses);
    }
    
    private void setupMyClasses() {
        myClasses = createMyClasses();
        classAdapter = new ClassAdapter(myClasses, this::onLeaveClass);
        rvMyClasses.setLayoutManager(new LinearLayoutManager(this));
        rvMyClasses.setAdapter(classAdapter);
    }
    
    private List<ClassItem> createMyClasses() {
        List<ClassItem> classes = new ArrayList<>();
        classes.add(new ClassItem("English Class A", "ABC123", "Teacher: Ms. Smith"));
        classes.add(new ClassItem("Math Class B", "DEF456", "Teacher: Mr. Johnson"));
        return classes;
    }
    
    private void setupEventHandlers() {
        btnJoinClass.setOnClickListener(v -> joinClass());
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void joinClass() {
        String classCode = etClassCode.getText().toString().trim();
        
        // Validate input
        if (TextUtils.isEmpty(classCode)) {
            etClassCode.setError("Please enter a class code");
            etClassCode.requestFocus();
            return;
        }
        
        if (classCode.length() < 3) {
            etClassCode.setError("Class code must be at least 3 characters");
            etClassCode.requestFocus();
            return;
        }
        
        // Simulate joining class
        if (isValidClassCode(classCode)) {
            Toast.makeText(this, R.string.class_join_success, Toast.LENGTH_LONG).show();
            
            // Add to my classes
            myClasses.add(new ClassItem("New Class", classCode, "Teacher: Unknown"));
            classAdapter.notifyItemInserted(myClasses.size() - 1);
            
            // Clear input
            etClassCode.setText("");
        } else {
            Toast.makeText(this, R.string.error_invalid_class_code, Toast.LENGTH_LONG).show();
        }
    }
    
    private void onLeaveClass(ClassItem classItem) {
        new android.app.AlertDialog.Builder(this)
            .setTitle("Leave Class")
            .setMessage("Are you sure you want to leave " + classItem.getClassName() + "?")
            .setPositiveButton("Yes", (dialog, which) -> {
                // Remove from my classes
                int position = myClasses.indexOf(classItem);
                if (position != -1) {
                    myClasses.remove(position);
                    classAdapter.notifyItemRemoved(position);
                    Toast.makeText(this, R.string.class_leave_success, Toast.LENGTH_LONG).show();
                }
            })
            .setNegativeButton("No", null)
            .show();
    }
    
    private boolean isValidClassCode(String classCode) {
        // In a real app, this would validate against the server
        // For demo purposes, accept any 3+ character code
        return classCode.length() >= 3;
    }
    
    // Class Item Data Class
    public static class ClassItem {
        private String className;
        private String classCode;
        private String teacherInfo;
        
        public ClassItem(String className, String classCode, String teacherInfo) {
            this.className = className;
            this.classCode = classCode;
            this.teacherInfo = teacherInfo;
        }
        
        public String getClassName() { return className; }
        public String getClassCode() { return classCode; }
        public String getTeacherInfo() { return teacherInfo; }
    }
    
    // Class Adapter
    private static class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {
        private List<ClassItem> classes;
        private OnLeaveClickListener listener;
        
        public interface OnLeaveClickListener {
            void onLeaveClick(ClassItem classItem);
        }
        
        public ClassAdapter(List<ClassItem> classes, OnLeaveClickListener listener) {
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
            ClassItem classItem = classes.get(position);
            holder.tvClassName.setText(classItem.getClassName());
            holder.tvDescription.setText("Code: " + classItem.getClassCode());
            holder.tvTeacher.setText(classItem.getTeacherInfo());
            
            holder.btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onLeaveClick(classItem);
                }
            });
        }
        
        @Override
        public int getItemCount() {
            return classes.size();
        }
        
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvClassName, tvDescription, tvTeacher;
            Button btnDelete;
            
            ViewHolder(View itemView) {
                super(itemView);
                tvClassName = itemView.findViewById(R.id.tvClassName);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                tvTeacher = itemView.findViewById(R.id.tvTeacher);
                btnDelete = itemView.findViewById(R.id.btnDelete);
            }
        }
    }
} 