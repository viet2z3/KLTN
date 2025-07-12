package com.example.kltn.models;

public class User {
    private String name;
    private String email;
    private String phone;
    private String role;
    private String status;
    private String lastLogin;
    private String avatar;
    private String password;

    public User(String name, String email, String phone, String role, String status, String lastLogin) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.lastLogin = lastLogin;
        this.avatar = "";
        this.password = "";
    }

    public User(String name, String email, String phone, String role, String status, String lastLogin, String avatar, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.lastLogin = lastLogin;
        this.avatar = avatar;
        this.password = password;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public String getLastLogin() { return lastLogin; }
    public String getAvatar() { return avatar; }
    public String getPassword() { return password; }
    
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public void setPassword(String password) { this.password = password; }
} 