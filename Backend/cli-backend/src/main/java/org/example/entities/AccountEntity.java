package org.example.entities;

/*
CREATE TABLE account (
    account_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username TEXT UNIQUE NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    photo_path TEXT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
 */

import java.time.OffsetDateTime;
import java.util.UUID;

public class AccountEntity {

    private UUID accountId;
    private String username;
    private String email;
    private String password;
    private String photoPath;
    private boolean isDeleted;
    private OffsetDateTime createdAt;

    public AccountEntity(UUID accountId, String username, String email, String password,
                         String photoPath, boolean isDeleted, OffsetDateTime createdAt) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.photoPath = photoPath;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
