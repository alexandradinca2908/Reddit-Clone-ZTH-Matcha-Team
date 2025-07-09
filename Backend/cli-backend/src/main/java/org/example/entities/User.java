package org.example.entities;

public class User {
    int userID;
    static int userCounter = 0;
    String userName;
    String email;
    String password;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userID = userCounter++;
    }

    public String getUsername() {
        return userName;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
