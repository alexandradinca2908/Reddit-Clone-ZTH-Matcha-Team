package org.matcha.springbackend.dto.post.requestbody;

public record PutRequestBodyDTO(
        String title,
        String content
) {
}
