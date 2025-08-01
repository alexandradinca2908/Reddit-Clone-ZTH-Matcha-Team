package org.matcha.springbackend.service;

import org.matcha.springbackend.entities.CommentEntity;
import org.matcha.springbackend.mapper.CommentMapper;
import org.matcha.springbackend.model.Comment;
import org.matcha.springbackend.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public CommentService(CommentMapper commentMapper, CommentRepository commentRepository) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsByPostId(UUID postId) {
        List<CommentEntity> entities = commentRepository.findByPostId(postId).orElse(new ArrayList<>());

        return entities.stream()
                .map(commentMapper::entityToModel)
                .collect(Collectors.toList());
    }

    //  TODO
    public List<Comment> organizeCommentsByHierarchy(List<Comment> comments) {
        return null;
    }

    public void addCommentToPost(Comment comment) {
        CommentEntity commentEntity = commentMapper.modelToEntity(comment);
        commentRepository.save(commentEntity);
    }
}
