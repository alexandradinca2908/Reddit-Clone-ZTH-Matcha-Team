package org.example.entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Comment implements Likeable {
    private static int commentCounter = 0;
    private int commentID;
    private String commentText;
    private final Post parentPost;
    private final User parentUser;
    private int voteCount;
    public HashMap<Integer, Integer> votingUserID;
    public ArrayList<Comment> replyList;

    public Comment(Post parentPost, User parentUser, String commentText) {
        this.parentPost = parentPost;
        this.parentUser = parentUser;
        this.commentText = commentText;
        this.replyList = new ArrayList<>();
        this.voteCount = 0;
        this.votingUserID = new HashMap<>();
        this.commentID = commentCounter++;
    }

    public Comment(Comment parentComment, User parentUser, String commentText) {
        this.parentPost = null;
        this.parentUser = parentUser;
        this.commentText = commentText;
        this.replyList = new ArrayList<>();
        this.voteCount = 0;
        this.votingUserID = new HashMap<>();
        this.commentID = commentCounter++;
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

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void addComentReply(String commentReplyText, User replyParentUser) {
        Comment commentReply = new Comment(this, replyParentUser, commentReplyText);
        replyList.add(commentReply);
    }

    public void setCommentID(int commentId) {
        this.commentID = commentId;
    }
}
