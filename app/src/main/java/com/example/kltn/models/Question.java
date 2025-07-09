package com.example.kltn.models;

import java.util.List;

public class Question {
    public String question;
    public String answer;
    public int imageRes;
    public List<String> choices; // null nếu là fill blank
    public Question(String q, String a, int img, List<String> c) {
        question = q; answer = a; imageRes = img; choices = c;
    }
} 