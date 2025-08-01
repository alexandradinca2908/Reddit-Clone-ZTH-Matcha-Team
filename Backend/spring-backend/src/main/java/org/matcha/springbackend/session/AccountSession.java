package org.matcha.springbackend.session;

import org.matcha.springbackend.mapper.AccountMapper;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.repositories.AccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
public class AccountSession {
    private Account currentAccount;

    public AccountSession(AccountMapper accountMapper, AccountRepository accountRepository) {
        this.currentAccount = accountMapper.entityToModel(accountRepository
                .findByUsername("current_user").orElse(null));
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public boolean isLoggedIn() {
        return currentAccount != null;
    }
}
