package org.matcha.springbackend.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Post extends Likeable {
    private UUID postID;
    private Account account;
    private Subreddit subreddit;
    private Integer upvotes;
    private Integer downvotes;
    private Integer score;
    private Integer commentCount;
    private String title;
    private String content;
    private String photoPath;
    private boolean isDeleted;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private java.util.List<Comment> comments;

    public Post(UUID postID, Account account, Subreddit subreddit, int upvotes, int downvotes, int commentCount,
                String title, String content, String photoPath, boolean isDeleted, OffsetDateTime createdAt,
                OffsetDateTime updatedAt) {
        this.postID = postID;
        this.account = account;
        this.subreddit = subreddit;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.score = score;
        this.commentCount = commentCount;
        this.title = title;
        this.content = content;
        this.photoPath = photoPath;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Post() {}

    public UUID getPostID() {
        return postID;
    }

    public Account getAccount() {
        return account;
    }

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public java.util.List<Comment> getComments() {
        return comments;
    }

    public void setPostID(UUID postId) {
        this.postID = postId;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setComments(java.util.List<Comment> comments) {
        this.comments = comments;
    }


}
