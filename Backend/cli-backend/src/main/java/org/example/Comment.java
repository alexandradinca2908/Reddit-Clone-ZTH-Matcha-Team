package org.example;

import java.util.List;
import java.util.ArrayList;

public class Comment implements Likeable {
    private int voteCount = 0;
    private int replyCount = 0;
    private String body;
    private List<Comment> replies;
    private User author;
    private Post parentPost;
    private Comment parentComment;

    public Comment(String body, User author, Post parentPost) {
        this.body = body;
        this.author = author;
        this.parentPost = parentPost;
        this.parentComment = null;
        this.replies = new ArrayList<>();
    }

    // Constructor pentru reply-uri
    public Comment(String body, User author, Post parentPost, Comment parentComment) {
        this.body = body;
        this.author = author;
        this.parentPost = parentPost;
        this.parentComment = parentComment;
        this.replies = new ArrayList<>();
    }

    @Override
    public void upvote() {
        voteCount++;
    }

    @Override
    public void downvote() {
        voteCount--;
    }

    @Override
    public int getVotes() {
        return voteCount;
    }

    public void addReply(Comment reply) {
        this.replies.add(reply);
    }

    public List<Comment> getReplies() {
        return new ArrayList<>(replies);
    }

    public String getComment() {
        return body;
    }

    public String getBody() {
        return body;
    }

    public User getAuthor() {
        return author;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isReply() {
        return parentComment != null;
    }

    public boolean hasReplies() {
        return !replies.isEmpty();
    }

}
