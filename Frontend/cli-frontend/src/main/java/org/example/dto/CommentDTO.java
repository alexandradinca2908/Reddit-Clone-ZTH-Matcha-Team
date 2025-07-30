package org.example.dto;

import java.util.ArrayList;

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
        ArrayList<CommentDTO> replies
) {
}
