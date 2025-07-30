package org.matcha.springbackend.dto.post.requestbody;

public record UpdatePostBodyDTO(
        String title,
        String content
) {
}
