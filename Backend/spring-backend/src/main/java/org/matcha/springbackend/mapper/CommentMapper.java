package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.comment.CommentDto;
import org.matcha.springbackend.entities.*;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.repository.CommentRepository;
import org.matcha.springbackend.repository.VoteRepository;
import org.matcha.springbackend.service.AccountService;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.service.CommentService;
import org.matcha.springbackend.service.PostService;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class CommentMapper {
    private final PostMapper postMapper;
    private final AccountMapper accountMapper;
    private final VoteRepository voteRepository;
    private final AccountService accountService;
    private final PostService postService;
    private final CommentRepository commentRepository;

    public CommentMapper(PostMapper postMapper, AccountMapper accountMapper,
                         VoteRepository voteRepository, AccountService accountService,
                         PostService postService, CommentRepository commentRepository) {
        this.postMapper = postMapper;
        this.accountMapper = accountMapper;
        this.voteRepository = voteRepository;
        this.accountService = accountService;
        this.postService = postService;
        this.commentRepository = commentRepository;
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

        entity.setPost(postService.getPostEntityById(model.getPostId().toString()));
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
        String userVote = model.getUserVote().toString();
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
        UUID id = entity.getCommentId();

        //  Map account
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
        int upvotes = entity.getUpvotes();
        int downvotes = entity.getDownvotes();
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
                    .map(this::entityToModel)
                    .collect(Collectors.toList());
        }

        return new Comment(id, author, parentCommentId, postId, text,
                deleted, upvotes, downvotes, score, voteType,
                createdAt, updatedAt, replies);
    }
}
