package org.example.services;
import org.example.AnsiColors;
import org.example.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostService extends AnsiColors {
    public static final int MAX_TEXT_LENGTH = 20;
    public static final String LINE_SEPARATOR = "----------------------";
    Scanner sc = new Scanner(System.in);
    private static ArrayList<Post> posts = new ArrayList<>(
            List.of(
                    new Post("First Post", "This is the body of the first post.", "TestUser1"),
                    new Post("Second Post", "This is the body of the second post.", "TestUser2"),
                    new Post("Third Post", "This is the body of the third post.", "TestUser1")
            )
    );

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

        posts.add(post);
        System.out.println(AnsiColors.toGreen("Post added successfully!"));

    }

    public void deletePost(int postID) {
        for (Post iter : posts) {
            if (iter.getPostID() == postID) {
                posts.remove(iter);
            }
        }
    }

    public void showFeed() {
        System.out.println(AnsiColors.toGreen("=== Welcome to Reddit v1.0 ==="));
        System.out.println(LINE_SEPARATOR);
        for (Post iter : posts) {
            System.out.println(AnsiColors.toGreen("PID: " + iter.getPostID() + " | UID: " + iter.getOwnershipID() + "\n"));
            System.out.println(iter.title);
            String preview;
            if (iter.body.length() > MAX_TEXT_LENGTH) {
                preview = iter.body.substring(0, MAX_TEXT_LENGTH) + "...";
            } else {
                preview = iter.body;
            }
            System.out.println(preview + "\n");
            System.out.print(AnsiColors.toRed("UP ") + iter.voteCount + AnsiColors.toBlue(" DOWN "));
            System.out.println( "| " + iter.getCommentsCounter() + " comments");
            System.out.println(LINE_SEPARATOR);
        }
    }

    public void expandPost() {
        System.out.println(AnsiColors.toGreen("Please enter PID: "));
        int postID = Integer.parseInt(sc.nextLine());

        boolean found = false;

        for (Post iter : posts) {
            if  (iter.getPostID() == postID) {
                found = true;
                System.out.println(LINE_SEPARATOR);
                System.out.println(AnsiColors.toGreen("PID: " + iter.getPostID() + " | UID: " + iter.getOwnershipID() + "\n"));
                System.out.println(iter.title);
                System.out.println(iter.body + "\n");
                System.out.print(AnsiColors.toRed("UP ") + iter.voteCount + AnsiColors.toBlue(" DOWN "));
                System.out.println( "| " + iter.getCommentsCounter() + " comments");
                System.out.println(LINE_SEPARATOR);
                System.out.println("//insert comments here");
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException(AnsiColors.toRed("Post with ID " + postID + " not found."));
        }
    }
}
