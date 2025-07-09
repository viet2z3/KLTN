package com.example.kltn.models;

public class Badge {
    private String name;
    private String description;
    private boolean isEarned;

    public Badge(String name, String description, boolean isEarned) {
        this.name = name;
        this.description = description;
        this.isEarned = isEarned;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isEarned() { return isEarned; }
} 