package org.matcha.springbackend.controller;

import org.matcha.springbackend.loggerobject.Logger;
import org.matcha.springbackend.model.Account;
import org.matcha.springbackend.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("{username}")
    public Account getUserByUsername(@PathVariable String username) {
        return accountService.findByUsername(username);
    }

    @PostMapping("/add")
    public Account registerUser(@RequestBody Account account) {
        Logger.debug("Register endpoint called for username: " + account.getUsername());
        Account registered = accountService.userRegister(account);
        Logger.debug("Register endpoint finished for username: " + account.getUsername());
        return registered;
    }
}
