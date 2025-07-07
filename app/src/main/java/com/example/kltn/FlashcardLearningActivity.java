package com.example.kltn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FlashcardLearningActivity extends AppCompatActivity {

    private TextView tvProgress, tvFlashcardText, tvFlashcardHint;
    private ProgressBar progressBar;
    private Button btnPrevious, btnFlip, btnNext, btnBack;
    private Button btnEasy, btnMedium, btnHard;
    
    private List<Flashcard> flashcards;
    private int currentCardIndex = 0;
    private boolean isCardFlipped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_learning);

        initViews();
        setupFlashcards();
        setupClickListeners();
        displayCurrentCard();
    }

    private void initViews() {
        tvProgress = findViewById(R.id.tv_progress);
        tvFlashcardText = findViewById(R.id.tv_flashcard_text);
        tvFlashcardHint = findViewById(R.id.tv_flashcard_hint);
        progressBar = findViewById(R.id.progress_bar);
        btnPrevious = findViewById(R.id.btn_previous);
        btnFlip = findViewById(R.id.btn_flip);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);
        btnEasy = findViewById(R.id.btn_easy);
        btnMedium = findViewById(R.id.btn_medium);
        btnHard = findViewById(R.id.btn_hard);
    }

    private void setupFlashcards() {
        flashcards = createFlashcards();
    }

    private List<Flashcard> createFlashcards() {
        List<Flashcard> cards = new ArrayList<>();
        
        cards.add(new Flashcard("Apple", "A red fruit that grows on trees"));
        cards.add(new Flashcard("Dog", "A friendly pet animal that barks"));
        cards.add(new Flashcard("House", "A place where people live"));
        cards.add(new Flashcard("Car", "A vehicle with four wheels"));
        cards.add(new Flashcard("Book", "Something you read to learn"));
        cards.add(new Flashcard("Tree", "A tall plant with leaves"));
        cards.add(new Flashcard("Water", "A clear liquid we drink"));
        cards.add(new Flashcard("Sun", "The bright star in the sky"));
        cards.add(new Flashcard("Moon", "Shines at night in the sky"));
        cards.add(new Flashcard("Star", "Small bright lights in the sky"));
        
        return cards;
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnPrevious.setOnClickListener(v -> showPreviousCard());
        btnFlip.setOnClickListener(v -> flipCard());
        btnNext.setOnClickListener(v -> showNextCard());
        
        btnEasy.setOnClickListener(v -> rateDifficulty("easy"));
        btnMedium.setOnClickListener(v -> rateDifficulty("medium"));
        btnHard.setOnClickListener(v -> rateDifficulty("hard"));
    }

    private void displayCurrentCard() {
        if (flashcards == null || flashcards.isEmpty()) return;
        
        Flashcard card = flashcards.get(currentCardIndex);
        tvProgress.setText(String.format("Card %d of %d", currentCardIndex + 1, flashcards.size()));
        
        // Update progress bar
        int progress = ((currentCardIndex + 1) * 100) / flashcards.size();
        progressBar.setProgress(progress);
        
        if (isCardFlipped) {
            tvFlashcardText.setText(card.getDefinition());
            tvFlashcardHint.setText("Tap to see word");
            btnFlip.setText("Show Word");
        } else {
            tvFlashcardText.setText(card.getWord());
            tvFlashcardHint.setText("Tap to reveal answer");
            btnFlip.setText("Show Answer");
        }
        
        updateNavigationButtons();
        updateDifficultyButtons();
    }

    private void flipCard() {
        isCardFlipped = !isCardFlipped;
        displayCurrentCard();
    }

    private void showPreviousCard() {
        if (currentCardIndex > 0) {
            currentCardIndex--;
            isCardFlipped = false;
            displayCurrentCard();
        }
    }

    private void showNextCard() {
        if (currentCardIndex < flashcards.size() - 1) {
            currentCardIndex++;
            isCardFlipped = false;
            displayCurrentCard();
        } else {
            showLearningComplete();
        }
    }

    private void updateNavigationButtons() {
        btnPrevious.setEnabled(currentCardIndex > 0);
        btnNext.setEnabled(currentCardIndex < flashcards.size() - 1);
    }

    private void updateDifficultyButtons() {
        // Only show difficulty buttons when card is flipped
        boolean showDifficulty = isCardFlipped;
        btnEasy.setVisibility(showDifficulty ? View.VISIBLE : View.GONE);
        btnMedium.setVisibility(showDifficulty ? View.VISIBLE : View.GONE);
        btnHard.setVisibility(showDifficulty ? View.VISIBLE : View.GONE);
    }

    private void rateDifficulty(String difficulty) {
        Flashcard currentCard = flashcards.get(currentCardIndex);
        String message = String.format("Marked '%s' as %s", currentCard.getWord(), difficulty);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        
        // Auto-advance to next card after rating
        if (currentCardIndex < flashcards.size() - 1) {
            showNextCard();
        } else {
            showLearningComplete();
        }
    }

    private void showLearningComplete() {
        Toast.makeText(this, "Congratulations! You've completed all flashcards!", Toast.LENGTH_LONG).show();
        finish();
    }

    // Flashcard Data Class
    public static class Flashcard {
        private String word;
        private String definition;

        public Flashcard(String word, String definition) {
            this.word = word;
            this.definition = definition;
        }

        public String getWord() { return word; }
        public String getDefinition() { return definition; }
    }
} 