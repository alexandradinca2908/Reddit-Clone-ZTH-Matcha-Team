package org.matcha.springbackend.service;

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
    private final Account currentAccount;
    private final PasswordService passwordService;

    public AccountService(AccountRepository accountRepository, PasswordService passwordService) {
        this.accounts = new ArrayList<>();
        this.accountRepository = accountRepository;
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
        return accountRepository.findByUsername(username).orElse(null);
    }
}
