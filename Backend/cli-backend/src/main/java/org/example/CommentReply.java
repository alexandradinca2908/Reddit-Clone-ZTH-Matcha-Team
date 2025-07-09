package org.example;

public class CommentReply implements Likeable {
    private static int commentReplyID = 0;
    private final Comment perentComment;
    private final User parentUser;
    private String commentReplyText;
    private int voteCount;

    public CommentReply(Comment perentComment, User parentUser, String commentReplyText) {
        this.perentComment = perentComment;
        this.parentUser = parentUser;
        this.commentReplyText = commentReplyText;
        this.voteCount = 0;
        commentReplyID++;
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
        return perentComment;
    }

    public void setCommentReplyText(String commentReplyText) {
        this.commentReplyText = commentReplyText;
    }

}
