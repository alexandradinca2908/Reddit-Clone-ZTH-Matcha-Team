package org.matcha.springbackend.service;

import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.entities.PostEntity;
import org.matcha.springbackend.entities.VoteEntity;
import org.matcha.springbackend.repository.PostRepository;
import org.matcha.springbackend.repository.VoteRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CacheService {
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;

    public CacheService(PostRepository postRepository, VoteRepository voteRepository) {
        this.postRepository = postRepository;
        this.voteRepository = voteRepository;
    }

    @Cacheable("posts")
    public List<PostEntity> getAllPostsFromDb() {
        return postRepository.findAllByIsDeletedFalseOrderByCreatedAtDesc();
    }

    // Cache user votes per account
    @Cacheable(value = "userVotes", key = "#account.accountId")
    public List<VoteEntity> getUserVotes(AccountEntity account, List<UUID> postIds) {
        return voteRepository.findByAccountAndVotableIdIn(account, postIds)
                .orElse(new ArrayList<>());
    }
}
