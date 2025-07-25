package org.matcha.springbackend.dto.post.requestbody;

public record PostRequestBodyDTO (
        String title,
        String content,
        String author,
        String subreddit
) {
}
