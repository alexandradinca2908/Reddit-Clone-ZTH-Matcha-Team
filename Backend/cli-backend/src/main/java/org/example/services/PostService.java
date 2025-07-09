package org.example.services;
import org.example.textprocessors.AnsiColors;
import org.example.entities.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostService extends AnsiColors {
    Scanner sc = new Scanner(System.in);

    public void addPost(String username) {
        System.out.println(AnsiColors.toGreen("Please enter title: "));
        String title = sc.nextLine();
        while (title.length() < MAX_TEXT_LENGTH) {
            System.out.println(AnsiColors.toRed("Title must be at least " +  MAX_TEXT_LENGTH + " characters long."));
            title = sc.nextLine();
        }
        System.out.println(AnsiColors.toGreen("Please enter description: "));
        String body = sc.nextLine();
        while (body.isEmpty()) {
            System.out.println(AnsiColors.toRed("Description can not be empty!"));
            body = sc.nextLine();
        }

        Post post = new Post(title, body, username);

        Post.posts.add(post);
        System.out.println(AnsiColors.toGreen("Post added successfully!"));

    }

    public void deletePost(int postID) {
        for (Post iter : Post.posts) {
            if (iter.getPostID() == postID) {
                Post.posts.remove(iter);
            }
        }
    }

    public void showFeed() {
        System.out.println(AnsiColors.toGreen("=== Showing a total of " + Post.postsCounter + " posts ==="));
        System.out.println(LINE_SEPARATOR);
        for (Post iter : Post.posts) {

        }
    }

    public Post expandPost() {
        System.out.println(AnsiColors.toGreen("Please enter PID: "));
        int postID = Integer.parseInt(sc.nextLine());

        boolean found = false;

        for (Post iter : Post.posts) {
            if  (iter.getPostID() == postID) {
                found = true;
                System.out.println(LINE_SEPARATOR);
                System.out.println(AnsiColors.toGreen("PID: " + iter.getPostID() + " | USER: " + iter.getOwnershipName() + "\n"));
                System.out.println(iter.title);
                System.out.println(iter.body + "\n");
                System.out.print(AnsiColors.toRed("UP ") + iter.voteCount + AnsiColors.toBlue(" DOWN "));
                System.out.println( "| " + iter.getCommentsCounter() + " comments");
                System.out.println(LINE_SEPARATOR);
                System.out.println("//insert comments here");

                return iter;
            }
        }

        throw new IllegalArgumentException(AnsiColors.toRed("Post with ID " + postID + " not found."));
    }
}
