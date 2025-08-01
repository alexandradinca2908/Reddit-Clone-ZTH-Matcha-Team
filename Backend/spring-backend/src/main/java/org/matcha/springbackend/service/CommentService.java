package org.matcha.springbackend.service;

import org.matcha.springbackend.dto.comment.requestbody.AddCommentBodyDTO;
import org.matcha.springbackend.entities.CommentEntity;
import org.matcha.springbackend.enums.VoteType;
import org.matcha.springbackend.loggerobject.Logger;
import org.matcha.springbackend.mapper.CommentMapper;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.repository.CommentRepository;
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

    public CommentService(CommentMapper commentMapper, CommentRepository commentRepository, AccountSession accountSession, PostService postService) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.accountSession = accountSession;
        this.postService = postService;
    }

    public List<Comment> getCommentsByPostId(UUID postId) {
        List<CommentEntity> entities = commentRepository.findByPost_PostID(postId).orElse(new ArrayList<>());

        return entities.stream()
                .map(commentMapper::entityToModel)
                .collect(Collectors.toList());
    }

    //  TODO
    public List<Comment> organizeCommentsByHierarchy(List<Comment> comments) {
        return null;
    }

    public Comment addCommentToPost(String postId, AddCommentBodyDTO commentDto) {
        OffsetDateTime createdAt = OffsetDateTime.now();

        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }

        Comment comment = new Comment(null, accountSession.getCurrentAccount(), null,
                post, commentDto.content(), false, 0, 0, VoteType.NONE, createdAt, createdAt);
        CommentEntity commentEntity = commentMapper.modelToEntity(comment);

        try {
            commentRepository.save(commentEntity);
            Logger.info("[CommentService] Comment saved with title: " + post.getTitle());
        } catch (Exception e) {
            Logger.error("[CommentService] Exception at save: " + e.getMessage());
            throw e;
        }

        //  Retrieve JPA-populated entity as model
        return commentMapper.entityToModel(commentEntity);
    }
}
