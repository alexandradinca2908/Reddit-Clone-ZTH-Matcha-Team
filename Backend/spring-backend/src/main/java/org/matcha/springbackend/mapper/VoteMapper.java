package org.matcha.springbackend.mapper;

import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.entities.VoteEntity;
import org.matcha.springbackend.model.Vote;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    public VoteMapper(AccountMapper accountMapper, AccountRepository accountRepository) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
    }

    public VoteEntity modelToEntity(Vote model) {
        if (model == null) return null;

        VoteEntity entity = new VoteEntity();
        if (model.getVoteID() != null) {
            entity.setVoteId(model.getVoteID());
        } else {
            entity.setVoteId(null);
        }

        entity.setVotableId(model.getVotableID());
        entity.setVotableType(model.getVotableType());
        if (model.getVoteType() != null) {
            entity.setVoteType(model.getVoteType());
        }

        if (model.getAccount() != null && model.getAccount().getAccountId() != null) {
            AccountEntity accountEntity = accountRepository.findById(model.getAccount().getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found in DB for id: " + model.getAccount().getAccountId()));
            if (accountEntity.getAccountId() == null) {
                throw new IllegalArgumentException("AccountEntity loaded from DB has null ID! This should never happen.");
            }
            entity.setAccount(accountEntity);
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
}
