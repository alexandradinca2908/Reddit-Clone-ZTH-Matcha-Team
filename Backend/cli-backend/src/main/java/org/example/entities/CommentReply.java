package org.example.entities;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentReply implements Likeable {
    private static int commentReplyCounter = 0;
    private int commentReplyID;
    private final Comment parentComment;
    private final User parentUser;
    private String commentReplyText;
    private int voteCount;
    public HashMap<Integer, Integer> votingUserID;
    public ArrayList<CommentReply> commentReplies = new ArrayList<CommentReply>();

    public CommentReply(Comment parentComment, User parentUser, String commentReplyText) {
        this.parentComment = parentComment;
        this.parentUser = parentUser;
        this.commentReplyText = commentReplyText;
        this.voteCount = 0;
        commentReplyID = commentReplyCounter++;
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

    public String getCommentReplyText() {
        return commentReplyText;
    }

    public User getParentUser() {
        return parentUser;
    }

    public Comment getPerentComment() {
        return parentComment;
    }

    public void setCommentReplyText(String commentReplyText) {
        this.commentReplyText = commentReplyText;
    }

    public ArrayList<CommentReply> getCommentReplies() {
        return commentReplies;
    }

    public int getCommentReplyID() {
        return commentReplyID;
    }
}
