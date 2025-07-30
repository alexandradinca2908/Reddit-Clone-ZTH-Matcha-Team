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
            entity.setAccountId(model.getAccount().getAccountId());
        }
        return entity;
    }

    public Vote entityToModel(VoteEntity entity) {
        if (entity == null) return null;
        String voteType = entity.getVoteType() != null ? entity.getVoteType().name() : null;
        return new Vote(
            entity.getVoteId(),
            entity.getVotableId(),
            voteType, // or null, depending on your Vote constructor
            entity.getVotableType(),
            voteType,
            null // No Account object, only UUID is available
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
