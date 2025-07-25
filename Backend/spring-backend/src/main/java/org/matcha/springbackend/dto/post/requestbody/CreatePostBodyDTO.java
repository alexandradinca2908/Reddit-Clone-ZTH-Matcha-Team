package org.matcha.springbackend.dto.post.requestbody;

public record CreatePostBodyDTO(
        String title,
        String content,
        String author,
        String subreddit
) {
}
