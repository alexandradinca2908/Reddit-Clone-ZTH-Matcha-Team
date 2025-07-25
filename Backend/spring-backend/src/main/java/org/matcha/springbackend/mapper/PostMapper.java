package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.PostDTO;
import org.matcha.springbackend.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post entityToModel(PostDTO dto) {
        //  TODO
        return null;
    }

    public PostDTO modelToDTO(Post model) {
        String id = model.getPostID().toString();
        String title = model.getTitle();
        String content = model.getContent();
        String author = model.getAccount().getUsername();
        String subreddit = model.getSubreddit().getDisplayName();
        //  TODO - SCORE
        //  TODO - COMMENT COUNT
        //  TODO - USER VOTE
        String createdAt = model.getCreatedAt().toString();
        //  TODO - UPDATED AT

        return new PostDTO(id, title, content, author, subreddit, 0, 0,
                null, null, null, createdAt, null);
    }
}
