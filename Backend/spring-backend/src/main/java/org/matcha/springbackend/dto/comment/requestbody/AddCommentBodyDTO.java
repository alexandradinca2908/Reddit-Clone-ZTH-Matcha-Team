package org.matcha.springbackend.dto.comment.requestbody;

public record AddCommentBodyDTO(
        String content,
        String author,
        String parentId
) {
}
