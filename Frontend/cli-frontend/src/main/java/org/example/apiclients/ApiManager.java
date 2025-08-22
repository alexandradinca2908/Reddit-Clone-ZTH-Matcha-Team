package org.example.apiclients;

import java.net.http.HttpClient;

public class ApiManager {
    private final ApiPostClient apiPostClient;
    private final ApiCommentClient apiCommentClient;
    private final ApiSubredditClient apiSubredditClient;
    private static ApiManager instance;

    private ApiManager(HttpClient httpClient) {
        apiPostClient = ApiPostClient.getInstance(httpClient);
        apiCommentClient = ApiCommentClient.getInstance(httpClient);
        apiSubredditClient = ApiSubredditClient.getInstance(httpClient);
    }

    public static ApiManager getInstance(HttpClient httpClient) {
        if (instance == null) {
            instance = new ApiManager(httpClient);
        }
        return instance;
    }

    public ApiPostClient getApiPostClient() {
        return apiPostClient;
    }

    public ApiCommentClient getApiCommentClient() {
        return apiCommentClient;
    }

    public ApiSubredditClient getApiSubredditClient() {
        return apiSubredditClient;
    }
}
