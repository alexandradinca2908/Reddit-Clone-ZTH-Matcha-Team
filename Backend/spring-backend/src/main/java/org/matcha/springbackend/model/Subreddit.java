package org.matcha.springbackend.model;

import org.matcha.springbackend.entities.SubredditEntity;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Subreddit {
    private UUID id;
    private Account account; // Optional: if you want to include the creator's account
    private String name;
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

    public Subreddit(UUID id, String name, String displayName,
                     String description, boolean isDeleted, Integer memberCount,
                     Integer postCount, String iconUrl, OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
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

    public UUID getSubredditId() {
        return id;
    }
}
