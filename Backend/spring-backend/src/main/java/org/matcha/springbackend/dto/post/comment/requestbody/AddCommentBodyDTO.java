package org.matcha.springbackend.dto.post.comment.requestbody;

public record AddCommentBodyDTO(
        String content,
        String author,
        String parentId
) {
}
