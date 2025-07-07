package com.example.kltn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Button btnLanguageEnglish, btnLanguageVietnamese;
    private Switch switchNotifications, switchSound;
    private Button btnChangePassword, btnBack;
    private TextView tvAppVersion;
    
    private String userEmail;
    private String currentLanguage = "English";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get user email from intent
        userEmail = getIntent().getStringExtra("user_email");

        initViews();
        setupClickListeners();
        loadSettings();
    }

    private void initViews() {
        btnLanguageEnglish = findViewById(R.id.btn_language_english);
        btnLanguageVietnamese = findViewById(R.id.btn_language_vietnamese);
        switchNotifications = findViewById(R.id.switch_notifications);
        switchSound = findViewById(R.id.switch_sound);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnBack = findViewById(R.id.btn_back);
        tvAppVersion = findViewById(R.id.tv_app_version);
    }

    private void setupClickListeners() {
        // Language selection
        btnLanguageEnglish.setOnClickListener(v -> selectLanguage("English"));
        btnLanguageVietnamese.setOnClickListener(v -> selectLanguage("Vietnamese"));

        // Notification settings
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNotificationSettings(isChecked, switchSound.isChecked());
            showSettingsSavedMessage();
        });

        switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNotificationSettings(switchNotifications.isChecked(), isChecked);
            showSettingsSavedMessage();
        });

        // Change password
        btnChangePassword.setOnClickListener(v -> openChangePassword());

        // Back button
        btnBack.setOnClickListener(v -> finish());
    }

    private void openChangePassword() {
        Intent intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
        intent.putExtra("user_email", userEmail);
        startActivity(intent);
    }

    private void loadSettings() {
        // Set app version
        tvAppVersion.setText("Version 1.0.0");

        // Load saved settings (in a real app, this would come from SharedPreferences)
        loadLanguageSettings();
        loadNotificationSettings();
    }

    private void loadLanguageSettings() {
        // In a real app, load from SharedPreferences
        updateLanguageButtonStates();
    }

    private void loadNotificationSettings() {
        // In a real app, load from SharedPreferences
        // For demo, set default values
        switchNotifications.setChecked(true);
        switchSound.setChecked(true);
    }

    private void selectLanguage(String language) {
        currentLanguage = language;
        updateLanguageButtonStates();
        saveLanguageSettings();
        showLanguageChangedMessage(language);
    }

    private void updateLanguageButtonStates() {
        // Reset both buttons to secondary style
        btnLanguageEnglish.setBackground(getDrawable(R.drawable.button_secondary));
        btnLanguageVietnamese.setBackground(getDrawable(R.drawable.button_secondary));

        // Highlight selected language
        if ("English".equals(currentLanguage)) {
            btnLanguageEnglish.setBackground(getDrawable(R.drawable.button_primary));
        } else if ("Vietnamese".equals(currentLanguage)) {
            btnLanguageVietnamese.setBackground(getDrawable(R.drawable.button_primary));
        }
    }

    private void saveLanguageSettings() {
        // In a real app, save to SharedPreferences
        // For demo, just show a message
        showSettingsSavedMessage();
    }

    private void saveNotificationSettings(boolean notificationsEnabled, boolean soundEnabled) {
        // In a real app, save to SharedPreferences
        // For demo, just show a message
    }

    private void showSettingsSavedMessage() {
        Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
    }

    private void showLanguageChangedMessage(String language) {
        String message = "Language changed to " + language;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
} 