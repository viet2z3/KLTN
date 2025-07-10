package com.example.kltn.activities;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;
import android.view.View;
import com.google.android.material.card.MaterialCardView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        MaterialCardView cardChangePassword = findViewById(R.id.card_change_password);
        cardChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        MaterialCardView cardUpdateProfile = findViewById(R.id.card_update_profile);
        cardUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ProfileUpdateActivity.class);
                startActivity(intent);
            }
        });
    }

} 