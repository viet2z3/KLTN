package com.example.kltn.models;

public class TestQuestion {
    private String question;
    private String[] options;
    private int correctAnswer;

    public TestQuestion(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }
} 