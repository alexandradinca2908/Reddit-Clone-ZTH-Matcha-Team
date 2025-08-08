package org.example.mapper;

import org.example.dto.PostDTO;
import org.example.models.Post;

public class PostMapper {
    public static PostDTO toDto(Post post, String subreddit, Integer upvotes, Integer downvotes, String userVote, String createdAt, String updatedAt) {
        if (post == null) {
            return null;
        }

        return new PostDTO(
                post.getUUID(),
                post.getTitle(),
                post.getContent(),
                post.getAuthorName(),
                subreddit,
                upvotes,
                downvotes,
                post.getScore(),
                post.getCommentsCounter(),
                userVote,
                createdAt,
                updatedAt
        );
    }

    public static Post toModel(PostDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Post(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.author()
        );
    }
}
