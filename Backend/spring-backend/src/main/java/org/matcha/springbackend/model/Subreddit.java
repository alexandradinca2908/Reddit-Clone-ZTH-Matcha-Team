package org.matcha.springbackend.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Subreddit {
    private UUID id;
    private Account account;
    private String displayName;
    private String description;
    private boolean isDeleted;
    private Integer memberCount;
    private Integer postCount;
    private String iconUrl;
    private OffsetDateTime createdAt;

    public Subreddit() {
        // Default constructor
    }

    public Subreddit(UUID id, Account account, String displayName,
                     String description, boolean isDeleted, Integer memberCount,
                     Integer postCount, String iconUrl, OffsetDateTime createdAt) {
        this.id = id;
        this.account = account;
        this.displayName = displayName;
        this.description = description;
        this.isDeleted = isDeleted;
        this.memberCount = memberCount;
        this.postCount = postCount;
        this.iconUrl = iconUrl;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
