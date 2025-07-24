package org.matcha.springbackend.dto;

import java.time.Instant;

public record SubredditDTO(
        String id,
        String name,
        String displayName,
        String description,
        Integer memberCount,
        Integer postCount,
        String iconUrl,
        Instant createdAt
) {
}
