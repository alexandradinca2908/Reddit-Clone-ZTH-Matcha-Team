package org.example.api;

import org.example.DTO.Comment;
import org.example.DTO.Post;
import org.example.DTO.User;

public class CommentApiClient extends BaseApiClient{
    private static CommentApiClient instance;
    private CommentApiClient(String baseUrl) {
        super(baseUrl);
    }

    public static CommentApiClient getInstance(String baseUrl) {
        if (instance == null) {
            instance = new CommentApiClient(baseUrl);
        }
        return instance;
    }

    public void addComment(User user, Post post) {
    }

    public Comment selectReply(Comment currentComment) {
        return null;
    }

    public void addReply(User user, Comment comment) {
    }

    public Comment selectComment(Post post) {
        return null;
    }
}
