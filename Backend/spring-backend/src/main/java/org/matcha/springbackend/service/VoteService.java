package org.matcha.springbackend.service;

import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.repositories.VoteRepository;

public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;

    public VoteService(VoteRepository voteRepository, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
    }

    public Vote getVoteByID(String id) {
        return voteRepository.findById(java.util.UUID.fromString(id))
                .map(voteMapper::entityToModel)
                .orElse(null);
    }
}
