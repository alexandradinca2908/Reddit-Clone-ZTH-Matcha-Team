package org.matcha.springbackend.dto.comment;

import java.util.List;

public record CommentDto(
        String id,
        String postId,
        String parentId,
        String content,
        String author,
        Integer upvotes,
        Integer downvotes,
        Integer score,
        String userVote,
        String createdAt,
        String updatedAt,
        List<CommentDto> replies
) {
}
