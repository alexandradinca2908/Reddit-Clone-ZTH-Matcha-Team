package org.matcha.springbackend.repository;

import org.matcha.springbackend.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
    Optional<List<CommentEntity>> findByPost_PostIDAndIsDeletedFalseOrderByCreatedAtAsc(UUID postId);
    Optional<CommentEntity> findByCommentId(UUID votableID);

    @Modifying
    @Query("UPDATE CommentEntity c SET c.upvotes = c.upvotes + 1 WHERE c.commentId = :commentId")
    void incrementUpvotes(@Param("commentId") UUID commentId);

    @Modifying
    @Query("UPDATE CommentEntity c SET c.downvotes = c.downvotes + 1 WHERE c.commentId = :commentId")
    void incrementDownvotes(@Param("commentId") UUID commentId);

    @Modifying
    @Query("UPDATE CommentEntity c SET c.upvotes = c.upvotes - 1 WHERE c.commentId = :commentId")
    void decrementUpvotes(@Param("commentId") UUID commentId);

    @Modifying
    @Query("UPDATE CommentEntity c SET c.downvotes = c.downvotes - 1 WHERE c.commentId = :commentId")
    void decrementDownvotes(@Param("commentId") UUID commentId);

    @Modifying
    @Query("UPDATE CommentEntity c SET c.upvotes = c.upvotes - 1, c.downvotes = c.downvotes + 1 WHERE c.commentId = :commentId")
    void decrementUpvotesAndIncrementDownvotes(@Param("commentId") UUID commentId);

    @Modifying
    @Query("UPDATE CommentEntity c SET c.downvotes = c.downvotes - 1, c.upvotes = c.upvotes + 1 WHERE c.commentId = :commentId")
    void decrementDownvotesAndIncrementUpvotes(@Param("commentId") UUID commentId);

    boolean existsByCommentIdAndIsDeletedFalse(UUID commentId);
}
