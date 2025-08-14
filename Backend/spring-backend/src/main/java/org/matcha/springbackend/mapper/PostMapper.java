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

    public PostMapper(AccountRepository accountRepository, SubredditRepository subredditRepository,
                      VoteRepository voteRepository, AccountMapper accountMapper,
                      SubredditMapper subredditMapper) {
        this.accountRepository = accountRepository;
        this.subredditRepository = subredditRepository;
        this.voteRepository = voteRepository;
        this.accountMapper = accountMapper;
        this.subredditMapper = subredditMapper;
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
        String author = model.getAccount().getUsername();
        String subreddit = model.getSubreddit().getDisplayName();
        Integer upvotes = model.getUpvotes();
        Integer downvotes = model.getDownvotes();
        Integer score = upvotes - downvotes;
        Integer commentCount = model.getCommentCount();
        String userVote = model.getVoteType().toString().toLowerCase();
        String createdAt = model.getCreatedAt().toString();
        String updatedAt = model.getUpdatedAt().toString();

        return new PostDto(id, title, content, author, subreddit, upvotes, downvotes,
                score, commentCount, userVote, createdAt, updatedAt);
    }

    public Post entityToModel(PostEntity entity) {
        VoteEntity voteEntity = voteRepository.findByAccountAndVotableId(entity.getAccount(), entity.getPostID())
                .orElse(null);

        VoteType voteType = voteEntity != null ? voteEntity.getVoteType() : VoteType.NONE;

        return createPostModel(entity, true, voteType);
    }

    public Post entityToModelWithVoteMap(PostEntity entity, Map<UUID, VoteType> voteMap) {
        VoteType voteType = voteMap.getOrDefault(entity.getPostID(), VoteType.NONE);
        return createPostModel(entity, false, voteType);
    }

    private Post createPostModel(PostEntity entity, boolean needsComments, VoteType voteType) {
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

        List<Comment> comments = null;
        if (needsComments && entity.getComments() != null) {
            comments = entity.getComments().stream()
                    .map(this::commentEntityToModel)
                    .toList();
        }

        return new Post(id, title, content, account, subreddit,
                upvotes, downvotes, score, commentCount, voteType,
                photoPath, isDeleted, createdAt, updatedAt, comments);
    }

    public Comment commentEntityToModel(CommentEntity entity) {
        UUID id = entity.getCommentId();
        Account author = accountMapper.entityToModel(entity.getAccount());

        UUID parentCommentId;
        if (entity.getParent() != null) {
            parentCommentId = entity.getParent().getCommentId();
        } else {
            parentCommentId = null;
        }

        UUID postId = entity.getPost().getPostID();
        String text = entity.getContent();
        boolean deleted = entity.isDeleted();
        Integer upvotes = entity.getUpvotes();
        Integer downvotes = entity.getDownvotes();
        int score = upvotes - downvotes;

        VoteEntity voteEntity = voteRepository.findByAccountAndVotableId(entity.getAccount(), id).orElse(null);

        VoteType voteType;
        if (voteEntity == null) {
            voteType = VoteType.NONE;
        } else {
            voteType = voteEntity.getVoteType();
        }

        OffsetDateTime createdAt = entity.getCreatedAt();
        OffsetDateTime updatedAt = entity.getUpdatedAt();

        List<Comment> replies = null;
        if (entity.getReplies() != null && !entity.getReplies().isEmpty()) {
            replies = entity.getReplies().stream()
                    .map(this::commentEntityToModel)
                    .collect(Collectors.toList());
        }

        return new Comment(id, author, parentCommentId, postId, text,
                deleted, upvotes, downvotes, score, voteType,
                createdAt, updatedAt, replies);
    }
}
