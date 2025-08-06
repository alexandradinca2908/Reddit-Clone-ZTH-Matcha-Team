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
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostMapper {
    private final AccountRepository accountRepository;
    private final SubredditRepository subredditRepository;
    private final VoteRepository voteRepository;
    private final VoteService voteService;
    private final AccountMapper accountMapper;

    public PostMapper(AccountRepository accountRepository, SubredditRepository subredditRepository,
                      VoteRepository voteRepository, VoteService voteService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.subredditRepository = subredditRepository;
        this.voteRepository = voteRepository;
        this.voteService = voteService;
        this.accountMapper = accountMapper;
    }

    public PostEntity modelToEntity(Post post) {
        if (post == null) return null;

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

        Integer score = 0;
        if (post.getUpvotes() != null && post.getDownvotes() != null) {
            score = post.getUpvotes() - post.getDownvotes();
        }

        Integer commentCount = 0;
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
        Integer upvotes = model.getUpvotes() == null ? 0 : model.getUpvotes();
        Integer downvotes = model.getDownvotes() == null ? 0 : model.getDownvotes();
        Integer commentCount = model.getCommentCount();
        String userVote = model.getVoteType().toString();
        String createdAt = model.getCreatedAt().toString();
        String updatedAt = model.getUpdatedAt().toString();

        return new PostDto(id, title, content, author, subreddit, upvotes, downvotes,
                commentCount, userVote, createdAt, updatedAt);
    }

    public Post entityToModel(PostEntity entity) {
        if (entity == null) return null;

        // Map Account
        Account account = new Account();
        if (entity.getAccount() != null) {
            account.setAccountId(entity.getAccount().getAccountId());
            account.setUsername(entity.getAccount().getUsername());
            account.setEmail(entity.getAccount().getEmail());
            account.setPhotoPath(entity.getAccount().getPhotoPath());
        }

        // Map Subreddit
        Subreddit subreddit = new Subreddit();
        if (entity.getSubreddit() != null) {
            subreddit.setSubredditId(entity.getSubreddit().getSubredditId());
            subreddit.setDisplayName(entity.getSubreddit().getName());
            subreddit.setDescription(entity.getSubreddit().getDescription());
        }

        // Map Post
        Post post = new Post();
        post.setPostID(entity.getPostID());
        post.setAccount(account);
        post.setSubreddit(subreddit);
        post.setTitle(entity.getTitle());
        post.setContent(entity.getContent());
        post.setUpvotes(entity.getUpvotes());
        post.setDownvotes(entity.getDownvotes());
        post.setCommentCount(entity.getCommentCount());
        post.setPhotoPath(entity.getPhotoPath());
        post.setDeleted(entity.isDeleted());
        post.setCreatedAt(entity.getCreatedAt());
        post.setUpdatedAt(entity.getUpdatedAt());

        VoteEntity voteEntity = voteRepository.findByAccountAndVotableId(entity.getAccount(), entity.getPostID())
                .orElse(null);

        VoteType voteType;
        if (voteEntity == null) {
            voteType = VoteType.NONE;
        } else {
            voteType = voteEntity.getVoteType();
        }
        post.setVoteType(voteType);

        List<Comment> comments = entity.getComments().stream().map(this::commentEntityToModel).toList();
        post.setComments(comments);

        return post;
    }

    public Comment commentEntityToModel(CommentEntity entity) {
        if (entity == null) return null;

        UUID id = entity.getCommentId();
        Account author = accountMapper.entityToModel(entity.getAccount());

        Comment parent;

        if (entity.getParent() != null) {
            parent = this.commentEntityToModel(entity.getParent());
        } else {
            parent = null;
        }

        // Don't recurse into full post
        Post post = new Post(entity.getPost().getPostID(), "", "", null, null, 0,
                0, 0, null, "", false,
                OffsetDateTime.now(), OffsetDateTime.now());

        String text = entity.getContent();
        boolean deleted = entity.isDeleted();
        Integer upvotes = entity.getUpvotes();
        Integer downvotes = entity.getDownvotes();
        Integer score = upvotes - downvotes;

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

        return new Comment(id, author, parent, post, text,
                deleted, upvotes, downvotes, voteType,
                createdAt, updatedAt, replies);
    }
}
