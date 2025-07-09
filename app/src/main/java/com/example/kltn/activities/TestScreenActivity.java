package com.example.kltn.activities;


import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;



import com.example.kltn.R;


public class TestScreenActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_screen);

        // Xử lý sự kiện khi ấn nút Start Test
        View startTestButton = findViewById(R.id.r52en8o6v33);
        startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestScreenActivity.this, TestChoice.class);
                startActivity(intent);
            }
        });
    }
} 