package org.matcha.springbackend.service;

import org.matcha.springbackend.entities.VoteEntity;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.repositories.VoteRepository;

import java.util.UUID;

public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;

    public VoteService(VoteRepository voteRepository, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
    }

    public Vote getVoteByAccountID(UUID id) {
        return voteRepository.findById(id)
                .map(voteMapper::entityToModel)
                .orElse(null);
    }

    public void addVote(Vote vote) {
        VoteEntity entity = voteMapper.modelToEntity(vote);
        voteRepository.save(entity);
    }

    public void deleteVoteByID(UUID id) {
        voteRepository.deleteByVoteId(id);
    }
}
