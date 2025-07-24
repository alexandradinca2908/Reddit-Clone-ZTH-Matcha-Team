package org.matcha.springbackend.model;

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
}
