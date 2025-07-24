package org.matcha.springbackend.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id", nullable = false, updatable = false)
    private UUID postId;

    // Mulți post-uri pot fi create de un singur cont, ON DELETE SET NULL → nullable true
    @ManyToOne
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_post_account"), nullable = true)
    private AccountEntity account;

    // Mulți post-uri pot aparține unui singur subreddit, ON DELETE CASCADE → trebuie să ștergem posturile când se șterge subreddit-ul
    @ManyToOne(optional = false)
    @JoinColumn(name = "subreddit_id", foreignKey = @ForeignKey(name = "fk_post_subreddit"), nullable = false)
    private SubredditEntity subreddit;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (postId == null) {
            postId = UUID.randomUUID();
        }
        createdAt = OffsetDateTime.now();
    }
}
