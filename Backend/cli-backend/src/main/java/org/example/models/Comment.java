package org.example.models;

import org.example.services.PostService;

import java.util.ArrayList;
import java.util.HashMap;

public class Comment extends Likeable {
    private static int commentCounter = 0;
    private int commentID;
    private String commentText;
    private final Post parentPost;
    private final Comment parentComment;
    private final User parentUser;
    public HashMap<Integer, Integer> votingUserID;
    public ArrayList<Comment> replyList;

    public Comment(Post parentPost, User parentUser, String commentText) {
        this.parentPost = parentPost;
        this.parentComment = null;
        this.parentUser = parentUser;
        this.commentText = commentText;
        this.replyList = new ArrayList<>();
        this.voteCount = 0;
        this.votingUserID = new HashMap<>();
        this.commentID = commentCounter++;
    }

    public Comment(Comment parentComment, User parentUser, String commentText) {
        this.parentPost = null;
        this.parentComment = parentComment;
        this.parentUser = parentUser;
        this.commentText = commentText;
        this.replyList = new ArrayList<>();
        this.voteCount = 0;
        this.votingUserID = new HashMap<>();
        this.commentID = commentCounter++;
    }

    public String getCommentText() {
        return commentText;
    }

    public int getCommentID() {
        return commentID;
    }

    public Post getParentPost() {
        return parentPost;
    }

    public User getParentUser() {
        return parentUser;
    }

    public ArrayList<Comment> getReplyList() {
        return replyList;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getVotes() {
        return voteCount;
    }

    public void addReply(String commentReplyText, User replyParentUser) {
        Comment commentReply = new Comment(this, replyParentUser, commentReplyText);
        this.replyList.add(commentReply);
    }

    public void addReply(Comment reply) {
        this.replyList.add(reply);
    }

    public void setCommentID(int commentId) {
        this.commentID = commentId;
    }

    public Comment getParentComment() {
        return this.parentComment;
    }

    public static Comment findById(int id) {
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