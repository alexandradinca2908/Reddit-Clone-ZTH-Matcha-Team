package org.matcha.springbackend.service;

import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.loggerobjects.Logger;
import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, PasswordService passwordService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public Account userRegister(Account account) {
        Logger.debug("userRegister called for username: " + account.getUsername());
        AccountEntity entity = accountMapper.modelToEntity(account);
        entity.setAccountId(null);
        Logger.debug("AccountEntity created and accountId set to null for username: " + account.getUsername());
        AccountEntity saved = accountRepository.save(entity);
        Logger.debug("AccountEntity saved for username: " + account.getUsername());
        return accountMapper.entityToModel(saved);
    }

    public Account findByUsername(String username) {
        AccountEntity entity = accountRepository.findByUsername(username).orElse(null);
        return entity != null ? accountMapper.entityToModel(entity) : null;
    }
}
