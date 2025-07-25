package org.matcha.springbackend.model;

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
    private String createdAt;

    public Subreddit(UUID id, Account account, String displayName,
                     String description, boolean isDeleted, Integer memberCount,
                     Integer postCount, String iconUrl, String createdAt) {
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
}
