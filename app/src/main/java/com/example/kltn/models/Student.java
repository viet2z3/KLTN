package com.example.kltn.models;

public class Student {
    private String name;
    private int age;
    private String email;
    private String phone;
    private String className;
    private int score;
    private int progress;
    private boolean isActive;

    public Student(String name, int age, String email, String phone, String className, int score, int progress, boolean isActive) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.className = className;
        this.score = score;
        this.progress = progress;
        this.isActive = isActive;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getClassName() { return className; }
    public int getScore() { return score; }
    public int getProgress() { return progress; }
    public boolean isActive() { return isActive; }
} 