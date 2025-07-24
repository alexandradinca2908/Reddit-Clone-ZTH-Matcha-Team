package org.example.api;

import org.example.DTO.Comment;
import org.example.DTO.Post;
import org.example.DTO.User;

public class VotingApiClient extends BaseApiClient{
    public VotingApiClient(String baseUrl) {
        super(baseUrl);
    }

    public void voteComment(User user, Comment comment, boolean b) {
    }

    public void votePost(User user, Post post, boolean b) {

    }
}
