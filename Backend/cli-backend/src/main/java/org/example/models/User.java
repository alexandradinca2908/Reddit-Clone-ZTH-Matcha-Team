package org.example.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private UUID profileId;
    private String username;
    private String email;
    private String password;
    private String photoPath;
    private boolean isDeleted;
    private LocalDateTime createdAt;

    public User(String userName, String email, String password) {
        this.profileId = null;
        this.username = userName;
        this.email = email;
        this.password = password;
        this.photoPath = null;
        this.isDeleted = false;
        this.createdAt = LocalDateTime.now();
    }

    public User(UUID profileId, String username, String email, String password,
                String photoPath, boolean isDeleted, LocalDateTime createdAt) {
        this.profileId = profileId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.photoPath = photoPath;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setUUID(UUID uuid) {
        this.profileId = uuid;
    }
}
