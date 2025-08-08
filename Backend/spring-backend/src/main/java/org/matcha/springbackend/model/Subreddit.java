package org.matcha.springbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class Subreddit {
    private UUID id;
    private Account account;
    private String name;
    private String displayName;
    private String description;
    private boolean isDeleted;
    private Integer memberCount;
    private Integer postCount;
    private String iconUrl;
    private OffsetDateTime createdAt;

    public Subreddit() {}

    public Subreddit(UUID id, String name, String displayName, Account account,
                     String description, boolean isDeleted, Integer memberCount,
                     Integer postCount, String iconUrl, OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
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
