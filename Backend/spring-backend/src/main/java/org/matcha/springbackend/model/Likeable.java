package org.matcha.springbackend.model;

public abstract class Likeable {
    protected int voteCount;

    public void upvote() {
        voteCount++;
    }

    public void downvote() {
        voteCount--;
    }

    public int getVotes() {
        return voteCount;
    }
}
