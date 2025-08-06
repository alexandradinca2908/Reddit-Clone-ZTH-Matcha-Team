package org.matcha.springbackend.model;

import org.matcha.springbackend.enums.VoteType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class Post {
    private UUID postID;
    private String title;
    private String content;
    private Account account;
    private Subreddit subreddit;
    private Integer upvotes;
    private Integer downvotes;
    private Integer commentCount;
    private VoteType voteType;
    private String photoPath;
    private boolean isDeleted;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<Comment> comments;

    public Post(UUID postID, String title, String content, Account account, Subreddit subreddit,
                int upvotes, int downvotes, int commentCount, VoteType voteType, String photoPath,
                boolean isDeleted, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.postID = postID;
        this.title = title;
        this.content = content;
        this.account = account;
        this.subreddit = subreddit;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.commentCount = commentCount;
        this.voteType = voteType;
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

    public List<Comment> getComments() {
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

    public Integer getUpvotes() {
        return upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }
}
