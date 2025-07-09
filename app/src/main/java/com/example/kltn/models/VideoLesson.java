package com.example.kltn.models;

public class VideoLesson {
    private String title;
    private String description;
    private String duration;
    private String topic;
    private int thumbnailResId;

    public VideoLesson(String title, String description, String duration, String topic, int thumbnailResId) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.topic = topic;
        this.thumbnailResId = thumbnailResId;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDuration() { return duration; }
    public String getTopic() { return topic; }
    public int getThumbnailResId() { return thumbnailResId; }
} 