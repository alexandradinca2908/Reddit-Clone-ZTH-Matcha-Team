package org.matcha.springbackend.mapper;

import org.matcha.springbackend.entities.PostEntity;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Subreddit;

public class PostEntityMapper {

    public static Post toDomain(PostEntity entity) {
        if (entity == null) {
            return null;
        }

        // Stub/mock Account and Subreddit
        Account account = new Account(); // Populate with test data if needed
        Subreddit subreddit = new Subreddit(); // Populate with test data if needed

        Post post = new Post();
        post.setPostId(entity.getPostId());
        post.setAccount(account); // Replace with actual mapping when ready
        post.setSubreddit(subreddit); // Replace with actual mapping when ready
        post.setTitle(entity.getTitle());
        post.setContent(entity.getDescription()); // Map description → content
        post.setPhotoPath(entity.getPhotoPath());
        post.setDeleted(entity.isDeleted());
        post.setCreatedAt(entity.getCreatedAt());
        post.setUpdatedAt(entity.getUpdatedAt());

        return post;
    }
}
