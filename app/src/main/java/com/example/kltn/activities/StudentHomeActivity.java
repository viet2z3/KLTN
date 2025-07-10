package com.example.kltn.activities;

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

import com.example.kltn.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class StudentHomeActivity extends AppCompatActivity {

    private ImageView ivAvatar;
    private TextView tvUserName;
    private ImageView imgFlashCard, imgBaitap, imgTest, imgVideo, imgBadge, imgProgress;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("user_email");

        // Initialize views
        initViews();

        // Setup BottomNavigationView
        setupBottomNavigation();
    }

    private void initViews() {
        ivAvatar = findViewById(R.id.r8xjyhkhtcc); // Avatar
        tvUserName = findViewById(R.id.rlff09bfus9); // Hello, <name>
        imgFlashCard = findViewById(R.id.imgFlashCard);
        imgBaitap = findViewById(R.id.imgBaitap);
        imgTest = findViewById(R.id.imgTest);
        imgVideo = findViewById(R.id.imgVideo);
        imgBadge = findViewById(R.id.imgBadge);
        imgProgress = findViewById(R.id.imgThongke);
        // Nếu muốn set tên động:
        // tvUserName.setText("Hello, " + <userName>);
        setupImageClicks();
        ivAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileDetail.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Intent intent = null;
            if (id == R.id.nav_home) {
                // Ở lại màn hình này, có thể reload nếu muốn
                return true;
            } else if (id == R.id.nav_learn) {
                intent = new Intent(this, FlashcardLearningActivity.class);
            } else if (id == R.id.nav_badge) {
                intent = new Intent(this, BadgesScreenActivity.class);
            } else if (id == R.id.nav_setting) {
                intent = new Intent(this, SettingsActivity.class);
            }
            if (intent != null) {
                intent.putExtra("user_email", userEmail);
                startActivity(intent);
                // Optional: không giữ lại activity hiện tại
                // finish();
            }
            return true;
        });
        // Đặt mục Home được chọn mặc định
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private void setupImageClicks() {
        imgFlashCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, FlashcardLearningActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });
        imgBaitap.setOnClickListener(v -> {
            Intent intent = new Intent(this, ExerciseScreenActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });
        imgTest.setOnClickListener(v -> {
            Intent intent = new Intent(this, TestScreenActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });
        imgVideo.setOnClickListener(v -> {
            Intent intent = new Intent(this, VideoLecturesActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });
        imgBadge.setOnClickListener(v -> {
            Intent intent = new Intent(this, BadgesScreenActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });
        imgProgress.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProgressTrackingActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });
    }
} 