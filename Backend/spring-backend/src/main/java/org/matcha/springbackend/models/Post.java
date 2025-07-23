package org.matcha.springbackend.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Post extends Likeable {
    static int postCounter = 1;
    ArrayList<Comment> commentList;
    int postID;
    String username;
    String title;
    String body;
    int voteCount;
    HashMap<String, Integer> votingUserID;

    public Post(String title, String body, String username) {
        this.title = title;
        this.body = body;
        this.voteCount = 0;
        this.username = username;
        this.postID = postCounter++;
        this.commentList = new ArrayList<>();
        this.votingUserID = new HashMap<>();
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

    public int getPostID() {
        return postID;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getCommentsCounter() { return commentList.size(); }
    public ArrayList<Comment> getCommentList() { return commentList; }
    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }
    public HashMap<String, Integer> getVotingUserID() {
        return votingUserID;
    }
    public void setPostId(int dbPostID) {
        this.postID = dbPostID;
    }
}
