package org.matcha.springbackend.dto.post.requestbody;

public record UpdatePostBodyDto(
        String title,
        String content
) {
}
