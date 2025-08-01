package org.matcha.springbackend.dto.subreddit.requestbody;

public record CreateSubredditBodyDto(
        String name,
        String displayName,
        String description,
        String iconUrl
) {
}
