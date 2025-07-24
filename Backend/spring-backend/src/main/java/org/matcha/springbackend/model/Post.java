package org.matcha.springbackend.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Post extends Likeable {
    UUID postID;
    Account account;
    Subreddit subreddit;
    String title;
    String content;
    String photoPath;
    boolean isDeleted;
    OffsetDateTime createdAt;

    public Post(UUID postID, Account account, Subreddit subreddit, String title,
                String content, String photoPath, boolean isDeleted, OffsetDateTime createdAt) {
        this.postID = postID;
        this.account = account;
        this.subreddit = subreddit;
        this.title = title;
        this.content = content;
        this.photoPath = photoPath;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }
}
