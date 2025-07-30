package org.example.api;

import org.example.dto.CommentDTO;
import org.example.jsonconverter.CommentToJson;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.userinterface.UIComment;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class CommentApiClient extends BaseApiClient{
    private static CommentApiClient instance;
    private static CommentToJson commentToJson = CommentToJson.getInstance();
    private static UIComment uiComment = UIComment.getInstance();
    private final Scanner sc = new Scanner(System.in);

    private CommentApiClient(String baseUrl) {
        super(baseUrl);
    }

    public static CommentApiClient getInstance(String baseUrl) {
        if (instance == null) {
            instance = new CommentApiClient(baseUrl);
        }
        return instance;
    }
    //POST
    public void addComment(User user, Post post) {

    }

    //GET
    public Comment selectReply(Comment currentComment) {
        return null;
    }

    //POST
    public void addReply(User user, Comment comment) {
    }

    //GET
    public Comment selectComment(Post post) throws URISyntaxException, IOException, InterruptedException {
        return null;
    }
}
