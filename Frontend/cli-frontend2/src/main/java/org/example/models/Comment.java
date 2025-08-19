package org.example.models;

import java.util.ArrayList;

public class Comment {
    private String id;
    private int displayId;
    private static int displayCounter = 0;
    private String postId;
    private String parentId;
    private String content;
    private String author;
    private int score;
    private String userVote;
    private String createdAt;
    private String updatedAt;
    private ArrayList<Comment> replies;

    public Comment() {
        displayId = displayCounter++;
        replies = new ArrayList<Comment>();
    }

    public String getId() {
        return id;
    }

    public void setDisplayId(int displayId) {
        this.displayId = displayId;
    }

    public int getDisplayId() {
        return displayId;
    }

    public String getPostId() {
        return postId;
    }

    public String getParentId() {
        return parentId;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserVote() {
        return userVote;
    }

    public void setUserVote(String userVote) {
        this.userVote = userVote;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public ArrayList<Comment> getReplies() {
        return replies;
    }

    public void addReply(Comment reply) {
        replies.add(reply);
    }
}
