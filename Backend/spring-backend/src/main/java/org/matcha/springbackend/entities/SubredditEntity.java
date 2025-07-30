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
    // ON DELETE SET NULL → nullable și setat null dacă contul dispare
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

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public SubredditEntity() {}

    @PrePersist
    protected void onCreate() {
        if (subredditId == null) {
            subredditId = UUID.randomUUID();
        }
        createdAt = OffsetDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    // Getters și setters

    public UUID getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(UUID subredditId) {
        this.subredditId = subredditId;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
