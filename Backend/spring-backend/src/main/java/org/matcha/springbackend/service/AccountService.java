package org.matcha.springbackend.service;

import org.matcha.springbackend.entities.AccountEntity;
import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private final List<Account> accounts;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final Account currentAccount;
    private final PasswordService passwordService;

    public AccountService(AccountRepository accountRepository, PasswordService passwordService, AccountMapper accountMapper) {
        this.accounts = new ArrayList<>();
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.currentAccount = findByUsername("Root");
        this.passwordService = passwordService;
    }

    public Account userRegister(Account account) {
        Account newAccount = new Account(UUID.randomUUID(), account.getUsername(), account.getEmail(), account.getPassword());
        accounts.add(newAccount);

        //  TODO - add in DB
        return newAccount;
    }

    public Account findByUsername(String username) {
        AccountEntity entity = accountRepository.findByUsername(username).orElse(null);
        return entity != null ? accountMapper.entityToModel(entity) : null;
    }
}
