package org.example.api;

import org.example.models.User;

public class UserApiClient extends BaseApiClient {
    private static UserApiClient instance;
    private static final String ACCOUNTS_DISABLED = "Accounts have been disabled";
    private UserApiClient(String baseUrl) {
        super(baseUrl);
    }

    public static UserApiClient getInstance(String baseUrl) {
        if (instance == null) {
            instance = new UserApiClient(baseUrl);
        }
        return instance;
    }

    public User userRegisterCLI() {
        System.out.println(ACCOUNTS_DISABLED);
        return null;
    }

    public User userLoginCLI() {
        System.out.println(ACCOUNTS_DISABLED);
        return null;
    }

    public boolean userDeleteCLI(User user) {
        System.out.println(ACCOUNTS_DISABLED);
        return false;
    }
}

