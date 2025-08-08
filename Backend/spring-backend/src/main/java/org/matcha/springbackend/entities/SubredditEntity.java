package org.matcha.springbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "subreddit")
public class SubredditEntity {
    @Id
    @GeneratedValue
    @Column(name = "subreddit_id", nullable = false, updatable = false)
    private UUID subredditId;

    // ON DELETE SET NULL → nullable și setat null dacă contul dispare
    @ManyToOne
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_subreddit_account"), nullable = true)
    private AccountEntity account;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column
    private String description;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "member_count", nullable = false)
    private Integer memberCount;

    @Column(name = "post_count", nullable = false)
    private Integer postCount;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    public SubredditEntity() {}

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}
