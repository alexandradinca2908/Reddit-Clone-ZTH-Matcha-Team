package org.example.api;

import org.example.DTO.User;

public class UserApiClient extends BaseApiClient {
    public UserApiClient(String baseUrl) {
        super(baseUrl);
    }

    public User userRegisterCLI() {
        System.out.println("Accounts have been disabled");
        return null;
    }

    public User userLoginCLI() {
        System.out.println("Accounts have been disabled");
        return null;
    }

    public boolean userDeleteCLI(User user) {
        System.out.println("Accounts have been disabled");
        return false;
    }
}

