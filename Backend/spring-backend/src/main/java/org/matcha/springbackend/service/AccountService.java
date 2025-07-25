package org.matcha.springbackend.service;

import org.matcha.springbackend.model.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final List<Account> accounts;
    private final PasswordService passwordService;

    public AccountService(PasswordService passwordService) {
        this.accounts = new ArrayList<>();
        this.passwordService = passwordService;
    }

    public Account userRegister(Account account) {
        Account newAccount = new Account(account.getUsername(), account.getEmail(), account.getPassword());
        accounts.add(newAccount);

        //  TODO - add in DB
        return newAccount;
    }

    public Account findByUsername(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }

        for (Account account : accounts) {
            if (username.equals(account.getUsername())) {
                return account;
            }
        }

        return null;
    }
}
