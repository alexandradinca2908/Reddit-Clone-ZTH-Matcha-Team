package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.post.PostDTO;
import org.matcha.springbackend.entities.PostEntity;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Subreddit;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
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

    public static Post entityToModel(PostEntity entity) {
        if (entity == null) return null;

        // Map Account
        Account account = new Account();
//        if (entity.getAccount() != null) {
//            account.setAccountId(entity.getAccount().getAccountId());
//            account.setUsername(entity.getAccount().getUsername());
//            account.setEmail(entity.getAccount().getEmail());
//            account.setPhotoPath(entity.getAccount().getPhotoPath());
//        }

        // Map Subreddit
        Subreddit subreddit = new Subreddit();
//        if (entity.getSubreddit() != null) {
//            subreddit.setSubredditId(entity.getSubreddit().getSubredditId());
//            subreddit.setDisplayName(entity.getSubreddit().getName());
//            subreddit.setDescription(entity.getSubreddit().getDescription());
//        }

        // Map Post
        Post post = new Post();
        post.setPostId(entity.getPostId());
        post.setAccount(account);
        post.setSubreddit(subreddit);
        post.setTitle(entity.getTitle());
        post.setContent(entity.getContent());
        post.setPhotoPath(entity.getPhotoPath());
        post.setDeleted(entity.isDeleted());
        post.setCreatedAt(entity.getCreatedAt());
        post.setUpdatedAt(entity.getUpdatedAt());

        return post;
    }

}
