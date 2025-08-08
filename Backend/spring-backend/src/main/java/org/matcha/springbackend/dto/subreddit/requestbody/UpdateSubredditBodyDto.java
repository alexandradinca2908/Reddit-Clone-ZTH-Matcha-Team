package org.matcha.springbackend.dto.subreddit.requestbody;

public record UpdateSubredditBodyDto(
        String name,
        String displayName,
        String description,
        String iconUrl
) {
}
