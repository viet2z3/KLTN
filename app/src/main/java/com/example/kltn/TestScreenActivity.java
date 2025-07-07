package com.example.kltn;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TestScreenActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTimer, tvQuestionNumber, tvQuestionText;
    private Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
    private Button btnPrevious, btnNext, btnBack;
    
    // Test data
    private List<TestQuestion> questions;
    private int currentQuestionIndex = 0;
    private int[] userAnswers;
    private CountDownTimer timer;
    private long timeRemaining = 30 * 60 * 1000; // 30 minutes in milliseconds
    private int selectedAnswerIndex = -1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_screen);
        
        initializeViews();
        setupTestData();
        setupEventHandlers();
        displayQuestion(0);
        startTimer();
    }
    
    private void initializeViews() {
        tvTimer = findViewById(R.id.tv_timer);
        tvQuestionNumber = findViewById(R.id.tv_question_number);
        tvQuestionText = findViewById(R.id.tv_question_text);
        btnAnswer1 = findViewById(R.id.btn_answer_1);
        btnAnswer2 = findViewById(R.id.btn_answer_2);
        btnAnswer3 = findViewById(R.id.btn_answer_3);
        btnAnswer4 = findViewById(R.id.btn_answer_4);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);
    }
    
    private void setupTestData() {
        questions = new ArrayList<>();
        questions.add(new TestQuestion(
            "What is the opposite of 'big'?",
            new String[]{"small", "large", "huge", "tall"},
            0
        ));
        questions.add(new TestQuestion(
            "Which word is a color?",
            new String[]{"run", "blue", "happy", "fast"},
            1
        ));
        questions.add(new TestQuestion(
            "Complete the sentence: 'I ___ a student.'",
            new String[]{"am", "is", "are", "be"},
            0
        ));
        questions.add(new TestQuestion(
            "What is 5 + 3?",
            new String[]{"6", "7", "8", "9"},
            2
        ));
        questions.add(new TestQuestion(
            "Which animal says 'meow'?",
            new String[]{"dog", "cat", "bird", "fish"},
            1
        ));
        
        userAnswers = new int[questions.size()];
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1; // -1 means no answer selected
        }
    }
    
    private void setupEventHandlers() {
        btnPrevious.setOnClickListener(v -> previousQuestion());
        btnNext.setOnClickListener(v -> nextQuestion());
        btnBack.setOnClickListener(v -> showExitConfirmation());
        
        // Answer button click listeners
        btnAnswer1.setOnClickListener(v -> selectAnswer(0));
        btnAnswer2.setOnClickListener(v -> selectAnswer(1));
        btnAnswer3.setOnClickListener(v -> selectAnswer(2));
        btnAnswer4.setOnClickListener(v -> selectAnswer(3));
    }
    
    private void selectAnswer(int answerIndex) {
        selectedAnswerIndex = answerIndex;
        userAnswers[currentQuestionIndex] = answerIndex;
        
        // Update button styles
        updateAnswerButtonStyles();
    }
    
    private void updateAnswerButtonStyles() {
        // Reset all buttons to secondary style
        btnAnswer1.setBackground(getDrawable(R.drawable.button_secondary));
        btnAnswer2.setBackground(getDrawable(R.drawable.button_secondary));
        btnAnswer3.setBackground(getDrawable(R.drawable.button_secondary));
        btnAnswer4.setBackground(getDrawable(R.drawable.button_secondary));
        
        btnAnswer1.setTextColor(getColor(R.color.primary_blue));
        btnAnswer2.setTextColor(getColor(R.color.primary_blue));
        btnAnswer3.setTextColor(getColor(R.color.primary_blue));
        btnAnswer4.setTextColor(getColor(R.color.primary_blue));
        
        // Highlight selected answer
        Button selectedButton = null;
        switch (selectedAnswerIndex) {
            case 0:
                selectedButton = btnAnswer1;
                break;
            case 1:
                selectedButton = btnAnswer2;
                break;
            case 2:
                selectedButton = btnAnswer3;
                break;
            case 3:
                selectedButton = btnAnswer4;
                break;
        }
        
        if (selectedButton != null) {
            selectedButton.setBackground(getDrawable(R.drawable.button_primary));
            selectedButton.setTextColor(getColor(R.color.white));
        }
    }
    
    private void displayQuestion(int index) {
        if (index < 0 || index >= questions.size()) return;
        
        currentQuestionIndex = index;
        TestQuestion question = questions.get(index);
        
        tvQuestionNumber.setText(String.format("Question %d of %d", index + 1, questions.size()));
        tvQuestionText.setText(question.getQuestion());
        
        btnAnswer1.setText("A) " + question.getOptions()[0]);
        btnAnswer2.setText("B) " + question.getOptions()[1]);
        btnAnswer3.setText("C) " + question.getOptions()[2]);
        btnAnswer4.setText("D) " + question.getOptions()[3]);
        
        // Restore user's previous answer
        selectedAnswerIndex = userAnswers[index];
        updateAnswerButtonStyles();
        
        // Update navigation buttons
        btnPrevious.setEnabled(index > 0);
        if (index == questions.size() - 1) {
            btnNext.setText("Submit");
            btnNext.setOnClickListener(v -> showSubmitConfirmation());
        } else {
            btnNext.setText("Next");
            btnNext.setOnClickListener(v -> nextQuestion());
        }
    }
    
    private void previousQuestion() {
        if (currentQuestionIndex > 0) {
            displayQuestion(currentQuestionIndex - 1);
        }
    }
    
    private void nextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            displayQuestion(currentQuestionIndex + 1);
        }
    }
    
    private void startTimer() {
        timer = new CountDownTimer(timeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateTimerDisplay();
            }
            
            @Override
            public void onFinish() {
                showTimeUpDialog();
            }
        }.start();
    }
    
    private void updateTimerDisplay() {
        int minutes = (int) (timeRemaining / 1000) / 60;
        int seconds = (int) (timeRemaining / 1000) % 60;
        tvTimer.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }
    
    private void showSubmitConfirmation() {
        new AlertDialog.Builder(this)
            .setTitle("Submit Test")
            .setMessage("Are you sure you want to submit your test?")
            .setPositiveButton("Submit", (dialog, which) -> submitTest())
            .setNegativeButton("Continue", null)
            .show();
    }
    
    private void showTimeUpDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Time's Up!")
            .setMessage("Your time has expired. The test will be submitted automatically.")
            .setPositiveButton("OK", (dialog, which) -> submitTest())
            .setCancelable(false)
            .show();
    }
    
    private void showExitConfirmation() {
        new AlertDialog.Builder(this)
            .setTitle("Exit Test")
            .setMessage("Are you sure you want to exit? Your progress will be lost.")
            .setPositiveButton("Exit", (dialog, which) -> finish())
            .setNegativeButton("Continue", null)
            .show();
    }
    
    private void submitTest() {
        // Calculate score
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i] == questions.get(i).getCorrectAnswer()) {
                correctAnswers++;
            }
        }
        
        double score = (double) correctAnswers / questions.size() * 100;
        
        // Show results
        String message = String.format("Test completed!\nScore: %.1f%%\nCorrect: %d/%d", 
            score, correctAnswers, questions.size());
        
        new AlertDialog.Builder(this)
            .setTitle("Test Results")
            .setMessage(message)
            .setPositiveButton("OK", (dialog, which) -> finish())
            .setCancelable(false)
            .show();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
    
    @Override
    public void onBackPressed() {
        showExitConfirmation();
    }
    
    private static class TestQuestion {
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
} 