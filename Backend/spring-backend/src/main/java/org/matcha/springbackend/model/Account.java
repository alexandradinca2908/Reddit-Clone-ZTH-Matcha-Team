package org.matcha.springbackend.model;

public class Account {
    String username;
    String email;
    String password;

    public Account(String userName, String email, String password) {
        this.username = userName;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}