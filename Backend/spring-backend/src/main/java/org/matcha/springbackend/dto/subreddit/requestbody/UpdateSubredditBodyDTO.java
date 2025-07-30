package org.matcha.springbackend.dto.subreddit.requestbody;

public record UpdateSubredditBodyDTO(
        String name,
        String displayName,
        String description,
        String iconUrl
) {
}
