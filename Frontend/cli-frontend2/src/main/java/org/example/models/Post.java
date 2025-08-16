package org.example.models;

import java.util.ArrayList;

public class Post {
    private String id;
    private String displayId;
    private static int displayIdCounter = 0;
    private String title;
    private String content;
    private String author;
    private String subreddit;
    private int score;
    private int commentCount;
    private String userVote;
    private String createdAt;
    private String updatedAt;
    private ArrayList<Comment> comments;

    public Post() {
        displayId = Integer.toString(displayIdCounter++);
        comments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public String getTitle() {
        return title;
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

    public int getCommentCount() {
        return commentCount;
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
