package org.matcha.springbackend.controllers;

import org.matcha.springbackend.models.User;
import org.matcha.springbackend.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/add")
    public User registerUser(@RequestBody User user) {
        return userService.userRegister(user);
    }
}
