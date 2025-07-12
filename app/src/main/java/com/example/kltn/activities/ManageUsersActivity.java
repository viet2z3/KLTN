package com.example.kltn.activities;

import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import com.example.kltn.R;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kltn.adapters.UserAdapter;
import com.example.kltn.models.User;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.ImageView;


public class ManageUsersActivity extends AppCompatActivity implements UserAdapter.OnUserActionListener {
    
    private List<User> userList;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        RecyclerView rvUsers = findViewById(R.id.rv_users);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        // Fix cứng dữ liệu user mẫu
        userList = new ArrayList<>();
        userList.add(new User("Ms. Emily Carter", "emily.carter@example.com", "(555) 123-4567", "Teacher", "Active", ""));
        userList.add(new User("Ethan Harper", "ethan.harper@example.com", "(555) 987-6543", "Student", "Active", ""));
        userList.add(new User("Olivia Bennett", "olivia.bennett@example.com", "(555) 246-8013", "Student", "Active", ""));
        userList.add(new User("Mr. David Clark", "david.clark@example.com", "(555) 369-1215", "Teacher", "Active", ""));
        userList.add(new User("Sophia Turner", "sophia.turner@example.com", "(555) 159-7531", "Student", "Active", ""));
        userList.add(new User("Noah Foster", "noah.foster@example.com", "(555) 753-9512", "Student", "Active", ""));

        adapter = new UserAdapter(userList, this);
        rvUsers.setAdapter(adapter);

        // Setup nút thêm user
        findViewById(R.id.btn_add_user).setOnClickListener(v -> showAddUserDialog());
    }

    private void showAddUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_user, null);
        
        // Setup spinner cho role
        Spinner spinnerRole = dialogView.findViewById(R.id.spinner_role);
        String[] roles = {"Student", "Teacher", "Admin"};
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(roleAdapter);
        
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        
        // Xử lý nút Cancel
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        
        // Xử lý nút Add User
        Button btnAdd = dialogView.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {
            EditText etName = dialogView.findViewById(R.id.et_user_name);
            EditText etEmail = dialogView.findViewById(R.id.et_user_email);
            EditText etPhone = dialogView.findViewById(R.id.et_user_phone);
            EditText etPassword = dialogView.findViewById(R.id.et_user_password);
            
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String role = spinnerRole.getSelectedItem().toString();
            
            // Validation
            if (name.isEmpty()) {
                etName.setError("Name is required");
                return;
            }
            if (email.isEmpty()) {
                etEmail.setError("Email is required");
                return;
            }
            if (phone.isEmpty()) {
                etPhone.setError("Phone is required");
                return;
            }
            if (password.isEmpty()) {
                etPassword.setError("Password is required");
                return;
            }
            
            // Thêm user mới với password
            User newUser = new User(name, email, phone, role, "Active", "", "", password);
            userList.add(newUser);
            adapter.notifyItemInserted(userList.size() - 1);
            
            Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        
        dialog.show();
    }

    @Override
    public void onUserAction(User user, String action) {
        if ("edit".equals(action)) {
            showEditDialog(user);
        }
    }

    private void showEditDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);
        
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        
        // Xử lý nút "Sửa thông tin"
        View btnEditUser = dialogView.findViewById(R.id.btn_edit_user);
        btnEditUser.setOnClickListener(v -> {
            dialog.dismiss();
            showEditUserDialog(user);
        });
        
        // Xử lý nút "Xóa người dùng"
        View btnDeleteUser = dialogView.findViewById(R.id.btn_delete_user);
        btnDeleteUser.setOnClickListener(v -> {
            dialog.dismiss();
            showDeleteConfirmation(user);
        });
        
        dialog.show();
    }
    
    private void showEditUserDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_user_info, null);
        
        // Pre-fill thông tin hiện tại
        EditText etName = dialogView.findViewById(R.id.et_edit_user_name);
        EditText etEmail = dialogView.findViewById(R.id.et_edit_user_email);
        EditText etPhone = dialogView.findViewById(R.id.et_edit_user_phone);
        EditText etPassword = dialogView.findViewById(R.id.et_edit_user_password);
        ImageView ivAvatar = dialogView.findViewById(R.id.iv_user_avatar);
        
        etName.setText(user.getName());
        etEmail.setText(user.getEmail());
        etPhone.setText(user.getPhone());
        etPassword.setText(user.getPassword()); // Pre-fill mật khẩu hiện tại
        
        // Hiển thị avatar hiện tại (nếu có)
        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            // TODO: Load avatar từ URL hoặc resource
            // ivAvatar.setImageURI(Uri.parse(user.getAvatar()));
        }
        
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        
        // Xử lý nút Change Photo
        Button btnChangeAvatar = dialogView.findViewById(R.id.btn_change_avatar);
        btnChangeAvatar.setOnClickListener(v -> {
            // TODO: Mở gallery hoặc camera để chọn ảnh
            Toast.makeText(this, "Photo selection feature coming soon", Toast.LENGTH_SHORT).show();
        });
        
        // Xử lý nút Cancel
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel_edit);
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        
        // Xử lý nút Save Changes
        Button btnSave = dialogView.findViewById(R.id.btn_save_edit);
        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString().trim();
            String newEmail = etEmail.getText().toString().trim();
            String newPhone = etPhone.getText().toString().trim();
            String newPassword = etPassword.getText().toString().trim();
            
            // Validation
            if (newName.isEmpty()) {
                etName.setError("Name is required");
                return;
            }
            if (newEmail.isEmpty()) {
                etEmail.setError("Email is required");
                return;
            }
            if (newPhone.isEmpty()) {
                etPhone.setError("Phone is required");
                return;
            }
            if (newPassword.isEmpty()) {
                etPassword.setError("Password is required");
                return;
            }
            
            // Cập nhật thông tin user
            user.setName(newName);
            user.setEmail(newEmail);
            user.setPhone(newPhone);
            user.setPassword(newPassword);
            
            // Cập nhật RecyclerView
            int position = userList.indexOf(user);
            if (position != -1) {
                adapter.notifyItemChanged(position);
            }
            
            Toast.makeText(this, "User information updated successfully", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        
        dialog.show();
    }
    
    private void showDeleteConfirmation(User user) {
        new AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa người dùng " + user.getName() + "?")
            .setPositiveButton("Xóa", (dialog, which) -> {
                Toast.makeText(this, "Đã xóa: " + user.getName(), Toast.LENGTH_SHORT).show();
                // TODO: Xử lý xóa user khỏi danh sách
            })
            .setNegativeButton("Hủy", null)
            .show();
    }
    

} 