package org.matcha.springbackend.repositories;

import org.matcha.springbackend.entities.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {
    Optional<VoteEntity> findByAccountAndPost(UUID accountId, UUID postId);;
    void deleteByVoteId(UUID id);
}
