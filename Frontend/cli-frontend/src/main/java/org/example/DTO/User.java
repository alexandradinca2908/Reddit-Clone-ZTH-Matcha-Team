package org.example.DTO;

public class User {
    String userName;
    String email;
    String password;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }
}
