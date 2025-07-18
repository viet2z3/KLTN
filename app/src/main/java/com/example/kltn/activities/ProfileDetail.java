package com.example.kltn.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.kltn.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProfileDetail extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        String userId = getIntent().getStringExtra("user_id");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Lấy từng include item
        View itemFullName = findViewById(R.id.itemFullName);
        View itemGender = findViewById(R.id.itemGender);
        View itemEmail = findViewById(R.id.itemEmail);
        View itemRole = findViewById(R.id.itemRole);
        View itemClassName = findViewById(R.id.itemClassName);
        View itemLevel = findViewById(R.id.itemLevel);
        View itemTeacher = findViewById(R.id.itemTeacher);
        TextView tvChangePhoto = findViewById(R.id.tvChangePhoto);
        ImageView imgAvatar = findViewById(R.id.imgAvatar);
        TextView tvName = findViewById(R.id.tvName);

        // Lấy thông tin user từ Firestore
        db.collection("users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String fullName = documentSnapshot.getString("full_name");
                String email = documentSnapshot.getString("email");
                String role = documentSnapshot.getString("role");
                String gender = documentSnapshot.getString("gender");
                String avatarUrl = documentSnapshot.getString("avatar_url");
                String avatarBase64 = documentSnapshot.getString("avatar_base64");

                setProfileInfo(itemFullName, "Full Name", fullName);
                setProfileInfo(itemGender, "Gender", gender);
                setProfileInfo(itemEmail, "Email", email);
                setProfileInfo(itemRole, "Role", role);

                // Xử lý class, level, teacher chỉ cho student
                if ("student".equalsIgnoreCase(role)) {
                    String className = documentSnapshot.getString("class_name");
                    String level = documentSnapshot.getString("level");
                    String teacherName = documentSnapshot.getString("teacher_name");

                    setProfileInfo(itemClassName, "Class", (className != null && !className.isEmpty()) ? className : "Chưa được phân lớp");
                    setProfileInfo(itemLevel, "Level", (level != null && !level.isEmpty()) ? level : "Chưa được phân lớp");
                    setProfileInfo(itemTeacher, "Teacher", (teacherName != null && !teacherName.isEmpty()) ? teacherName : "Chưa được phân lớp");

                    itemClassName.setVisibility(View.VISIBLE);
                    itemLevel.setVisibility(View.VISIBLE);
                    itemTeacher.setVisibility(View.VISIBLE);
                } else {
                    itemClassName.setVisibility(View.GONE);
                    itemLevel.setVisibility(View.GONE);
                    itemTeacher.setVisibility(View.GONE);
                }

                tvName.setText(fullName);
                // Hiển thị avatar: ưu tiên avatar_base64, nếu không có thì dùng avatar_url
                if (avatarBase64 != null && !avatarBase64.isEmpty()) {
                    byte[] decodedString = Base64.decode(avatarBase64, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    // Sử dụng Glide để bo tròn bitmap
                    Glide.with(this)
                        .load(decodedByte)
                        .placeholder(R.drawable.user)
                        .circleCrop()
                        .into(imgAvatar);
                } else if (avatarUrl != null && !avatarUrl.isEmpty()) {
                    Glide.with(this)
                        .load(avatarUrl)
                        .placeholder(R.drawable.user)
                        .circleCrop()
                        .into(imgAvatar);
                } else {
                    imgAvatar.setImageResource(R.drawable.user);
                }
            }
        });

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
                try {
                    // Đọc ảnh, resize nhỏ lại (ví dụ 256x256), chuyển sang Base64
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    // Nén mạnh hơn (chất lượng 60)
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                    byte[] imageBytes = baos.toByteArray();
                    // Kiểm tra kích thước sau nén
                    int imageSizeKB = imageBytes.length / 1024;
                    if (imageSizeKB > 500) {
                        Toast.makeText(this, "Ảnh quá lớn sau nén (" + imageSizeKB + "KB). Vui lòng chọn ảnh nhỏ hơn!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    // Hiển thị ảnh mới ngay lập tức (dùng Glide để bo tròn)
                    Glide.with(this)
                        .load(scaledBitmap)
                        .placeholder(R.drawable.user)
                        .circleCrop()
                        .into(imgAvatar);

                    // Lưu base64 vào Firestore
                    String userId = getIntent().getStringExtra("user_id");
                    // Xóa avatar cũ trước khi lưu mới
                    FirebaseFirestore.getInstance().collection("users").document(userId)
                        .update("avatar_base64", "")
                        .addOnSuccessListener(aVoid -> {
                            // Lưu base64 mới vào Firestore
                            FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(userId)
                                .update("avatar_base64", base64Image);
                        });
                } catch (IOException e) {
                    Toast.makeText(this, "Lỗi đọc ảnh: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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