package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.post.PostDTO;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.entities.PostEntity;
import org.matcha.springbackend.entities.SubredditEntity;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Subreddit;
import org.matcha.springbackend.repositories.AccountRepository;
import org.matcha.springbackend.repositories.SubredditRepository;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final AccountRepository accountRepository;
    private final SubredditRepository subredditRepository;

    public PostMapper(AccountRepository accountRepository, SubredditRepository subredditRepository) {
        this.accountRepository = accountRepository;
        this.subredditRepository = subredditRepository;
    }

    public PostEntity modelToEntity(Post post) {
        if (post == null) return null;

        PostEntity entity = new PostEntity();
        entity.setPostID(post.getPostID());
        entity.setTitle(post.getTitle());
        entity.setContent(post.getContent());
        entity.setPhotoPath(post.getPhotoPath());
        entity.setDeleted(post.isDeleted());
        entity.setCreatedAt(post.getCreatedAt());
        entity.setUpdatedAt(post.getUpdatedAt());

        // Map Account
        if (post.getAccount() != null && post.getAccount().getAccountId() != null) {
            AccountEntity accountEntity = accountRepository.findById(post.getAccount().getAccountId()).orElse(null);
            entity.setAccount(accountEntity);
        }

        // Map Subreddit
        if (post.getSubreddit() != null && post.getSubreddit().getSubredditId() != null) {
            SubredditEntity subredditEntity = subredditRepository.findById(post.getSubreddit().getSubredditId()).orElse(null);
            entity.setSubreddit(subredditEntity);
        }

        return entity;
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

    public Post entityToModel(PostEntity entity) {
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
        post.setPostID(entity.getPostID());
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
