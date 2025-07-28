package org.matcha.springbackend.dto.comment;

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
        String createdAt,
        String updatedAt,
        List<CommentDTO> replies
) {
}
