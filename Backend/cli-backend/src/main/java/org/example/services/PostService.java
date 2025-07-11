package org.example.services;
import org.example.loggerobjects.Logger;
import org.example.repositories.PostRepo;
import org.example.textprocessors.AnsiColors;
import org.example.entities.Post;

import java.io.IOException;
import java.util.Scanner;

public class PostService extends AnsiColors {
    private static PostService instance;
    public static final PostRepo postRepo = PostRepo.getInstance();
    Scanner sc = new Scanner(System.in);

    private PostService() {}

    public static PostService getInstance() {
        if (instance == null) {
            instance = new PostService();

            postRepo.load(Post.posts);
        }
        return instance;
    }

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

        Logger.fatal("Great Success!");
        Post.posts.add(post);
        System.out.println(AnsiColors.toGreen("Post added successfully!"));

        postRepo.save(post);
    }

    public void deletePost(int postID) {
        Post.posts.removeIf(iter -> iter.getPostID() == postID);
    }

    public void showFeed() {
        String headerText = String.format(AnsiColors.POST_COUNT_HEADER_FORMAT, Post.posts.size());
        System.out.println(AnsiColors.toGreen(headerText));
        System.out.println(LINE_SEPARATOR);
        for (Post iter : Post.posts) {
            System.out.println(AnsiColors.toGreen("ID: " + iter.getPostID() + " | USER: " + iter.getUsername() + "\n"));
            System.out.println(AnsiColors.addReward(iter.title, iter.voteCount));
            System.out.println(SMALL_LINE_SEPARATOR);
            String preview;
            if (iter.body.length() > MAX_TEXT_LENGTH) {
                preview = iter.body.substring(0, MAX_TEXT_LENGTH) + "...";
            } else {
                preview = iter.body;
            }
            System.out.println(preview + "\n");
            System.out.print(AnsiColors.toRed("UP ") + iter.voteCount + AnsiColors.toBlue(" DOWN "));
            System.out.println( "| " + iter.commentList.size()+ " comments");
            System.out.println(LINE_SEPARATOR);
        }
    }

    public Post expandPost() throws IOException {
        System.out.println(AnsiColors.toGreen("Please enter PostID: "));

        int postID;
        while (true) {
            System.out.print("Enter a PostID: ");
            try {
                postID = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(AnsiColors.toRed("Invalid input. Please enter a valid number."));
            }
        }

        for (Post iter : Post.posts) {
            if  (iter.getPostID() == postID) {
                System.out.println(LINE_SEPARATOR);
                System.out.println(AnsiColors.toGreen("ID: " + iter.getPostID() + " | USER: " + iter.getUsername() + "\n"));
                System.out.println(AnsiColors.addReward(iter.title, iter.voteCount));
                System.out.println(SMALL_LINE_SEPARATOR);
                System.out.println(iter.body + "\n");
                System.out.print(AnsiColors.toRed("UP ") + iter.voteCount + AnsiColors.toBlue(" DOWN "));
                System.out.println( "| " + iter.getCommentsCounter() + " comments");
                System.out.println(DOUBLE_LINE_SEPARATOR + "\n");
                iter.printComments(0);

                return iter;
            }
        }

        throw new IllegalArgumentException(AnsiColors.toRed("Post with ID " + postID + " not found."));
    }

    public void expandPost(Post post) {
        System.out.println(LINE_SEPARATOR);
        System.out.println(AnsiColors.toGreen("ID: " + post.getPostID() + " | USER: " + post.getUsername() + "\n"));
        System.out.println(AnsiColors.addReward(post.title, post.voteCount));
        System.out.println(post.body + "\n");
        System.out.print(AnsiColors.toRed("UP ") + post.voteCount + AnsiColors.toBlue(" DOWN "));
        System.out.println( "| " + post.getCommentsCounter() + " comments");
        System.out.println(DOUBLE_LINE_SEPARATOR + "\n");
        post.printComments(0);
    }

    public void votePost(int userID, int postID, String vote) {
        for (Post iter : Post.posts) {
            if (iter.getPostID() == postID) {
                if (vote.equalsIgnoreCase("upvote")) {
                    if (iter.votingUserID.containsKey(userID)) { // am votat deja dar nu stiu ce am votat
                        if (iter.votingUserID.get(userID).equals(1)) { //am votat deja upvote
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
                else if (vote.equalsIgnoreCase("downvote")) {
                    if (iter.votingUserID.containsKey(userID)) {
                        if (iter.votingUserID.get(userID).equals(-1)) {
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
