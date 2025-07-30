package org.matcha.springbackend.repositories;

import org.matcha.springbackend.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    Optional<PostEntity> findByPostID(UUID uuid);
}
