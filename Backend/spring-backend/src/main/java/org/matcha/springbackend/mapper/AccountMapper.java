package org.matcha.springbackend.mapper;

import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.dto.account.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account entityToModel(AccountEntity entity) {
        if (entity == null) return null;
        Account account = new Account();
        account.setAccountId(entity.getAccountId());
        account.setUsername(entity.getUsername());
        account.setEmail(entity.getEmail());
        account.setPassword(entity.getPassword());
        account.setPhotoPath(entity.getPhotoPath());
        account.setDeleted(entity.isDeleted());
        account.setCreatedAt(entity.getCreatedAt());
        account.setUpdatedAt(entity.getUpdatedAt());
        return account;
    }

    public AccountEntity modelToEntity(Account model) {
        if (model == null) return null;
        AccountEntity entity = new AccountEntity();
        entity.setAccountId(model.getAccountId());
        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        entity.setPassword(model.getPassword());
        entity.setPhotoPath(model.getPhotoPath());
        entity.setDeleted(model.isDeleted());
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());
        return entity;
    }

    public AccountDTO modelToDTO(Account model) {
        if (model == null) return null;
        return new AccountDTO(
            model.getAccountId() != null ? model.getAccountId().toString() : null,
            model.getUsername(),
            model.getEmail(),
            model.getPhotoPath(),
            model.isDeleted(),
            model.getCreatedAt() != null ? model.getCreatedAt().toString() : null,
            model.getUpdatedAt() != null ? model.getUpdatedAt().toString() : null
        );
    }
}
