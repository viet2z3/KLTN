package com.example.kltn.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.R;
import com.example.kltn.adapters.StudentAdapter;
import com.example.kltn.models.Student;
import java.util.ArrayList;
import java.util.List;

public class ManageStudentsActivity extends AppCompatActivity implements StudentAdapter.OnStudentActionListener {
    
    private RecyclerView rvStudents;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;
    private Spinner spinnerClass;
    private List<Student> allStudentsList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);
        
        initViews();
        setupRecyclerView();
        loadSampleData();
    }
    
    private void initViews() {
        rvStudents = findViewById(R.id.rvStudents);
        spinnerClass = findViewById(R.id.spinnerClass);
        
        // Setup Add Student button
        View btnAddStudent = findViewById(R.id.btnAddStudent);
        btnAddStudent.setOnClickListener(v -> showAddStudentDialog());
    }
    
    private void setupRecyclerView() {
        studentList = new ArrayList<>();
        allStudentsList = new ArrayList<>();
        studentAdapter = new StudentAdapter(studentList, this);
        
        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setAdapter(studentAdapter);
    }
    
    private void loadSampleData() {
        // Add sample students to all students list
        allStudentsList.add(new Student("Lucas Bennett", 7, "lucas.b@example.com", "", "Grade 2", 85, 75, true));
        allStudentsList.add(new Student("Olivia Carter", 8, "olivia.c@example.com", "", "Grade 3", 92, 80, true));
        allStudentsList.add(new Student("Noah Davis", 6, "noah.d@example.com", "", "Grade 1", 78, 65, true));
        allStudentsList.add(new Student("Isabella Evans", 9, "isabella.e@example.com", "", "Grade 4", 88, 85, true));
        allStudentsList.add(new Student("Ethan Foster", 7, "ethan.f@example.com", "", "Grade 2", 90, 78, true));
        allStudentsList.add(new Student("Sophia Green", 8, "sophia.g@example.com", "", "Grade 3", 87, 82, true));
        
        // Setup spinner
        setupSpinner();
        
        // Initially show all students
        studentList.addAll(allStudentsList);
        studentAdapter.notifyDataSetChanged();
    }
    
    private void setupSpinner() {
        // Create class options for spinner
        String[] classOptions = {"All Classes", "Grade 1", "Grade 2", "Grade 3", "Grade 4"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, classOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerClass.setAdapter(spinnerAdapter);
        spinnerClass.setSelection(0); // Default to "All Classes"
        
        // Set spinner listener
        spinnerClass.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                filterStudentsByClass(position);
            }
            
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Show all students
                studentList.clear();
                studentList.addAll(allStudentsList);
                studentAdapter.notifyDataSetChanged();
            }
        });
    }
    
    private void filterStudentsByClass(int position) {
        studentList.clear();
        
        if (position == 0) {
            // "All Classes" - show all students
            studentList.addAll(allStudentsList);
        } else {
            // Filter by specific grade
            String selectedGrade = "Grade " + position;
            for (Student student : allStudentsList) {
                if (selectedGrade.equals(student.getClassName())) {
                    studentList.add(student);
                }
            }
        }
        
        studentAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void onStudentAction(Student student, String action) {
        if ("delete".equals(action)) {
            showDeleteDialog(student);
        }
    }
    
    private void showDeleteDialog(Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_delete_student, null);
        
        // Set custom message with student name
        TextView tvDeleteMessage = dialogView.findViewById(R.id.tvDeleteMessage);
        tvDeleteMessage.setText("Are you sure you want to delete " + student.getName() + "? This action cannot be undone.");
        
        // Setup buttons
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnDelete = dialogView.findViewById(R.id.btnDelete);
        
        AlertDialog dialog = builder.create();
        dialog.setView(dialogView);
        
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnDelete.setOnClickListener(v -> {
            deleteStudent(student);
            dialog.dismiss();
        });
        
        dialog.show();
    }
    
    private void deleteStudent(Student student) {
        // Remove from both lists
        allStudentsList.remove(student);
        studentList.remove(student);
        
        // Update adapter
        studentAdapter.notifyDataSetChanged();
        
        // Show confirmation
        Toast.makeText(this, "Deleted: " + student.getName(), Toast.LENGTH_SHORT).show();
    }
    
    private void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_student, null);
        
        // Get views
        ImageView btnClose = dialogView.findViewById(R.id.btnClose);
        EditText etStudentName = dialogView.findViewById(R.id.etStudentName);
        EditText etStudentID = dialogView.findViewById(R.id.etStudentID);
        Spinner spinnerAddClass = dialogView.findViewById(R.id.spinnerAddClass);
        Button btnAddStudentConfirm = dialogView.findViewById(R.id.btnAddStudentConfirm);
        
        // Setup class spinner
        String[] classOptions = {"Grade 1", "Grade 2", "Grade 3", "Grade 4"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, classOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAddClass.setAdapter(spinnerAdapter);
        spinnerAddClass.setSelection(0);
        
        AlertDialog dialog = builder.create();
        dialog.setView(dialogView);
        
        // Close button
        btnClose.setOnClickListener(v -> dialog.dismiss());
        
        // Add student button
        btnAddStudentConfirm.setOnClickListener(v -> {
            String studentName = etStudentName.getText().toString().trim();
            String studentID = etStudentID.getText().toString().trim();
            String selectedClass = spinnerAddClass.getSelectedItem().toString();
            
            if (studentName.isEmpty()) {
                etStudentName.setError("Please enter student name");
                return;
            }
            
            if (studentID.isEmpty()) {
                etStudentID.setError("Please enter student ID");
                return;
            }
            
            // Create new student (using studentID as email for demo)
            Student newStudent = new Student(studentName, 7, studentID + "@example.com", "", selectedClass, 0, 0, true);
            
            // Add to lists
            allStudentsList.add(newStudent);
            studentList.add(newStudent);
            
            // Update adapter
            studentAdapter.notifyDataSetChanged();
            
            // Show confirmation
            Toast.makeText(this, "Added: " + studentName, Toast.LENGTH_SHORT).show();
            
            // Close dialog
            dialog.dismiss();
        });
        
        dialog.show();
    }
} 