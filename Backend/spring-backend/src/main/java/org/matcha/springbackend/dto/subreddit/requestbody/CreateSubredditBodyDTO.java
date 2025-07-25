package org.matcha.springbackend.dto.subreddit.requestbody;

public record CreateSubredditBodyDTO(
        String name,
        String displayName,
        String description,
        String iconUrl
) {
}
