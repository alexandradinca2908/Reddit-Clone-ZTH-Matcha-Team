package org.example.api;

public class ApiManager {
    private static ApiManager instance;
    private final UserApiClient userApiClient;
    private final PostApiClient postApiClient;
    private final CommentApiClient commentApiClient;
    private final VotingApiClient votingApiClient;

    public ApiManager(String baseUrl) {
        this.userApiClient = new UserApiClient(baseUrl);
        this.postApiClient = new PostApiClient(baseUrl);
        this.commentApiClient = new CommentApiClient(baseUrl);
        this.votingApiClient = new VotingApiClient(baseUrl);
    }

    public static ApiManager getInstance(String baseUrl) {
        if (instance == null) {
            instance = new ApiManager(baseUrl);
        }
        return instance;
    }


    public UserApiClient getUserApiClient() {
        return userApiClient;
    }

    public PostApiClient getPostApiClient() {
        return postApiClient;
    }

    public CommentApiClient getCommentApiClient() {
        return commentApiClient;
    }

    public VotingApiClient getVotingApiClient() {
        return votingApiClient;
    }

}