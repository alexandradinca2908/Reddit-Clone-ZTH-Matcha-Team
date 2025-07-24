package org.matcha.springbackend.dto;

import java.time.Instant;
import java.util.List;

public record CommentDTO(
        String id,
        String postId,
        String parentId,
        String content,
        String author,
        Integer upvotes,
        Integer downvotes,
        Integer score,
        String userVote,
        Instant createdAt,
        Instant updatedAt,
        List<CommentDTO> replies
) {
}
