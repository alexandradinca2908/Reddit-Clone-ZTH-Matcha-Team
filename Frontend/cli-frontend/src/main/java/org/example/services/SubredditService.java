package org.example.services;

public class SubredditService {
    private static SubredditService instance;

    private SubredditService() {}

    public static SubredditService getInstance() {
        if (instance == null) {
            instance = new SubredditService();
        }
        return instance;
    }
}
