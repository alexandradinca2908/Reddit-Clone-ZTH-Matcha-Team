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
            System.out.println(AnsiColors.toGreen("PID: " + iter.getPostID() + " | USER: " + iter.getOwnershipName() + "\n"));
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
                System.out.println(DOUBLE_LINE_SEPARATOR);
                iter.printComments(0);

                return iter;
            }
        }

        throw new IllegalArgumentException(AnsiColors.toRed("Post with ID " + postID + " not found."));
    }

    public void votePost(int userID, int postID, String vote) {
        for(Post iter : Post.posts) {
            if(iter.getPostID() == postID) {
                if(vote.equalsIgnoreCase("upvote")) {
                    if(iter.votingUserID.containsKey(userID)) { // am votat deja dar nu stiu ce am votat
                        if(iter.votingUserID.get(userID).equals(1)) { //am votat deja upvote
                            iter.downvote();
                            iter.votingUserID.remove(userID);
                        }
                        else {
                            iter.upvote();
                            iter.upvote();
                            iter.votingUserID.put(userID, 1);
                        }
                    }
                    else {
                        iter.upvote();
                        iter.votingUserID.put(userID, 1);
                    }
                }
                else if(vote.equalsIgnoreCase("downvote")) {
                    if(iter.votingUserID.containsKey(userID)) {
                        if(iter.votingUserID.get(userID).equals(-1)) {
                            iter.upvote();
                            iter.votingUserID.remove(userID);
                        }
                        else {
                            iter.downvote();
                            iter.downvote();
                            iter.votingUserID.put(userID, -1);
                        }
                    }
                    else {
                        iter.downvote();
                        iter.votingUserID.put(userID, -1);
                    }
                }
                break;
            }
        }

    }
}
