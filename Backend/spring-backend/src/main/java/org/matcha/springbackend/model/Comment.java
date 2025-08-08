package org.matcha.springbackend.model;

import org.matcha.springbackend.enums.VoteType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class Comment {
    private UUID commentId;
    private Account account;
    private UUID parentCommentId;
    private UUID postId;
    private String text;
    private boolean isDeleted;
    private int upvotes;
    private int downvotes;
    private int score;
    private VoteType userVote;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<Comment> replies;

    public Comment() {}

    public Comment(UUID commentId, Account account, UUID parentCommentId, UUID postId, String text,
                   boolean isDeleted, int upvotes, int downvotes, int score, VoteType voteType,
                   OffsetDateTime createdAt, OffsetDateTime updatedAt,  List<Comment> replies) {
        this.commentId = commentId;
        this.account = account;
        this.parentCommentId = parentCommentId;
        this.postId = postId;
        this.text = text;
        this.isDeleted = isDeleted;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.score = score;
        this.userVote = voteType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.replies = replies;
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

    public UUID getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(UUID parentCommentId) {
        this.parentCommentId = parentCommentId;
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

    public VoteType getUserVote() {
        return userVote;
    }

    public void setUserVote(VoteType userVote) {
        this.userVote = userVote;
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

    public java.util.List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(java.util.List<Comment> replies) {
        this.replies = replies;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
