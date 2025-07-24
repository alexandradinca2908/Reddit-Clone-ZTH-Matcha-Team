package org.example.api;

import org.example.DTO.Comment;
import org.example.DTO.Post;
import org.example.DTO.User;

public class CommentApiClient extends BaseApiClient{
    public CommentApiClient(String baseUrl) {
        super(baseUrl);
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
