package org.matcha.springbackend.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Comment() extends Likeable {
    private static int commentCounter = 1;
    private int commentID;
    private String commentText;
    private final Post parentPost;
    private final Comment parentComment;
    private final User parentUser;
    public HashMap<String, Integer> votingUserID;
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
}
