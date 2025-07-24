package org.matcha.springbackend.dto;

import java.time.Instant;

public record PostDTO(
        String id,
        String title,
        String content,
        String author,
        String subreddit,
        Integer upvotes,
        Integer downvotes,
        Integer score,
        Integer commentCount,
        String userVote,
        Instant createdAt,
        Instant updatedAt
) {
}
