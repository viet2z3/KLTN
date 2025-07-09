package com.example.kltn.activities;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kltn.R;

public class VideoDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        ImageView ivThumbnail = findViewById(R.id.rh49g4vrwbfe);
        TextView tvTitle = findViewById(R.id.rfxxxes5byk5);
        TextView tvDescription = findViewById(R.id.r6nrzucmp76v);
        TextView tvDuration = null;
        TextView tvTopic = null;
        // Nếu có view duration/topic thì ánh xạ thêm

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String duration = intent.getStringExtra("duration");
        String topic = intent.getStringExtra("topic");
        int thumbnailResId = intent.getIntExtra("thumbnailResId", R.drawable.video1);

        tvTitle.setText(title);
        tvDescription.setText(description);
        ivThumbnail.setImageResource(thumbnailResId);
        // Nếu có view duration/topic thì setText tương ứng
    }
}