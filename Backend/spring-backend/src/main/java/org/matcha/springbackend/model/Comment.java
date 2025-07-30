package org.matcha.springbackend.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class Comment {
    private UUID commentId;
    private Account account;
    private UUID parentId;
    private UUID postId;
    private String text;
    private boolean isDeleted;
    private int upvotes;
    private int downvotes;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<Comment> comments;

    public Comment() {}

    public Comment(UUID commentId, Account account, UUID parentId, UUID postId, String text,
                   boolean isDeleted, int upvotes, int downvotes, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.commentId = commentId;
        this.account = account;
        this.parentId = parentId;
        this.postId = postId;
        this.text = text;
        this.isDeleted = isDeleted;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getCommentId() {
        return commentId;
    }

    public void setCommentId(UUID commentId) {
        this.commentId = commentId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public java.util.List<Comment> getComments() {
        return comments;
    }

    public void setComments(java.util.List<Comment> comments) {
        this.comments = comments;
    }
}
