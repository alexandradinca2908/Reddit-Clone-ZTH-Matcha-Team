package org.matcha.springbackend.model;

import org.matcha.springbackend.enums.VotableType;
import org.matcha.springbackend.enums.VoteType;

import java.util.UUID;

public class Vote {
    private UUID voteID;
    private UUID votableID;
    private VotableType votableType;
    private VoteType voteType;
    private Account account;

    public Vote(UUID voteID, UUID votableID, VotableType votableType,
                VoteType voteType, Account account) {
        this.voteID = voteID;
        this.votableID = votableID;
        this.votableType = votableType;
        this.voteType = voteType;
        this.account = account;
    }

    public UUID getVoteID() {
        return voteID;
    }

    public UUID getVotableID() {
        return votableID;
    }

    public VotableType getVotableType() {
        return votableType;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voteID=" + voteID +
                ", votableID=" + votableID +
                ", votableType=" + votableType +
                ", voteType=" + voteType +
                ", account=" + account +
                '}';
    }
}
