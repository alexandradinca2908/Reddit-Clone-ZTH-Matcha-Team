package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.comment.CommentDto;
import org.matcha.springbackend.entities.*;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.repositories.AccountRepository;
import org.matcha.springbackend.repositories.VoteRepository;
import org.matcha.springbackend.service.AccountService;
import org.matcha.springbackend.service.PostService;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
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

    public CommentMapper(PostMapper postMapper, AccountMapper accountMapper, VoteRepository voteRepository, AccountService accountService, PostService postService) {
        this.postMapper = postMapper;
        this.accountMapper = accountMapper;
        this.voteRepository = voteRepository;
        this.accountService = accountService;
        this.postService = postService;
    }

    public CommentEntity modelToEntity(Comment model) {
        if (model == null) return null;

        CommentEntity entity = new CommentEntity();

        entity.setCommentId(model.getCommentId());
        entity.setAccount(accountService.getAccountEntityById(model.getAccount().getAccountId()));

        CommentEntity parent;
        if (model.getParent() != null) {
            parent = this.modelToEntity(model.getParent());
        } else {
            parent = null;
        }
        entity.setParent(parent);

        entity.setPost(postService.getPostEntityById(model.getPost().getPostID().toString()));
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
        String postId = model.getPost().getPostID().toString();
        String parentId = model.getParent().getCommentId().toString();
        String content = model.getText();
        String author = model.getAccount().getUsername();
        int upvotes = model.getUpvotes();
        int downvotes = model.getDownvotes();
        int score = upvotes - downvotes;
        String userVote = model.getUserVote().toString();
        String createdAt = model.getCreatedAt().toString();
        String updatedAt = model.getUpdatedAt().toString();
        List<CommentDto> replies = model.getComments().stream().map(this::modelToDto).collect(Collectors.toList());

        return new CommentDto(id, postId, parentId, content, author,
                upvotes, downvotes, score, userVote, createdAt, updatedAt, replies);
    }

    public Comment entityToModel(CommentEntity entity) {
        if (entity == null) return null;

        UUID id = entity.getCommentId();
        Account author = accountMapper.entityToModel(entity.getAccount());

        Comment parent;

        if (entity.getParent() != null) {
            parent = this.entityToModel(entity.getParent());
        } else {
            parent = null;
        }

        Post post = postMapper.entityToModel(entity.getPost());
        String text = entity.getContent();
        boolean deleted = entity.isDeleted();
        Integer upvotes = entity.getUpvotes();
        Integer downvotes = entity.getDownvotes();

        VoteEntity voteEntity = voteRepository.findByAccountAndVotableId(entity.getAccount(), id).orElse(null);

        VoteType voteType;
        if (voteEntity == null) {
            voteType = VoteType.NONE;
        } else {
            voteType = voteEntity.getVoteType();
        }

        OffsetDateTime createdAt = entity.getCreatedAt();
        OffsetDateTime updatedAt = entity.getUpdatedAt();

        return new Comment(id, author, parent, post, text,
                deleted, upvotes, downvotes, voteType, createdAt, updatedAt);
    }
}
