package org.matcha.springbackend.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "vote",
        uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "votable_id"}))
public class VoteEntity {

    @Id
    @GeneratedValue
    @Column(name = "vote_id", nullable = false, updatable = false)
    private UUID voteId;

    @Column(name = "votable_id", nullable = false)
    private UUID votableId;

    @Enumerated(EnumType.STRING)
    @Column(name = "votable_type", nullable = false)
    private VotableType votableType;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type", nullable = false)
    private VoteType voteType;

    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_vote_account"), nullable = false)
    private AccountEntity account;

    public VoteEntity() {}

    @PrePersist
    protected void onCreate() {
        if (voteId == null) {
            voteId = UUID.randomUUID();
        }
    }

    // Getters and setters

    public UUID getVoteId() {
        return voteId;
    }

    public void setVoteId(UUID voteId) {
        this.voteId = voteId;
    }

    public UUID getVotableId() {
        return votableId;
    }

    public void setVotableId(UUID votableId) {
        this.votableId = votableId;
    }

    public VotableType getVotableType() {
        return votableType;
    }

    public void setVotableType(VotableType votableType) {
        this.votableType = votableType;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
