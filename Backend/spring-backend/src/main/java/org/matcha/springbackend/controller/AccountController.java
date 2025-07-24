package org.matcha.springbackend.controller;

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
        return accountService.userRegister(account);
    }
}
