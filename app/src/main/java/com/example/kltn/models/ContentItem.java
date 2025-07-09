package com.example.kltn.models;

public class ContentItem {
    private String title, type, date;
    public ContentItem(String title, String type, String date) {
        this.title = title; this.type = type; this.date = date;
    }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getDate() { return date; }
} 