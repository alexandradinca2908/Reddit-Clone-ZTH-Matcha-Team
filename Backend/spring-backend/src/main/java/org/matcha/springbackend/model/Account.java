package org.matcha.springbackend.model;

public class Account {
    //  TODO - delete all and rewrite
    String username;
    String email;
    String password;

    public Account() {
        this.username = "cineva";
        this.email = "test@ex.com";
        this.password = "123456";
    }

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