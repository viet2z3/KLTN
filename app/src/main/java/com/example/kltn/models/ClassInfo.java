package com.example.kltn.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClassInfo {
    private String name;
    private String description;
    private int capacity;
    private int currentStudents;
    private String teacherName;
    private boolean isActive;
    private Date creationDate;

    public ClassInfo(String name, String description, int capacity, int currentStudents, String teacherName, boolean isActive, Date creationDate) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.currentStudents = currentStudents;
        this.teacherName = teacherName;
        this.isActive = isActive;
        this.creationDate = creationDate;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCapacity() { return capacity; }
    public int getCurrentStudents() { return currentStudents; }
    public String getTeacherName() { return teacherName; }
    public boolean isActive() { return isActive; }
    public Date getCreationDate() { return creationDate; }
    
    public void setActive(boolean active) { isActive = active; }
    
    public String getFormattedCreationDate() {
        if (creationDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            return sdf.format(creationDate);
        }
        return "";
    }
    
    public String getStudentInfo() {
        return currentStudents + " students · Created on " + getFormattedCreationDate();
    }
} 