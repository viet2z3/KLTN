package com.example.kltn.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kltn.R;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

public class ProfileDetail extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        // Lấy từng include item
        View itemFullName = findViewById(R.id.itemFullName);
        View itemGender = findViewById(R.id.itemGender);
        View itemEmail = findViewById(R.id.itemEmail);
        View itemRole = findViewById(R.id.itemRole);
        View itemClassName = findViewById(R.id.itemClassName);
        TextView tvChangePhoto = findViewById(R.id.tvChangePhoto);

        // Set label và value cho từng dòng
        setProfileInfo(itemFullName, "Full Name", "Ethan Carter");
        setProfileInfo(itemGender, "Gender", "Male");
        setProfileInfo(itemEmail, "Email", "ethan.carter@example.com");
        setProfileInfo(itemRole, "Role", "Student");
        setProfileInfo(itemClassName, "Class Name", "Class 3A");
        tvChangePhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                ImageView imgAvatar = findViewById(R.id.imgAvatar);
                Glide.with(this)
                    .load(selectedImageUri)
                    .transform(new CircleCrop())
                    .into(imgAvatar);
            }
        }
    }

    private void setProfileInfo(View item, String label, String value) {
        TextView tvLabel = item.findViewById(R.id.tvLabel);
        TextView tvValue = item.findViewById(R.id.tvValue);
        tvLabel.setText(label);
        tvValue.setText(value);
    }
}