package org.example.api;

public class ApiManager {
    private static ApiManager instance;
    private final UserApiClient userApiClient;
    private final PostApiClient postApiClient;
    private final CommentApiClient commentApiClient;
    private final VotingApiClient votingApiClient;

    public ApiManager(String baseUrl) {
        this.userApiClient = UserApiClient.getInstance(baseUrl);
        this.postApiClient = PostApiClient.getInstance(baseUrl);
        this.commentApiClient = CommentApiClient.getInstance(baseUrl);
        this.votingApiClient = VotingApiClient.getInstance(baseUrl);
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