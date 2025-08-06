package org.matcha.springbackend.dto.post;

public record PostDto(
        String id,
        String title,
        String content,
        String author,
        String subreddit,
        Integer upvotes,
        Integer downvotes,
        Integer commentCount,
        String userVote,
        String createdAt,
        String updatedAt
) {
}
