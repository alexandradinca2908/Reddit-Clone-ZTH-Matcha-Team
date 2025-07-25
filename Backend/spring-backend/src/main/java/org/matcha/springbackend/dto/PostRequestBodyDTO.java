package org.matcha.springbackend.dto;

public record PostRequestBodyDTO (
        String title,
        String content,
        String author,
        String subreddit
) {
}
