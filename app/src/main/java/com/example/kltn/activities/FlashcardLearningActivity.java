package com.example.kltn.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;
import com.google.android.material.tabs.TabLayout;
import android.view.animation.AlphaAnimation;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import com.example.kltn.models.Flashcard;

public class FlashcardLearningActivity extends AppCompatActivity {

    private TextView tvProgress, tvFlashcardText, tvFlashcardHint;
    private TabLayout tabLayout;
    private LinearProgressIndicator flashcardProgressBar;
    private ImageView imgFlashcard;
    private TextView tvFlashcardExample;
    private ImageButton btnFlashcardBack, btnFlashcardNext;

    private List<Flashcard> flashcards;
    private int currentCardIndex = 0;
    private boolean isCardFlipped = false;
    private String currentTopic = "Colors";
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_learning);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("user_email");

        initViews();
        setupTabs();
        setupFlashcards();
        setupClickListeners();
        displayCurrentCard();
        setupBottomNavigation();
    }

    private void initViews() {
        tvProgress = findViewById(R.id.tvProgress); // Card 3/10
        tvFlashcardText = findViewById(R.id.tvFlashcardText); // Từ tiếng Anh
        tvFlashcardHint = findViewById(R.id.tvFlashcardHint); // Nghĩa tiếng Việt
        tabLayout = findViewById(R.id.tabLayout);
        flashcardProgressBar = findViewById(R.id.flashcardProgressBar);
        imgFlashcard = findViewById(R.id.r4tyal07kb98);
        tvFlashcardExample = findViewById(R.id.tvFlashcardExample);
        btnFlashcardBack = findViewById(R.id.btnFlashcardBack);
        btnFlashcardNext = findViewById(R.id.btnFlashcardNext);
    }

    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Colors"));
        tabLayout.addTab(tabLayout.newTab().setText("Animals"));
        tabLayout.addTab(tabLayout.newTab().setText("Numbers"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String topic = tab.getText().toString();
                if (!topic.equals(currentTopic)) {
                    currentTopic = topic;
                    setupFlashcards();
                    currentCardIndex = 0;
                    isCardFlipped = false;
                    animateFlashcardChange();
                    displayCurrentCard();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupFlashcards() {
        flashcards = createFlashcardsByTopic(currentTopic);
    }

    private List<Flashcard> createFlashcardsByTopic(String topic) {
        List<Flashcard> cards = new ArrayList<>();
        switch (topic) {
            case "Colors":
                cards.add(new Flashcard("Red", "Màu đỏ", "The apple is red."));
                cards.add(new Flashcard("Blue", "Màu xanh dương", "The sky is blue."));
                cards.add(new Flashcard("Green", "Màu xanh lá", "The leaves are green."));
                break;
            case "Animals":
                cards.add(new Flashcard("Dog", "Con chó", "The dog is barking."));
                cards.add(new Flashcard("Cat", "Con mèo", "The cat is sleeping."));
                cards.add(new Flashcard("Bird", "Con chim", "The bird is singing."));
                break;
            case "Numbers":
                cards.add(new Flashcard("One", "Số một", "I have one book."));
                cards.add(new Flashcard("Two", "Số hai", "She has two cats."));
                cards.add(new Flashcard("Three", "Số ba", "There are three apples."));
                break;
            default:
                cards.add(new Flashcard("Apple", "A red fruit that grows on trees", "I eat an apple every day."));
        }
        return cards;
    }

    private void animateFlashcardChange() {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(400);
        tvFlashcardText.startAnimation(anim);
        tvFlashcardHint.startAnimation(anim);
    }

    private void setupClickListeners() {
        imgFlashcard.setOnClickListener(v -> flipCardWithAnimation());
        btnFlashcardBack.setOnClickListener(v -> showPreviousCard());
        btnFlashcardNext.setOnClickListener(v -> showNextCard());
        

    }

    private void flipCardWithAnimation() {
        View cardView = findViewById(R.id.flashcardCardView);
        AnimatorSet outAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_out);
        AnimatorSet inAnim = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_in);
        outAnim.setTarget(cardView);
        inAnim.setTarget(cardView);
        outAnim.start();
        outAnim.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                isCardFlipped = !isCardFlipped;
                displayCurrentCard();
                inAnim.start();
            }
        });
    }

    private void displayCurrentCard() {
        if (flashcards == null || flashcards.isEmpty()) return;
        
        Flashcard card = flashcards.get(currentCardIndex);
        tvProgress.setText(String.format("Card %d/%d", currentCardIndex + 1, flashcards.size()));
        
        // Update progress bar
        int progress = ((currentCardIndex + 1) * 100) / flashcards.size();
        flashcardProgressBar.setProgress(progress);
        
        if (isCardFlipped) {
            tvFlashcardText.setText(card.getDefinition());
            tvFlashcardHint.setText("Tap image to see word");
            tvFlashcardExample.setVisibility(View.VISIBLE);
            tvFlashcardExample.setText(card.getExample());
        } else {
            tvFlashcardText.setText(card.getWord());
            tvFlashcardHint.setText("Tap image to reveal answer");
            tvFlashcardExample.setVisibility(View.GONE);
        }
        
        // updateNavigationButtons(); // Nếu có nút điều hướng thì xử lý riêng
        // updateDifficultyButtons(); // Nếu có nút đánh giá thì xử lý riêng
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
        // btnPrevious.setEnabled(currentCardIndex > 0); // Nếu có nút điều hướng thì xử lý riêng
        // btnNext.setEnabled(currentCardIndex < flashcards.size() - 1); // Nếu có nút điều hướng thì xử lý riêng
    }

    private void updateDifficultyButtons() {
        // Only show difficulty buttons when card is flipped
        boolean showDifficulty = isCardFlipped;
        // btnEasy.setVisibility(showDifficulty ? View.VISIBLE : View.GONE); // Nếu có nút đánh giá thì xử lý riêng
        // btnMedium.setVisibility(showDifficulty ? View.VISIBLE : View.GONE); // Nếu có nút đánh giá thì xử lý riêng
        // btnHard.setVisibility(showDifficulty ? View.VISIBLE : View.GONE); // Nếu có nút đánh giá thì xử lý riêng
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

    private void setupBottomNavigation() {
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Intent intent = null;
            if (id == R.id.nav_home) {
                intent = new Intent(this, StudentHomeActivity.class);
            } else if (id == R.id.nav_learn) {
                // Đang ở màn hình này, không chuyển
                return true;
            } else if (id == R.id.nav_badge) {
                intent = new Intent(this, BadgesScreenActivity.class);
            } else if (id == R.id.nav_setting) {
                intent = new Intent(this, SettingsActivity.class);
            }
            if (intent != null) {
                intent.putExtra("user_email", userEmail);
                startActivity(intent);
                // finish(); // Nếu muốn đóng màn hiện tại
            }
            return true;
        });
        // Đặt mục Learn được chọn mặc định
        bottomNavigationView.setSelectedItemId(R.id.nav_learn);
    }
} 