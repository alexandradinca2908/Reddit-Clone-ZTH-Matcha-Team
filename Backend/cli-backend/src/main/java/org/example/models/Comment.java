package org.example.models;

import org.example.services.PostService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Comment extends Likeable {
    private static int commentCounter = 0;
    private int displayIndex;
    private UUID commentID;
    private String commentText;
    private final Post parentPost;
    private final Comment parentComment;
    private String username;
    public HashMap<String, Integer> votingUserID;
    public ArrayList<Comment> replyList;

    public Comment(Post parentPost, String username, String commentText) {
        this.parentPost = parentPost;
        this.parentComment = null;
        this.username = username;
        this.commentText = commentText;
        this.replyList = new ArrayList<>();
        this.voteCount = 0;
        this.votingUserID = new HashMap<>();
        displayIndex = ++commentCounter;
    }

    public Comment(Comment parentComment, String username, String commentText) {
        this.parentPost = null;
        this.parentComment = parentComment;
        this.username = username;
        this.commentText = commentText;
        this.replyList = new ArrayList<>();
        this.voteCount = 0;
        this.votingUserID = new HashMap<>();
        displayIndex = ++commentCounter;
    }

    public String getCommentText() {
        return commentText;
    }

    public UUID getCommentID() {
        return commentID;
    }

    public int getDisplayIndex() {
        return displayIndex;
    }

    public Post getParentPost() {
        return parentPost;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Comment> getReplyList() {
        return replyList;
    }

    public void addReply(Comment reply) {
        this.replyList.add(reply);
    }

    public void setCommentID(UUID commentId) {
        this.commentID = commentId;
    }

    public Comment getParentComment() {
        return this.parentComment;
    }

    public static Comment findById(UUID id) {
        for (Post post : PostService.posts) {
            for (Comment comment : post.getCommentList()) {
                if (comment.getCommentID() == id) {
                    return comment;
                }
                for (Comment reply : comment.getReplyList()) {
                    if (reply.getCommentID() == id) {
                        return reply;
                    }
                }
            }
        }
        return null;
    }
}