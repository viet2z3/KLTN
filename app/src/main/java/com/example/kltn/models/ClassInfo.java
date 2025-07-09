package com.example.kltn.models;

public class ClassInfo {
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