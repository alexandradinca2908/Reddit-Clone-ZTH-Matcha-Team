package org.matcha.springbackend.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "subreddit")
public class SubredditEntity {

    @Id
    @GeneratedValue
    @Column(name = "subreddit_id", nullable = false, updatable = false)
    private UUID subredditId;

    // Mulți subreddits pot avea un singur creator (account)
    // ON DELETE SET NULL în DB înseamnă ca aici să fie nullable și să se seteze null dacă contul dispare
    @ManyToOne
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_subreddit_account"), nullable = true)
    private AccountEntity account;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (subredditId == null) {
            subredditId = UUID.randomUUID();
        }
        createdAt = OffsetDateTime.now();
    }
}
