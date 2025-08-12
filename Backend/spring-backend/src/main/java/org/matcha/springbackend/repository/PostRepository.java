package org.matcha.springbackend.repository;

import org.matcha.springbackend.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    Optional<PostEntity> findByPostIDAndIsDeletedFalse(UUID uuid);

    List<PostEntity> findAllByIsDeletedFalseOrderByCreatedAtDesc();

    List<PostEntity> findAllBySubreddit_Name(String name);

    boolean existsByPostIDAndIsDeletedFalse(UUID uuid);

    @Modifying
    @Query("UPDATE PostEntity p SET p.commentCount = COALESCE(p.commentCount, 0) + 1 " +
            "WHERE p.postID = :postId AND p.isDeleted = false")
    void incrementCommentCount(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.commentCount = COALESCE(p.commentCount, 0) - 1 " +
            "WHERE p.postID = :postId AND p.isDeleted = false")
    void decrementCommentCount(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.upvotes = p.upvotes + 1 WHERE p.postID = :postId")
    void incrementUpvotes(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.downvotes = p.downvotes + 1 WHERE p.postID = :postId")
    void incrementDownvotes(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.upvotes = p.upvotes - 1 WHERE p.postID = :postId")
    void decrementUpvotes(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.downvotes = p.downvotes - 1 WHERE p.postID = :postId")
    void decrementDownvotes(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.upvotes = p.upvotes - 1, p.downvotes = p.downvotes + 1 WHERE p.postID = :postId")
    void decrementUpvotesAndIncrementDownvotes(@Param("postId") UUID postId);

    @Modifying
    @Query("UPDATE PostEntity p SET p.downvotes = p.downvotes - 1, p.upvotes = p.upvotes + 1 WHERE p.postID = :postId")
    void decrementDownvotesAndIncrementUpvotes(@Param("postId") UUID postId);
}
