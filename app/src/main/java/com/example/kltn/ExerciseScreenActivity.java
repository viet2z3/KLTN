package com.example.kltn;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ExerciseScreenActivity extends AppCompatActivity {

    private TextView tvTimer, tvQuestionNumber, tvQuestionText;
    private EditText etAnswer;
    private Button btnPrevious, btnSubmit, btnNext, btnBack;
    
    private List<ExerciseQuestion> questions;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_screen);

        initViews();
        setupQuestions();
        setupClickListeners();
        displayCurrentQuestion();
        startTimer();
    }

    private void initViews() {
        tvTimer = findViewById(R.id.tv_timer);
        tvQuestionNumber = findViewById(R.id.tv_question_number);
        tvQuestionText = findViewById(R.id.tv_question_text);
        etAnswer = findViewById(R.id.et_answer);
        btnPrevious = findViewById(R.id.btn_previous);
        btnSubmit = findViewById(R.id.btn_submit);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);
    }

    private void setupQuestions() {
        questions = createQuestions();
    }

    private List<ExerciseQuestion> createQuestions() {
        List<ExerciseQuestion> questions = new ArrayList<>();
        
        questions.add(new ExerciseQuestion(
            "Fill in the blank: The cat ___ on the mat.",
            "sits",
            "The cat sits on the mat."
        ));
        
        questions.add(new ExerciseQuestion(
            "Fill in the blank: I ___ to school every day.",
            "go",
            "I go to school every day."
        ));
        
        questions.add(new ExerciseQuestion(
            "Fill in the blank: She ___ a beautiful dress.",
            "wears",
            "She wears a beautiful dress."
        ));
        
        questions.add(new ExerciseQuestion(
            "Fill in the blank: The sun ___ in the east.",
            "rises",
            "The sun rises in the east."
        ));
        
        questions.add(new ExerciseQuestion(
            "Fill in the blank: We ___ dinner at 7 PM.",
            "eat",
            "We eat dinner at 7 PM."
        ));
        
        return questions;
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnPrevious.setOnClickListener(v -> previousQuestion());
        btnSubmit.setOnClickListener(v -> submitAnswer());
        btnNext.setOnClickListener(v -> nextQuestion());
    }

    private void displayCurrentQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            ExerciseQuestion question = questions.get(currentQuestionIndex);
            
            tvQuestionNumber.setText(String.format("Question %d of %d", 
                currentQuestionIndex + 1, questions.size()));
            tvQuestionText.setText(question.getQuestion());
            etAnswer.setText("");
            
            // Update button states
            btnPrevious.setEnabled(currentQuestionIndex > 0);
            btnSubmit.setEnabled(true);
            btnNext.setEnabled(false);
        }
    }

    private void submitAnswer() {
        String answer = etAnswer.getText().toString().trim();
        
        if (TextUtils.isEmpty(answer)) {
            Toast.makeText(this, "Please enter your answer", Toast.LENGTH_SHORT).show();
            return;
        }

        ExerciseQuestion currentQuestion = questions.get(currentQuestionIndex);
        boolean isCorrect = answer.equalsIgnoreCase(currentQuestion.getCorrectAnswer());

        if (isCorrect) {
            correctAnswers++;
            Toast.makeText(this, "Correct! " + currentQuestion.getExplanation(), 
                Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Incorrect. The answer is: " + currentQuestion.getCorrectAnswer(), 
                Toast.LENGTH_LONG).show();
        }

        // Enable next button
        btnSubmit.setEnabled(false);
        btnNext.setEnabled(true);
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        
        if (currentQuestionIndex < questions.size()) {
            displayCurrentQuestion();
        } else {
            showExerciseComplete();
        }
    }

    private void previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            displayCurrentQuestion();
        }
    }

    private void startTimer() {
        // Simple timer implementation
        new Thread(() -> {
            int timeLeft = 300; // 5 minutes
            while (timeLeft > 0 && currentQuestionIndex < questions.size()) {
                final int minutes = timeLeft / 60;
                final int seconds = timeLeft % 60;
                
                runOnUiThread(() -> {
                    tvTimer.setText(String.format("Time: %02d:%02d", minutes, seconds));
                });
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                timeLeft--;
            }
            
            if (timeLeft <= 0) {
                runOnUiThread(this::showExerciseComplete);
            }
        }).start();
    }

    private void showExerciseComplete() {
        String message = String.format("Exercise completed! You got %d out of %d correct.", 
            correctAnswers, questions.size());
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
    }

    // Exercise Question Data Class
    public static class ExerciseQuestion {
        private String question;
        private String correctAnswer;
        private String explanation;

        public ExerciseQuestion(String question, String correctAnswer, String explanation) {
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.explanation = explanation;
        }

        public String getQuestion() { return question; }
        public String getCorrectAnswer() { return correctAnswer; }
        public String getExplanation() { return explanation; }
    }
} 