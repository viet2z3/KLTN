package com.example.kltn.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;
import com.example.kltn.models.Student;
import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class EvaluateStudentActivity extends AppCompatActivity {

    private TextView tvEvaluationDate;
    private Spinner spinnerSubject;
    private String selectedSubject;
    private Spinner spinnerStudentName;
    private TextView tvStudentClass;
    private List<Student> studentList;
    private Student selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_student);

        // Initialize views
        tvEvaluationDate = findViewById(R.id.tvEvaluationDate);
        spinnerSubject = findViewById(R.id.spinnerSubject);
        spinnerStudentName = findViewById(R.id.spinnerStudentName);
        tvStudentClass = findViewById(R.id.tvStudentClass);

        // Set current date
        setCurrentDate();
        
        // Setup spinner
        setupSpinner();
        loadSampleStudents();
        setupStudentSpinner();
    }

    private void setCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        tvEvaluationDate.setText(currentDate);
    }

    private void setupSpinner() {
        // Create array of subjects
        String[] subjects = {
            "Select Subject",
            "Mathematics",
            "English",
            "Science",
            "History",
            "Geography",
            "Literature",
            "Physics",
            "Chemistry",
            "Biology",
            "Computer Science",
            "Art",
            "Music",
            "Physical Education"
        };

        // Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            R.layout.spinner_item,
            subjects
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Set adapter to spinner
        spinnerSubject.setAdapter(adapter);

        // Set default selection
        spinnerSubject.setSelection(0);

        // Set item selected listener
        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = subjects[position];
                if (position > 0) { // Not "Select Subject"
                    Toast.makeText(EvaluateStudentActivity.this, 
                        "Selected: " + selectedSubject, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedSubject = "";
            }
        });
    }

    private void loadSampleStudents() {
        studentList = new ArrayList<>();
        studentList.add(new Student("Lucas Bennett", 7, "lucas.b@example.com", "", "Grade 2", 85, 75, true));
        studentList.add(new Student("Olivia Carter", 8, "olivia.c@example.com", "", "Grade 3", 92, 80, true));
        studentList.add(new Student("Noah Davis", 6, "noah.d@example.com", "", "Grade 1", 78, 65, true));
        studentList.add(new Student("Isabella Evans", 9, "isabella.e@example.com", "", "Grade 4", 88, 85, true));
        studentList.add(new Student("Ethan Foster", 7, "ethan.f@example.com", "", "Grade 2", 90, 78, true));
        studentList.add(new Student("Sophia Green", 8, "sophia.g@example.com", "", "Grade 3", 87, 82, true));
    }

    private void setupStudentSpinner() {
        List<String> studentNames = new ArrayList<>();
        studentNames.add("Select Students");
        for (Student s : studentList) {
            studentNames.add(s.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            R.layout.spinner_item,
            studentNames
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerStudentName.setAdapter(adapter);
        spinnerStudentName.setSelection(0);
        spinnerStudentName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedStudent = null;
                    tvStudentClass.setText("");
                } else {
                    selectedStudent = studentList.get(position - 1);
                    tvStudentClass.setText(selectedStudent.getClassName());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedStudent = null;
                tvStudentClass.setText("");
            }
        });
    }

    // Method to get selected subject
    public String getSelectedSubject() {
        return selectedSubject;
    }
} 