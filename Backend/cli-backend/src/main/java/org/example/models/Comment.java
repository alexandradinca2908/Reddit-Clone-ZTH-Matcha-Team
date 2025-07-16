package org.example.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Comment extends Likeable {
    private static int commentCounter = 0;
    private int commentID;
    private String commentText;
    private final Post parentPost;
    private final User parentUser;
    private Comment parentComment;
    public HashMap<Integer, Integer> votingUserID;
    private ArrayList<Comment> replyList;

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

    public Comment getParentComment() {
        return parentComment;
    }

    public ArrayList<Comment> getReplyList() {
        return replyList;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void addReply(String replyText, User replyParentUser) {
        Comment commentReply = new Comment(this, replyParentUser, replyText);
        this.replyList.add(commentReply);
    }

    public void setCommentID(int commentId) {
        this.commentID = commentId;
    }
}
