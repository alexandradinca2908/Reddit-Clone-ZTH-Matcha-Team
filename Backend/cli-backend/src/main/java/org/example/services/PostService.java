package org.example.services;
import org.example.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostService {
    Scanner sc = new Scanner(System.in);
    private static ArrayList<Post> posts = new ArrayList<>(
            List.of(
                    new Post("First Post", "This is the body of the first post.", "TestUser1"),
                    new Post("Second Post", "This is the body of the second post.", "TestUser2"),
                    new Post("Third Post", "This is the body of the third post.", "TestUser1")
            )
    );

    public void addPost(String username) {
        System.out.println("Please enter title: ");
        String title = sc.nextLine();
        System.out.println("Please enter description: ");
        String body = sc.nextLine();

        Post post = new Post(title, body, username);

        posts.add(post);
        System.out.println("Successfully added post");

    }

    public void deletePost(int postID) {
        for (Post iter : posts) {
            if (iter.getPostID() == postID) {
                posts.remove(iter);
            }
        }
    }

    public void showFeed() {
        for (Post iter : posts) {
            System.out.println("\n---------------");
            System.out.println("Post ID: " + iter.getPostID() + " | User ID: " + iter.getOwnershipID() + "\n");
            System.out.println(iter.title);
            String preview = iter.body.length() > 20 ? iter.body.substring(0, 10) + "..." : iter.body;
            System.out.println(preview + "\n");
            System.out.println("UP " + iter.voteCount + " DOWN | " + iter.getCommentsCounter() + " comments");
        }
    }

    public void expandPost() {
        System.out.println("Please enter postID: ");
        int postID = Integer.parseInt(sc.nextLine());
        for (Post iter : posts) {
            if  (iter.getPostID() == postID) {
                System.out.println("---------------");
                System.out.println("\nPost ID: " + iter.getPostID() + " | User ID: " + iter.getOwnershipID());
                System.out.println(iter.title);
                System.out.println(iter.body + "\n");
                System.out.println("UP " + iter.voteCount + " DOWN | " + iter.getCommentsCounter() + " comments\n");
            }

        }
    }
}
