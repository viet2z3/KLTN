package com.example.kltn.models;

public class User {
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