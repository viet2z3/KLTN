package com.example.kltn.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kltn.R;

public class TestDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_detail);
        // Nhận loại test từ Intent
        String testLevel = getIntent().getStringExtra("test_level");
        // TODO: Sử dụng testLevel để hiển thị nội dung phù hợp

    }
}