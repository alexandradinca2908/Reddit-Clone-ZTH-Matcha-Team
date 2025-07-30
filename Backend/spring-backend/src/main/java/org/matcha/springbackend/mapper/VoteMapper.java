package org.matcha.springbackend.mapper;

import org.matcha.springbackend.entities.VoteEntity;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.dto.vote.AllVotesDTO;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {
    private final AccountMapper accountMapper;

    public VoteMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public VoteEntity modelToEntity(Vote model) {
        if (model == null) return null;
        VoteEntity entity = new VoteEntity();
        entity.setVoteId(model.getVoteID());
        entity.setVotableId(model.getVotableID());
        entity.setVotableType(model.getVotableType());

        if (model.getVoteType() != null) {
            entity.setVoteType(model.getVoteType());
        }
        if (model.getAccount() != null) {
            entity.setAccount(accountMapper.modelToEntity(model.getAccount()));
        }
        return entity;
    }

    public Vote entityToModel(VoteEntity entity) {
        if (entity == null) return null;

        Account account = null;
        if (entity.getAccount() != null) {
            account = accountMapper.entityToModel(entity.getAccount());
        } else {
            return null;
        }

        return new Vote(
            entity.getVoteId(),
            entity.getVotableId(),
            entity.getVotableType(),
            entity.getVoteType(),
            account
        );
    }

    public AllVotesDTO modelToDTO(Vote model) {
        if (model == null) return null;
        Integer upvotes = null;
        Integer downvotes = null;
        Integer score = null;
        String userVote = model.getVoteType().toString().toLowerCase();

        return new AllVotesDTO(upvotes, downvotes, score, userVote);
    }
}
