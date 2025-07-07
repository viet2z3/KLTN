package com.example.kltn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileUpdateActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTitle;
    private ImageView ivProfilePicture;
    private EditText etFullName, etEmail, etPhone;
    private Switch switchEmailNotifications, switchPushNotifications;
    private Button btnChangePicture, btnSaveProfile, btnBack;
    
    // Profile data
    private String currentName = "John Doe";
    private String currentEmail = "john.doe@example.com";
    private String currentPhone = "+1 234-567-8900";
    private boolean emailNotifications = true;
    private boolean pushNotifications = true;
    private Uri profilePictureUri = null;
    
    // Activity result launcher for image selection
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        
        initializeViews();
        setupEventHandlers();
        setupImagePicker();
        loadCurrentProfile();
    }
    
    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        switchEmailNotifications = findViewById(R.id.switchEmailNotifications);
        switchPushNotifications = findViewById(R.id.switchPushNotifications);
        btnChangePicture = findViewById(R.id.btnChangePicture);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnBack = findViewById(R.id.btnBack);
    }
    
    private void setupEventHandlers() {
        btnChangePicture.setOnClickListener(v -> selectImage());
        btnSaveProfile.setOnClickListener(v -> saveProfile());
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void setupImagePicker() {
        imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    profilePictureUri = result.getData().getData();
                    if (profilePictureUri != null) {
                        ivProfilePicture.setImageURI(profilePictureUri);
                    }
                }
            }
        );
    }
    
    private void loadCurrentProfile() {
        // Load current profile data
        etFullName.setText(currentName);
        etEmail.setText(currentEmail);
        etPhone.setText(currentPhone);
        
        // Set notification preferences
        switchEmailNotifications.setChecked(emailNotifications);
        switchPushNotifications.setChecked(pushNotifications);
        
        // Set default profile picture
        ivProfilePicture.setImageResource(R.drawable.ic_launcher_foreground);
    }
    
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }
    
    private void saveProfile() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        
        // Clear previous errors
        etFullName.setError(null);
        etEmail.setError(null);
        etPhone.setError(null);
        
        // Validate full name
        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("Please enter your full name");
            etFullName.requestFocus();
            return;
        }
        
        if (fullName.length() < 2) {
            etFullName.setError("Name must be at least 2 characters");
            etFullName.requestFocus();
            return;
        }
        
        // Validate email
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Please enter your email");
            etEmail.requestFocus();
            return;
        }
        
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            etEmail.requestFocus();
            return;
        }
        
        // Validate phone
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Please enter your phone number");
            etPhone.requestFocus();
            return;
        }
        
        // Get notification preferences
        boolean emailNotif = switchEmailNotifications.isChecked();
        boolean pushNotif = switchPushNotifications.isChecked();
        
        // Save profile
        performProfileSave(fullName, email, phone, emailNotif, pushNotif);
    }
    
    private void performProfileSave(String fullName, String email, String phone, boolean emailNotif, boolean pushNotif) {
        // Update current profile data
        currentName = fullName;
        currentEmail = email;
        currentPhone = phone;
        emailNotifications = emailNotif;
        pushNotifications = pushNotif;
        
        // Show success message
        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_LONG).show();
        
        // Close activity
        finish();
    }
} 