package org.matcha.springbackend.repository;

import jakarta.persistence.LockModeType;
import org.matcha.springbackend.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PostEntity> findByPostID(UUID uuid);
}
