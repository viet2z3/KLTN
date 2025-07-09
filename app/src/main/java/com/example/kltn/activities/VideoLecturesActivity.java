package com.example.kltn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;


public class VideoLecturesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_lectures);

        LinearLayout layoutVideoItem1 = findViewById(R.id.layoutVideoItem1);
        LinearLayout layoutVideoItem2 = findViewById(R.id.layoutVideoItem2);
        LinearLayout layoutVideoItem3 = findViewById(R.id.layoutVideoItem3);

        layoutVideoItem1.setOnClickListener(v -> openVideoDetail(
                "Lesson 1: The Alphabet",
                "Introduction to the alphabet",
                "5:30",
                "Alphabet",
                R.drawable.video1
        ));
        layoutVideoItem2.setOnClickListener(v -> openVideoDetail(
                "Lesson 2: Counting",
                "Learn to count from 1 to 10",
                "6:10",
                "Counting",
                R.drawable.video2
        ));
        layoutVideoItem3.setOnClickListener(v -> openVideoDetail(
                "Lesson 3: Colors",
                "Learn to color",
                "4:45",
                "Colors",
                R.drawable.video3
        ));
    }

    private void openVideoDetail(String title, String description, String duration, String topic, int thumbnailResId) {
        Intent intent = new Intent(this, VideoDetail.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("duration", duration);
        intent.putExtra("topic", topic);
        intent.putExtra("thumbnailResId", thumbnailResId);
        startActivity(intent);
    }
} 