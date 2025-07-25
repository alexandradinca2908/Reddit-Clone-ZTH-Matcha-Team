package org.matcha.springbackend.model;

public class Subreddit {
    private String id;
    private String name;
    private String displayName;
    private String description;
    private Integer memberCount;
    private Integer postCount;
    private String iconUrl;
    private String createdAt;

    public Subreddit(String id, String name, String displayName, String description,
                     Integer memberCount, Integer postCount, String iconUrl, String createdAt) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.memberCount = memberCount;
        this.postCount = postCount;
        this.iconUrl = iconUrl;
        this.createdAt = createdAt;
    }
}
