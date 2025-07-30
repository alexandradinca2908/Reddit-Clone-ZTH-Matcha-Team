package org.matcha.springbackend.model;

import java.util.UUID;

public class Vote {
    private UUID vote_id;
    private UUID votable_id;
    private String vote;
    private Account account;
    private UUID postID;
    private UUID commentID;

    public void upvote() {
        this.vote = "up";
    }

    public void downvote() {
        this.vote = "down";
    }

    public void cancelVote() {
        this.vote = "null";
    }
}
