package org.example.services;
import org.example.Post;

import java.util.ArrayList;
import java.util.Scanner;

public class PostService {
    Scanner sc = new Scanner(System.in);
    private static ArrayList<Post> posts = new ArrayList<>();

    public void addPost(String username) {
        System.out.println("Please enter title: ");
        String title = sc.nextLine();
        System.out.println("Please enter description: ");
        String body = sc.nextLine();

        Post post = new Post(title, body, username);

        posts.add(post);

    }

    public void deletePost(int postID) {
        for (Post iter : posts) {
            if (iter.getPostID() == postID) {
                posts.remove(iter);
            }
        }
    }

    public void showFeed() {
        for  (Post iter : posts) {
            System.out.println(iter.getPostID());
            System.out.println(iter.title);
            System.out.println();
            System.out.println(iter.body.substring(0, 40).concat("..."));
            System.out.println();
            System.out.print("UP ".concat(String.valueOf(iter.voteCount)));
            System.out.println("DOWN");
            System.out.println("---");
        }
    }

    public void expandPost(int postID) {
        for (Post iter : posts) {
            System.out.println(iter.title);
            System.out.println();
            System.out.println(iter.body);
            System.out.println();
            System.out.print("UP ".concat(String.valueOf(iter.voteCount)));
            System.out.println("DOWN");
            System.out.println();
        }
    }
}
