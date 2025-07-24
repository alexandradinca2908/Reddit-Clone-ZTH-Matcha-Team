package org.example.DTO;

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
