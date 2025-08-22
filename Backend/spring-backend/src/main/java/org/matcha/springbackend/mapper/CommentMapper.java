package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.comment.CommentDto;
import org.matcha.springbackend.entities.*;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.repository.CommentRepository;
import org.matcha.springbackend.repository.PostRepository;
import org.matcha.springbackend.repository.VoteRepository;
import org.matcha.springbackend.service.AccountService;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.service.CommentService;
import org.matcha.springbackend.service.PostService;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class CommentMapper {
    private final AccountMapper accountMapper;
    private final VoteRepository voteRepository;
    private final AccountService accountService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentMapper(AccountMapper accountMapper,
                         VoteRepository voteRepository, AccountService accountService,
                         CommentRepository commentRepository, PostRepository postRepository) {
        this.accountMapper = accountMapper;
        this.voteRepository = voteRepository;
        this.accountService = accountService;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public CommentEntity modelToEntity(Comment model) {
        if (model == null) return null;

        CommentEntity entity = new CommentEntity();

        entity.setCommentId(model.getCommentId());
        entity.setAccount(accountService.getAccountEntityById(model.getAccount().getAccountId()));

        CommentEntity parent;
        if (model.getParentCommentId() != null) {
            UUID parentId = model.getParentCommentId();
            parent = commentRepository.findByCommentId(parentId).orElse(null);
        } else {
            parent = null;
        }
        entity.setParent(parent);

        entity.setPost(postRepository.findByPostIDAndIsDeletedFalse(model.getPostId())
                .orElse(null));
        entity.setContent(model.getText());
        entity.setDeleted(model.isDeleted());
        entity.setUpvotes(model.getUpvotes());
        entity.setDownvotes(model.getDownvotes());
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());

        return entity;
    }

    public CommentDto modelToDto(Comment model) {
        String id = model.getCommentId().toString();
        String postId = model.getPostId().toString();

        String parentId;
        if (model.getParentCommentId() != null) {
            parentId = model.getParentCommentId().toString();
        } else {
            parentId = null;
        }

        String content = model.getText();
        String author = model.getAccount().getUsername();
        int upvotes = model.getUpvotes();
        int downvotes = model.getDownvotes();
        int score = upvotes - downvotes;
        String userVote = model.getUserVote().toString().toLowerCase();
        String createdAt = model.getCreatedAt().toString();
        String updatedAt = model.getUpdatedAt().toString();

        List<CommentDto> replies;
        if (model.getReplies() != null && !model.getReplies().isEmpty()) {
            replies = model.getReplies().stream()
                    .filter(reply -> !reply.isDeleted())
                    .map(this::modelToDto)
                    .toList();
        } else {
            replies = new ArrayList<>();
        }

        return new CommentDto(id, postId, parentId, content, author,
                upvotes, downvotes, score, userVote, createdAt, updatedAt, replies);
    }

    public Comment entityToModel(CommentEntity entity) {
        VoteEntity voteEntity = voteRepository.findByAccountAndVotableId(
                entity.getAccount(), entity.getCommentId()
        ).orElse(null);

        VoteType voteType = (voteEntity != null) ? voteEntity.getVoteType() : VoteType.NONE;

        return createCommentModel(entity, voteType);
    }

    public Comment entityToModelWithVoteMap(CommentEntity entity, Map<UUID, VoteType> voteMap) {
        VoteType voteType = voteMap.getOrDefault(entity.getCommentId(), VoteType.NONE);
        return createCommentModel(entity, voteType);
    }

    private Comment createCommentModel(CommentEntity entity, VoteType voteType) {
        UUID id = entity.getCommentId();

        // Map account
        Account author = accountMapper.entityToModel(entity.getAccount());

        // Parent comment ID (nullable)
        UUID parentCommentId = (entity.getParent() != null)
                ? entity.getParent().getCommentId()
                : null;

        // Post ID
        UUID postId = entity.getPost().getPostID();

        String text = entity.getContent();
        boolean deleted = entity.isDeleted();
        int upvotes = entity.getUpvotes();
        int downvotes = entity.getDownvotes();
        int score = upvotes - downvotes;

        OffsetDateTime createdAt = entity.getCreatedAt();
        OffsetDateTime updatedAt = entity.getUpdatedAt();

        List<Comment> replies = new ArrayList<>();

        return new Comment(id, author, parentCommentId, postId, text, deleted,
                upvotes, downvotes, score, voteType, createdAt, updatedAt, replies);
    }
}
