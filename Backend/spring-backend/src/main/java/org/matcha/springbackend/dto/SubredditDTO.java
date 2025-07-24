package org.matcha.springbackend.dto;

public record SubredditDTO(
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
