package com.example.kltn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kltn.R;

public class TestChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_choice);

        LinearLayout btnStartBeginner = findViewById(R.id.btn_start_beginner);
        LinearLayout btnStartIntermediate = findViewById(R.id.btn_start_intermediate);
        LinearLayout btnStartAdvanced = findViewById(R.id.btn_start_advanced);

        btnStartBeginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestChoice.this, TestDetail.class);
                intent.putExtra("test_level", "beginner");
                startActivity(intent);
            }
        });

        btnStartIntermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestChoice.this, TestDetail.class);
                intent.putExtra("test_level", "intermediate");
                startActivity(intent);
            }
        });

        btnStartAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestChoice.this, TestDetail.class);
                intent.putExtra("test_level", "advanced");
                startActivity(intent);
            }
        });
    }
}