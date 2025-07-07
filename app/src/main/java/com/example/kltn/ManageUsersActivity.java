package com.example.kltn;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersActivity extends AppCompatActivity {
    
    // UI Components
    private TextView tvTitle, tvTotalUsers, tvActiveUsers;
    private EditText etSearch;
    private Button btnSearch, btnFilterAll, btnFilterStudents, btnFilterTeachers, btnFilterAdmins;
    private RecyclerView rvUsers;
    
    // Data
    private List<User> allUsers;
    private List<User> filteredUsers;
    private UserAdapter userAdapter;
    private String currentFilter = "all";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        
        initializeViews();
        setupUserData();
        setupRecyclerView();
        setupEventHandlers();
        updateStatistics();
    }
    
    private void initializeViews() {
        tvTitle = findViewById(R.id.tvTitle);
        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnFilterAll = findViewById(R.id.btnFilterAll);
        btnFilterStudents = findViewById(R.id.btnFilterStudents);
        btnFilterTeachers = findViewById(R.id.btnFilterTeachers);
        btnFilterAdmins = findViewById(R.id.btnFilterAdmins);
        tvTotalUsers = findViewById(R.id.tvTotalUsers);
        tvActiveUsers = findViewById(R.id.tvActiveUsers);
        rvUsers = findViewById(R.id.rvUsers);
    }
    
    private void setupUserData() {
        allUsers = new ArrayList<>();
        allUsers.add(new User("Alice Johnson", "alice.johnson@example.com", "+1 234-567-8900", "Student", "Active", "Today at 2:30 PM"));
        allUsers.add(new User("Bob Smith", "bob.smith@example.com", "+1 234-567-8901", "Student", "Active", "Yesterday at 4:15 PM"));
        allUsers.add(new User("Charlie Brown", "charlie.brown@example.com", "+1 234-567-8902", "Student", "Inactive", "3 days ago"));
        allUsers.add(new User("Diana Prince", "diana.prince@example.com", "+1 234-567-8903", "Teacher", "Active", "Today at 9:00 AM"));
        allUsers.add(new User("Eve Wilson", "eve.wilson@example.com", "+1 234-567-8904", "Teacher", "Active", "Yesterday at 1:45 PM"));
        allUsers.add(new User("Frank Miller", "frank.miller@example.com", "+1 234-567-8905", "Administrator", "Active", "Today at 8:30 AM"));
        
        filteredUsers = new ArrayList<>(allUsers);
    }
    
    private void setupRecyclerView() {
        userAdapter = new UserAdapter(filteredUsers, this::onUserAction);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(userAdapter);
    }
    
    private void setupEventHandlers() {
        btnSearch.setOnClickListener(v -> performSearch());
        btnFilterAll.setOnClickListener(v -> setFilter("all"));
        btnFilterStudents.setOnClickListener(v -> setFilter("students"));
        btnFilterTeachers.setOnClickListener(v -> setFilter("teachers"));
        btnFilterAdmins.setOnClickListener(v -> setFilter("admins"));
    }
    
    private void performSearch() {
        String searchTerm = etSearch.getText().toString().trim();
        applyFilters(searchTerm);
    }
    
    private void setFilter(String filter) {
        currentFilter = filter;
        
        // Update button states
        btnFilterAll.setBackground(getDrawable(R.drawable.button_secondary));
        btnFilterStudents.setBackground(getDrawable(R.drawable.button_secondary));
        btnFilterTeachers.setBackground(getDrawable(R.drawable.button_secondary));
        btnFilterAdmins.setBackground(getDrawable(R.drawable.button_secondary));
        
        switch (filter) {
            case "all":
                btnFilterAll.setBackground(getDrawable(R.drawable.button_primary));
                break;
            case "students":
                btnFilterStudents.setBackground(getDrawable(R.drawable.button_primary));
                break;
            case "teachers":
                btnFilterTeachers.setBackground(getDrawable(R.drawable.button_primary));
                break;
            case "admins":
                btnFilterAdmins.setBackground(getDrawable(R.drawable.button_primary));
                break;
        }
        
        applyFilters(etSearch.getText().toString().trim());
    }
    
    private void applyFilters(String searchTerm) {
        filteredUsers.clear();
        
        for (User user : allUsers) {
            boolean matchesSearch = TextUtils.isEmpty(searchTerm) || 
                user.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                user.getEmail().toLowerCase().contains(searchTerm.toLowerCase()) ||
                user.getRole().toLowerCase().contains(searchTerm.toLowerCase());
            
            boolean matchesFilter = true;
            switch (currentFilter) {
                case "students":
                    matchesFilter = user.getRole().equalsIgnoreCase("Student");
                    break;
                case "teachers":
                    matchesFilter = user.getRole().equalsIgnoreCase("Teacher");
                    break;
                case "admins":
                    matchesFilter = user.getRole().equalsIgnoreCase("Administrator");
                    break;
            }
            
            if (matchesSearch && matchesFilter) {
                filteredUsers.add(user);
            }
        }
        
        userAdapter.notifyDataSetChanged();
    }
    
    private void updateStatistics() {
        int totalUsers = allUsers.size();
        int activeUsers = 0;
        
        for (User user : allUsers) {
            if (user.getStatus().equalsIgnoreCase("Active")) {
                activeUsers++;
            }
        }
        
        tvTotalUsers.setText(String.valueOf(totalUsers));
        tvActiveUsers.setText(String.valueOf(activeUsers));
    }
    
    private void onUserAction(User user, String action) {
        switch (action) {
            case "edit":
                editUser(user);
                break;
            case "reset_password":
                resetUserPassword(user);
                break;
            case "delete":
                deleteUser(user);
                break;
        }
    }
    
    private void editUser(User user) {
        Toast.makeText(this, "Edit user: " + user.getName(), Toast.LENGTH_SHORT).show();
    }
    
    private void resetUserPassword(User user) {
        new AlertDialog.Builder(this)
            .setTitle("Reset Password")
            .setMessage("Are you sure you want to reset password for " + user.getName() + "?")
            .setPositiveButton("Reset", (dialog, which) -> {
                Toast.makeText(this, "Password reset email sent to " + user.getEmail(), Toast.LENGTH_LONG).show();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
    
    private void deleteUser(User user) {
        new AlertDialog.Builder(this)
            .setTitle("Delete User")
            .setMessage("Are you sure you want to delete " + user.getName() + "?")
            .setPositiveButton("Delete", (dialog, which) -> {
                allUsers.remove(user);
                applyFilters(etSearch.getText().toString().trim());
                updateStatistics();
                Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
    
    // User Data Class
    public static class User {
        private String name;
        private String email;
        private String phone;
        private String role;
        private String status;
        private String lastLogin;
        
        public User(String name, String email, String phone, String role, String status, String lastLogin) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.role = role;
            this.status = status;
            this.lastLogin = lastLogin;
        }
        
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getRole() { return role; }
        public String getStatus() { return status; }
        public String getLastLogin() { return lastLogin; }
    }
    
    // User Adapter
    private static class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
        private List<User> users;
        private OnUserActionListener listener;
        
        public interface OnUserActionListener {
            void onUserAction(User user, String action);
        }
        
        public UserAdapter(List<User> users, OnUserActionListener listener) {
            this.users = users;
            this.listener = listener;
        }
        
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user, parent, false);
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            User user = users.get(position);
            holder.bind(user);
        }
        
        @Override
        public int getItemCount() {
            return users.size();
        }
        
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvUserName, tvUserRole, tvUserStatus, tvUserEmail, tvUserPhone, tvLastLogin;
            Button btnEdit, btnResetPassword, btnDelete;
            
            ViewHolder(View itemView) {
                super(itemView);
                tvUserName = itemView.findViewById(R.id.tvUserName);
                tvUserRole = itemView.findViewById(R.id.tvUserRole);
                tvUserStatus = itemView.findViewById(R.id.tvUserStatus);
                tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
                tvUserPhone = itemView.findViewById(R.id.tvUserPhone);
                tvLastLogin = itemView.findViewById(R.id.tvLastLogin);
                btnEdit = itemView.findViewById(R.id.btnEdit);
                btnResetPassword = itemView.findViewById(R.id.btnResetPassword);
                btnDelete = itemView.findViewById(R.id.btnDelete);
            }
            
            void bind(User user) {
                tvUserName.setText(user.getName());
                tvUserRole.setText(user.getRole());
                tvUserStatus.setText(user.getStatus());
                tvUserEmail.setText(user.getEmail());
                tvUserPhone.setText(user.getPhone());
                tvLastLogin.setText(user.getLastLogin());
                
                btnEdit.setOnClickListener(v -> {
                    if (listener != null) listener.onUserAction(user, "edit");
                });
                btnResetPassword.setOnClickListener(v -> {
                    if (listener != null) listener.onUserAction(user, "reset_password");
                });
                btnDelete.setOnClickListener(v -> {
                    if (listener != null) listener.onUserAction(user, "delete");
                });
            }
        }
    }
} 