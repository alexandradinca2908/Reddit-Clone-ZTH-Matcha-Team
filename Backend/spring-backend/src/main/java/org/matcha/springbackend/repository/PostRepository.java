package org.matcha.springbackend.repository;

import org.matcha.springbackend.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    Optional<PostEntity> findByPostIDAndIsDeletedFalse(UUID uuid);

    List<PostEntity> findAllByIsDeletedFalseOrderByCreatedAtDesc();

    List<PostEntity> findAllBySubreddit_Name(String name);
}
