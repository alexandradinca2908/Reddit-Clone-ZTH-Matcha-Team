package org.example.entities;

/*
CREATE TABLE subreddit (
    subreddit_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID REFERENCES account(account_id) ON DELETE SET NULL,
    name TEXT UNIQUE NOT NULL,
    description TEXT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
 */

import java.time.OffsetDateTime;
import java.util.UUID;

public class SubredditEntity {

    private UUID subredditId;
    private UUID accountId;      // referință către account(account_id)
    private String name;
    private String description;
    private boolean isDeleted;
    private OffsetDateTime createdAt;

    public SubredditEntity(UUID subredditId, UUID accountId, String name, String description,
                           boolean isDeleted, OffsetDateTime createdAt) {
        this.subredditId = subredditId;
        this.accountId = accountId;
        this.name = name;
        this.description = description;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    public UUID getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(UUID subredditId) {
        this.subredditId = subredditId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
