package org.example.services;
import org.example.entities.Post;
import org.example.entities.User;

import java.util.Scanner;

public class CommentService {
    Scanner sc = new Scanner(System.in);
    private Post post;
    private User user;


    public void addComment(User user, Post post) {
        System.out.println("Please enter comment text: ");
        String commentText = sc.nextLine();
        post.addComment(user, commentText);
    }
}
