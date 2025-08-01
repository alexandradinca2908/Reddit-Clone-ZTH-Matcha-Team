package org.matcha.springbackend.dto.subreddit;

public record SubredditDto(
        String id,
        String name,
        String displayName,
        String description,
        Integer memberCount,
        Integer postCount,
        String iconUrl,
        String createdAt
) {
}
