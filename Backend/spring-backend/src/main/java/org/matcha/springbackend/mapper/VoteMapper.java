package org.matcha.springbackend.mapper;

import org.matcha.springbackend.entities.VoteEntity;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.dto.vote.AllVotesDTO;
import org.matcha.springbackend.service.AccountService;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {
    private final AccountMapper accountMapper;
    private final AccountService accountService;

    public VoteMapper(AccountMapper accountMapper, AccountService accountService) {
        this.accountMapper = accountMapper;
        this.accountService = accountService;
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
            entity.setAccountId(model.getAccount().getAccountId());
        }
        return entity;
    }

    public Vote entityToModel(VoteEntity entity) {
        if (entity == null) return null;

        Account account = null;
        if (entity.getAccountId() != null) {
            account = accountService.findByID(entity.getAccountId());
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
