package org.matcha.springbackend.mapper;

import org.matcha.springbackend.dto.comment.CommentDTO;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.entities.CommentEntity;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.repositories.AccountRepository;
import org.springframework.stereotype.Component;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class CommentMapper {
    private final AccountRepository accountRepository;

    public CommentMapper(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public CommentEntity modelToEntity(Comment comment) {
        if (comment == null) return null;
        CommentEntity entity = new CommentEntity();
        entity.setCommentId(comment.getCommentId());
        entity.setText(comment.getText());
        entity.setDeleted(comment.isDeleted());
        entity.setCreatedAt(comment.getCreatedAt());
        entity.setUpdatedAt(comment.getUpdatedAt());
        entity.setPostId(comment.getPostId());
        entity.setParentId(comment.getParentId());
        // Map Account
        if (comment.getAccount() != null && comment.getAccount().getAccountId() != null) {
            AccountEntity accountEntity = accountRepository.findById(comment.getAccount().getAccountId()).orElse(null);
            entity.setAccount(accountEntity);
        }
        // Upvotes, downvotes, score can be set if present in model
        return entity;
    }

    public CommentDTO modelToDTO(Comment model) {
        String id = model.getCommentId().toString();
        String postId = model.getPostId() != null ? model.getPostId().toString() : null;
        String parentId = model.getParentId() != null ? model.getParentId().toString() : null;
        String content = model.getText();
        String author = model.getAccount() != null ? model.getAccount().getUsername() : null;
        int upvotes = model.getUpvotes();
        int downvotes = model.getDownvotes();
        int score = upvotes - downvotes;
        String userVote = "null"; // TODO: implement if needed
        String createdAt = model.getCreatedAt() != null ? model.getCreatedAt().toString() : null;
        String updatedAt = model.getUpdatedAt() != null ? model.getUpdatedAt().toString() : null;
        List<CommentDTO> replies = model.getComments() != null ? model.getComments().stream().map(this::modelToDTO).collect(Collectors.toList()) : null;
        return new CommentDTO(id, postId, parentId, content, author, upvotes, downvotes, score, userVote, createdAt, updatedAt, replies);
    }

    public Comment entityToModel(CommentEntity entity) {
        if (entity == null) return null;
        Comment comment = new Comment();
        comment.setCommentId(entity.getCommentId());
        comment.setText(entity.getText());
        comment.setDeleted(entity.isDeleted());
        comment.setCreatedAt(entity.getCreatedAt());
        comment.setUpdatedAt(entity.getUpdatedAt());
        comment.setPostId(entity.getPostId());
        comment.setParentId(entity.getParentId());
        // Map Account
        Account account = new Account();
        if (entity.getAccount() != null) {
            account.setAccountId(entity.getAccount().getAccountId());
            account.setUsername(entity.getAccount().getUsername());
            account.setEmail(entity.getAccount().getEmail());
            account.setPhotoPath(entity.getAccount().getPhotoPath());
        }
        comment.setAccount(account);
        // Comments (replies) can be set if needed
        return comment;
    }
}
