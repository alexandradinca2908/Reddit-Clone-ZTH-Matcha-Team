package org.example.entities;

import java.util.ArrayList;

public class Comment implements Likeable {
    private static int commentID = 0;
    private String commentText;
    private final Post parentPost;
    private final User parentUser;
    private int voteCount;
    ArrayList<CommentReply> replyList;

    public Comment(Post parentPost, User parentUser, String commentText) {
        this.parentPost = parentPost;
        this.parentUser = parentUser;
        this.commentText = commentText;
        this.replyList = new ArrayList<>();
        this.voteCount = 0;
        commentID++;
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

    public Post getParentPost() {
        return parentPost;
    }

    public User getParentUser() {
        return parentUser;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void addComentReply(String commentReplyText, User replyParentUser) {
        CommentReply commentReply = new CommentReply(this, replyParentUser, commentReplyText);
        replyList.add(commentReply);
    }

}
