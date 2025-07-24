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

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

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

}
