package org.matcha.springbackend.repository;

import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.entities.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {
    Optional<VoteEntity> findByAccountAndVotableId(AccountEntity account, UUID votableId);;
    void deleteByVoteId(UUID id);
    Optional<List<VoteEntity>> findByAccountAndVotableIdIn(AccountEntity account, List<UUID> votableIds);
}
