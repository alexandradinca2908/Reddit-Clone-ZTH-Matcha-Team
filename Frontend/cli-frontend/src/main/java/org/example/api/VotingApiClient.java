package org.example.api;

import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;

public class VotingApiClient extends BaseApiClient{
    public static VotingApiClient instance;
    private VotingApiClient(String baseUrl) {
        super(baseUrl);
    }

    public static VotingApiClient getInstance(String baseUrl) {
        if (instance == null) {
            instance = new VotingApiClient(baseUrl);
        }
        return instance;
    }

    public void voteComment(User user, Comment comment, boolean b) {
    }

    public void votePost(User user, Post post, boolean b) {

    }
}
