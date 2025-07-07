package com.example.kltn;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentHomeActivity extends AppCompatActivity {

    private TextView tvWelcomeMessage;
    private RecyclerView rvLearningCards;
    private Button btnProfile;
    private LearningCardAdapter adapter;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("user_email");

        // Initialize views
        initViews();

        // Setup learning cards
        setupLearningCards();

        // Setup click listeners
        setupClickListeners();
    }

    private void initViews() {
        tvWelcomeMessage = findViewById(R.id.tv_welcome_message);
        rvLearningCards = findViewById(R.id.rv_learning_cards);
        btnProfile = findViewById(R.id.btn_profile);
        
        rvLearningCards.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void setupLearningCards() {
        List<LearningCard> cards = createLearningCards();
        adapter = new LearningCardAdapter(cards, this::onCardClicked);
        rvLearningCards.setAdapter(adapter);
    }

    private List<LearningCard> createLearningCards() {
        List<LearningCard> cards = new ArrayList<>();
        
        cards.add(new LearningCard("Flashcards", "Learn with interactive flashcards", R.drawable.ic_launcher_foreground, "flashcards"));
        cards.add(new LearningCard("Exercises", "Practice with fun exercises", R.drawable.ic_launcher_foreground, "exercises"));
        cards.add(new LearningCard("My Progress", "Track your learning progress", R.drawable.ic_launcher_foreground, "progress"));
        cards.add(new LearningCard("Video Lessons", "Watch educational videos", R.drawable.ic_launcher_foreground, "videos"));
        cards.add(new LearningCard("Take Test", "Test your knowledge", R.drawable.ic_launcher_foreground, "test"));
        cards.add(new LearningCard("My Badges", "View your achievements", R.drawable.ic_launcher_foreground, "badges"));
        cards.add(new LearningCard("Learning Streak", "Check your daily streak", R.drawable.ic_launcher_foreground, "streak"));
        cards.add(new LearningCard("Settings", "App settings and preferences", R.drawable.ic_launcher_foreground, "settings"));
        
        return cards;
    }

    private void setupClickListeners() {
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(StudentHomeActivity.this, ProfileUpdateActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });
    }

    private void onCardClicked(LearningCard card) {
        Intent intent = null;
        
        switch (card.getType()) {
            case "flashcards":
                intent = new Intent(this, FlashcardLearningActivity.class);
                break;
            case "exercises":
                intent = new Intent(this, ExerciseScreenActivity.class);
                break;
            case "progress":
                intent = new Intent(this, ProgressTrackingActivity.class);
                break;
            case "videos":
                intent = new Intent(this, VideoLecturesActivity.class);
                break;
            case "test":
                intent = new Intent(this, TestScreenActivity.class);
                break;
            case "badges":
                intent = new Intent(this, BadgesScreenActivity.class);
                break;
            case "streak":
                intent = new Intent(this, LearningStreakActivity.class);
                break;
            case "settings":
                intent = new Intent(this, SettingsActivity.class);
                break;
        }
        
        if (intent != null) {
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        }
    }

    // Learning Card Data Class
    public static class LearningCard {
        private String title;
        private String description;
        private int iconResId;
        private String type;

        public LearningCard(String title, String description, int iconResId, String type) {
            this.title = title;
            this.description = description;
            this.iconResId = iconResId;
            this.type = type;
        }

        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public int getIconResId() { return iconResId; }
        public String getType() { return type; }
    }

    // RecyclerView Adapter
    private static class LearningCardAdapter extends RecyclerView.Adapter<LearningCardAdapter.ViewHolder> {
        private List<LearningCard> cards;
        private OnCardClickListener listener;

        public interface OnCardClickListener {
            void onCardClick(LearningCard card);
        }

        public LearningCardAdapter(List<LearningCard> cards, OnCardClickListener listener) {
            this.cards = cards;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_learning_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            LearningCard card = cards.get(position);
            holder.tvCardTitle.setText(card.getTitle());
            holder.tvCardDescription.setText(card.getDescription());
            holder.ivCardIcon.setImageResource(card.getIconResId());
            
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCardClick(card);
                }
            });
        }

        @Override
        public int getItemCount() {
            return cards.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvCardTitle, tvCardDescription;
            ImageView ivCardIcon;

            ViewHolder(View itemView) {
                super(itemView);
                tvCardTitle = itemView.findViewById(R.id.tv_card_title);
                tvCardDescription = itemView.findViewById(R.id.tv_card_description);
                ivCardIcon = itemView.findViewById(R.id.iv_card_icon);
            }
        }
    }
} 