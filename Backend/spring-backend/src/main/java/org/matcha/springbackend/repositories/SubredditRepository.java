package org.matcha.springbackend.repositories;

import org.matcha.springbackend.entities.SubredditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubredditRepository extends JpaRepository<SubredditEntity, UUID> {
}
