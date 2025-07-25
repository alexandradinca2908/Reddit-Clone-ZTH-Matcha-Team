package org.example.api;

import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.userinterface.UIComment;

import java.util.Scanner;

public class CommentApiClient extends BaseApiClient{
    private static CommentApiClient instance;
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
        uiComment.pleaseEnter("comment");
        String input = sc.nextLine();
        Comment comm = new Comment(post, user, input);

        post.getCommentList().add(comm);
        uiComment.addedSuccessfully("comment");

        //TODO - Trebuie trimisa postarea cu comentariul adaugat - Se face in PostApiClient
    }

    //GET
    public Comment selectReply(Comment currentComment) {
        uiComment.pleaseEnter("replyID");
        return null;
    }

    //POST
    public void addReply(User user, Comment comment) {
    }

    //GET
    public Comment selectComment(Post post) {
        return null;
    }
}
