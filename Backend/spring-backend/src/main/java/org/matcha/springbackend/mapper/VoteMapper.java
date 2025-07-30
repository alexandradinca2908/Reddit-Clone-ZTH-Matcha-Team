package org.matcha.springbackend.mapper;

import org.matcha.springbackend.entities.VoteEntity;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.mapper.AccountMapper;
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

        if (model.getVote() != null) {
            entity.setVoteType(org.matcha.springbackend.entities.VoteType.valueOf(model.getVote()));
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
        }
        String voteType = entity.getVoteType() != null ? entity.getVoteType().name() : null;
        return new Vote(
            entity.getVoteId(),
            entity.getVotableId(),
            null,
            entity.getVotableType(),
            voteType,
            account
        );
    }

    public AllVotesDTO modelToDTO(Vote model) {
        if (model == null) return null;
        Integer upvotes = null;
        Integer downvotes = null;
        Integer score = null;
        String userVote = model.getVote();
        return new AllVotesDTO(upvotes, downvotes, score, userVote);
    }
}
