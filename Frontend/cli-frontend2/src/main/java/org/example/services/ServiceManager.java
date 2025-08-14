package org.example.services;

import com.google.gson.Gson;

import java.net.http.HttpClient;

public class ServiceManager {
    private static ServiceManager instance;
    private PostService postService;
    private CommentService commentService;

    private ServiceManager(HttpClient client, Gson gson) {
        postService = PostService.getInstance(client, gson);
        commentService = CommentService.getInstance(client, gson);

    }

    public static ServiceManager getInstance(HttpClient client, Gson gson) {
        if (instance == null) {
            instance = new ServiceManager(client, gson);
        }
        return instance;
    }

    public PostService getPostService() {
        return postService;
    }

    public CommentService getCommentService() {
        return commentService;
    }
}
