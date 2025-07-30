package org.matcha.springbackend.service;

import org.matcha.springbackend.entities.PostEntity;
import org.matcha.springbackend.entities.VoteEntity;
import org.matcha.springbackend.mapper.VoteMapper;
import org.matcha.springbackend.model.Post;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.repositories.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;

    public VoteService(VoteRepository voteRepository, VoteMapper voteMapper) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
    }

    public Vote getVoteByAccountAndVotable(UUID accountId, UUID votableId) {
        return voteRepository.findByAccountIdAndVotableId(accountId, votableId)
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

    public void updateVote(Vote vote) {
        VoteEntity entity = voteMapper.modelToEntity(vote);
        if (voteRepository.existsById(entity.getVoteId())) {
            voteRepository.save(entity);
        } else {
            throw new IllegalArgumentException("Vote with ID " + entity.getVoteId() + " does not exist.");
        }
    }
}
