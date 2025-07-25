package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.post.PostDTO;
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
        Integer score = 0;
        //  TODO - COMMENT COUNT
        Integer commentCount = 0;
        //  TODO - USER VOTE
        String userVote = "null";
        String createdAt = model.getCreatedAt().toString();
        String updatedAt = model.getUpdatedAt().toString();

        return new PostDTO(id, title, content, author, subreddit, 0, 0,
                score, commentCount, userVote, createdAt, updatedAt);
    }
}
