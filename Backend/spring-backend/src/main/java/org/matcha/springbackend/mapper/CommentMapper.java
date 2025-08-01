package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.comment.CommentDto;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.entities.CommentEntity;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.repositories.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.List;

@Component
public class CommentMapper {
    private final AccountRepository accountRepository;
    private final PostMapper postMapper;
    private final AccountMapper accountMapper;

    public CommentMapper(AccountRepository accountRepository, PostMapper postMapper, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.postMapper = postMapper;
        this.accountMapper = accountMapper;
    }

    public CommentEntity modelToEntity(Comment comment) {
        if (comment == null) return null;
        CommentEntity entity = new CommentEntity();
        // entity.setCommentId(comment.getCommentId());
        entity.setContent(comment.getText());
        entity.setDeleted(comment.isDeleted());
        entity.setCreatedAt(comment.getCreatedAt());
        entity.setUpdatedAt(comment.getUpdatedAt());
        entity.setPost(postMapper.modelToEntity(comment.getPost()));
        if (comment.getParent() != null) {
            entity.setParent(this.modelToEntity(comment.getParent()));
        } else {
            entity.setParent(null);
        }

        // Map Account
        if (comment.getAccount() != null && comment.getAccount().getAccountId() != null) {
            AccountEntity accountEntity =
                    accountRepository.findById(comment.getAccount().getAccountId()).orElse(null);
            entity.setAccount(accountEntity);
        }

        // Upvotes, downvotes, score can be set if present in model
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
        String userVote = "null"; // TODO: implement if needed
        String createdAt = model.getCreatedAt().toString();
        String updatedAt = model.getUpdatedAt().toString();
        List<CommentDto> replies = model.getComments().stream().map(this::modelToDto).collect(Collectors.toList());

        return new CommentDto(id, postId, parentId, content, author,
                upvotes, downvotes, score, userVote, createdAt, updatedAt, replies);
    }

    public Comment entityToModel(CommentEntity entity) {
        if (entity == null) return null;
        Comment comment = new Comment();
        comment.setCommentId(entity.getCommentId());
        comment.setText(entity.getContent());
        comment.setDeleted(entity.isDeleted());
        comment.setCreatedAt(entity.getCreatedAt());
        comment.setUpdatedAt(entity.getUpdatedAt());
        comment.setPost(postMapper.entityToModel(entity.getPost()));
        if (entity.getAccount() != null) {
            comment.setParent(this.entityToModel(entity.getParent()));
        } else {
            comment.setParent(null);
        }

        // Map Account
        Account account = accountMapper.entityToModel(entity.getAccount());
        comment.setAccount(account);

        return comment;
    }
}
