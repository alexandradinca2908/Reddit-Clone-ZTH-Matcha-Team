package org.example.services;
import org.example.Post;
import org.example.User;

import java.util.ArrayList;
import java.util.Scanner;

public class CommentService {
    Scanner sc = new Scanner(System.in);
    private Post post;
    private User user;

    CommentService(User user, Post post) {
        this.post = post;
        this.user = user;
    }

    public void addComment() {
        System.out.println("Please enter comment text: ");
        String commentText = sc.nextLine();
        post.addComment(user, commentText);
    }
}
