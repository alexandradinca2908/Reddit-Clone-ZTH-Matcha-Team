package org.matcha.springbackend.model;

import org.matcha.springbackend.entities.VotableType;

import java.util.UUID;

public class Vote {
    private UUID voteID;
    private UUID votableID;
    private String vote;
    private VotableType votableType;
    private String voteType;
    private Account account;

    public Vote(UUID voteID, UUID votableID, String vote, VotableType votableType,
                String voteType, Account account) {
        this.voteID = voteID;
        this.votableID = votableID;
        this.vote = vote;
        this.votableType = votableType;
        this.voteType = voteType;
        this.account = account;
    }

    public void upvote() {
        this.vote = "up";
    }

    public void downvote() {
        this.vote = "down";
    }

    public void cancelVote() {
        this.vote = "none";
    }

    public UUID getVoteID() {
        return voteID;
    }

    public UUID getVotableID() {
        return votableID;
    }

    public String getVote() {
        return vote;
    }

    public VotableType getVotableType() {
        return votableType;
    }

    public String getVoteType() {
        return voteType;
    }

    public Account getAccount() {
        return account;
    }
}
