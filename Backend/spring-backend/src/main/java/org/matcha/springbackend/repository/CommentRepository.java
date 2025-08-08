package org.matcha.springbackend.repository;

import org.matcha.springbackend.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
    Optional<List<CommentEntity>> findByPost_PostIDAndIsDeletedFalseOrderByCreatedAtAsc(UUID postId);
    Optional<CommentEntity> findByCommentId(UUID votableID);
}
