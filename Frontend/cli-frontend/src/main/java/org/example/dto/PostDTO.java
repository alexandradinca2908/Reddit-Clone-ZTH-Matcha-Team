package org.example.dto;

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
        String createdAt,
        String updatedAt
) {
}
