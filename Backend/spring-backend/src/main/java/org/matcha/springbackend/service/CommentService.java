package org.matcha.springbackend.service;

import jakarta.transaction.Transactional;
import org.matcha.springbackend.dto.comment.requestbody.AddCommentBodyDTO;
import org.matcha.springbackend.entities.CommentEntity;
import org.matcha.springbackend.entities.PostEntity;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.loggerobject.Logger;
import org.matcha.springbackend.mapper.CommentMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.repository.CommentRepository;
import org.matcha.springbackend.repository.PostRepository;
import org.matcha.springbackend.session.AccountSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final AccountSession accountSession;
    private final PostService postService;
    private final PostRepository postRepository;

    public CommentService(CommentMapper commentMapper, CommentRepository commentRepository, AccountSession accountSession, PostService postService, PostRepository postRepository) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.accountSession = accountSession;
        this.postService = postService;
        this.postRepository = postRepository;
    }

    public List<Comment> getCommentsByPostId(UUID postId) {
        List<CommentEntity> entities = commentRepository.findByPost_PostID(postId).orElse(new ArrayList<>());

        return entities.stream()
                .map(commentMapper::entityToModel)
                .collect(Collectors.toList());
    }

    public Comment getCommentById(String commentId) {
        return commentRepository.findByCommentId(UUID.fromString(commentId))
                .map(commentMapper::entityToModel)
                .orElse(null);
    }

    //  TODO
    public List<Comment> organizeCommentsByHierarchy(List<Comment> comments) {
        return null;
    }

    @Transactional
    public Comment addCommentToPost(String postId, AddCommentBodyDTO commentDto) {
        OffsetDateTime createdAt = OffsetDateTime.now();

        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        Comment comment = new Comment(null, accountSession.getCurrentAccount(), null,
                post, commentDto.content(), false, 0, 0,
                VoteType.NONE, createdAt, createdAt, new ArrayList<>());
        CommentEntity commentEntity = commentMapper.modelToEntity(comment);

        //  Save comment
        try {
            commentRepository.save(commentEntity);
            Logger.info("[CommentService] Comment saved with title: " + post.getTitle());
        } catch (Exception e) {
            Logger.error("[CommentService] Exception at save: " + e.getMessage());
            throw e;
        }

        //  Update comment counter for the parent post
        try {
            PostEntity postEntity = postRepository.findByPostID(UUID.fromString(postId)).orElse(null);

            if (postEntity == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found when trying to update " +
                        "comment counter.");
            }

            int currentCount;
            if (postEntity.getCommentCount() != null) {
                currentCount = postEntity.getCommentCount();
            } else {
                currentCount = 0;
            }

            postEntity.setCommentCount(currentCount + 1);
            postRepository.save(postEntity);
        } catch (Exception e) {
            Logger.error("[CommentService] Exception at post update: " + e.getMessage());
            throw e;
        }

        //  Retrieve JPA-populated entity as model
        return commentMapper.entityToModel(commentEntity);
    }

    @Transactional
    public Comment updateComment(String commentId, String content) {
        CommentEntity commentEntity = commentRepository.findByCommentId(UUID.fromString(commentId))
                .orElseThrow(() -> new IllegalArgumentException("Comment not found in DB for id: " + commentId));

        Account currentUser = accountSession.getCurrentAccount();
        if (!commentEntity.getAccount().getAccountId().equals(currentUser.getAccountId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to edit this comment.");
        }

        commentEntity.setContent(content);
        commentRepository.save(commentEntity);

        return commentMapper.entityToModel(commentEntity);
    }
}
