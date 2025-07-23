package org.matcha.springbackend.services;

import org.matcha.springbackend.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    public ArrayList<User> users;
    private final PasswordService passwordService;

    public UserService(PasswordService passwordService) {
        this.users = new ArrayList<>();
        this.passwordService = passwordService;
    }

    public User userRegister(User user) {
        User newUser = new User(user.getUsername(), user.getEmail(), user.getPassword());
        users.add(newUser);

        //  TODO - add in DB
        return newUser;
    }

    public User findByUsername(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }

        for (User user : users) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }

        return null;
    }
}
