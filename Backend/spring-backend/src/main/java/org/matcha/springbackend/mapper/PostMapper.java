package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.post.PostDto;
import org.matcha.springbackend.entities.*;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.model.*;
import org.matcha.springbackend.repository.AccountRepository;
import org.matcha.springbackend.repository.SubredditRepository;
import org.matcha.springbackend.repository.VoteRepository;
import org.matcha.springbackend.service.VoteService;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostMapper {
    private final AccountRepository accountRepository;
    private final SubredditRepository subredditRepository;
    private final VoteRepository voteRepository;
    private final AccountMapper accountMapper;
    private final SubredditMapper subredditMapper;
    private final CommentMapper commentMapper;

    public PostMapper(AccountRepository accountRepository, SubredditRepository subredditRepository,
                      VoteRepository voteRepository, AccountMapper accountMapper,
                      SubredditMapper subredditMapper, CommentMapper commentMapper) {
        this.accountRepository = accountRepository;
        this.subredditRepository = subredditRepository;
        this.voteRepository = voteRepository;
        this.accountMapper = accountMapper;
        this.subredditMapper = subredditMapper;
        this.commentMapper = commentMapper;
    }

    public PostEntity modelToEntity(Post post) {
        PostEntity entity = new PostEntity();

        if (post.getPostID() != null) {
            entity.setPostID(post.getPostID());
        } else {
            entity.setPostID(null);
        }

        entity.setTitle(post.getTitle());
        entity.setContent(post.getContent());
        entity.setPhotoPath(post.getPhotoPath());
        entity.setPhotoPath(post.getPhotoPath());
        entity.setDeleted(post.isDeleted());
        entity.setCreatedAt(post.getCreatedAt());
        entity.setUpdatedAt(post.getUpdatedAt());
        entity.setUpvotes(post.getUpvotes());
        entity.setDownvotes(post.getDownvotes());

        int commentCount = 0;
        if (post.getComments() != null) {
            commentCount = post.getComments().size();
        }
        entity.setCommentCount(commentCount);

        // Map Account
        if (post.getAccount() != null && post.getAccount().getAccountId() != null) {
            AccountEntity accountEntity = accountRepository.findById(post.getAccount().getAccountId()).orElse(null);
            entity.setAccount(accountEntity);
        }

        // Map Subreddit
        if (post.getSubreddit() != null && post.getSubreddit().getId() != null) {
            SubredditEntity subredditEntity = subredditRepository.findById(post.getSubreddit().getId()).orElse(null);
            entity.setSubreddit(subredditEntity);
        }

        return entity;
    }

    public PostDto modelToDto(Post model) {
        String id = model.getPostID().toString();
        String title = model.getTitle();
        String content = model.getContent();
        String imageUrl = model.getPhotoPath();
        String author = model.getAccount().getUsername();
        String subreddit = model.getSubreddit().getDisplayName();
        Integer upvotes = model.getUpvotes();
        Integer downvotes = model.getDownvotes();
        Integer score = upvotes - downvotes;
        Integer commentCount = model.getCommentCount();
        String userVote = model.getVoteType().toString().toLowerCase();
        String createdAt = model.getCreatedAt().toString();
        String updatedAt = model.getUpdatedAt().toString();

        return new PostDto(id, title, content, imageUrl, author, subreddit, upvotes, downvotes,
                score, commentCount, userVote, createdAt, updatedAt);
    }

    public Post entityToModel(PostEntity entity) {
        VoteEntity voteEntity = voteRepository.findByAccountAndVotableId(entity.getAccount(), entity.getPostID())
                .orElse(null);

        VoteType voteType = voteEntity != null ? voteEntity.getVoteType() : VoteType.NONE;

        return createPostModel(entity, voteType);
    }

    public Post entityToModelWithVoteMap(PostEntity entity, Map<UUID, VoteType> voteMap) {
        VoteType voteType = voteMap.getOrDefault(entity.getPostID(), VoteType.NONE);
        return createPostModel(entity, voteType);
    }

    private Post createPostModel(PostEntity entity, VoteType voteType) {
        // Map Account
        Account account = accountMapper.entityToModel(entity.getAccount());

        // Map Subreddit
        Subreddit subreddit = subredditMapper.entityToModel(entity.getSubreddit());

        UUID id = entity.getPostID();
        String title = entity.getTitle();
        String content = entity.getContent();
        int upvotes = entity.getUpvotes();
        int downvotes = entity.getDownvotes();
        int score = upvotes - downvotes;
        int commentCount = entity.getCommentCount();
        String photoPath = entity.getPhotoPath();
        boolean isDeleted = entity.isDeleted();
        OffsetDateTime createdAt = entity.getCreatedAt();
        OffsetDateTime updatedAt = entity.getUpdatedAt();

        //  Comments are extracted separately
        List<Comment> comments = new ArrayList<>();

        return new Post(id, title, content, account, subreddit,
                upvotes, downvotes, score, commentCount, voteType,
                photoPath, isDeleted, createdAt, updatedAt, comments);
    }
}
