package org.matcha.springbackend.service;

import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final Account currentAccount;

    public AccountService(AccountRepository accountRepository, PasswordService passwordService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.currentAccount = accountMapper.entityToModel(accountRepository.findByUsername("Root").orElse(null));
    }

    public Account userRegister(Account account) {
        AccountEntity entity = accountMapper.modelToEntity(account);
        entity.setAccountId(UUID.randomUUID());
        AccountEntity saved = accountRepository.save(entity);
        return accountMapper.entityToModel(saved);
    }

    public Account findByUsername(String username) {
        AccountEntity entity = accountRepository.findByUsername(username).orElse(null);
        return entity != null ? accountMapper.entityToModel(entity) : null;
    }

    public Account findByID(UUID id) {
        AccountEntity entity = accountRepository.findById(id).orElse(null);
        return entity != null ? accountMapper.entityToModel(entity) : null;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }
}
