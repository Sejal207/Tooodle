package com.example.mad_toodle.models;

public class User {
    private String userId;
    private String email;
    private String name;
    private long createdAt;

    // Required empty constructor for Firebase
    public User() {}

    public User(String userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
} 