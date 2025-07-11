package org.example.services;
import org.example.repositories.PostRepo;
import org.example.textprocessors.AnsiColors;
import org.example.entities.Post;

import java.util.Scanner;

public class PostService {
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
        while (title.length() < AnsiColors.MAX_TEXT_LENGTH) {
            System.out.println(AnsiColors.toRed("Title must be at least " +  AnsiColors.MAX_TEXT_LENGTH + " characters long."));
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

        postRepo.save(post);
    }

    // move in ViewPort
    public void deletePost(int postID) {
        Post.posts.removeIf(iter -> iter.getPostID() == postID);
    }

    public void showFeed() {
        String headerText = String.format(AnsiColors.POST_COUNT_HEADER_FORMAT, Post.posts.size());
        System.out.println(AnsiColors.toGreen(headerText));
        System.out.println(AnsiColors.LINE_SEPARATOR);
        for (Post iter : Post.posts) {
            this.showPost(false, iter);
        }
    }

    public int getPostIDUser() {
        int postID;
        System.out.println(AnsiColors.toGreen("Please enter PostID: "));

        while (true) {
            System.out.print("Enter a PostID: ");
            try {
                postID = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(AnsiColors.toRed("Invalid input. Please enter a valid number."));
            }
        }
        return postID;
    }

    public Post getPost(int postID) {
        return postRepo.findById(postID);
    }

    // move in ViewPost
    public void showPost(boolean isExpanded, Post post) {
        if  (post == null) {
            System.out.println(AnsiColors.toRed("Post is null!"));
            return;
        }
        System.out.println(AnsiColors.toGreen("ID: " + post.getPostID() + " | USER: " + post.getUsername() + "\n"));
        System.out.println(AnsiColors.highlight(AnsiColors.addReward(post.getTitle(), post.getVotes())));
        if (!isExpanded) {
            if (post.getBody().length() > AnsiColors.MAX_TEXT_LENGTH) {
                System.out.println(post.getBody().substring(0, AnsiColors.MAX_TEXT_LENGTH) + "...\n");
            }
        } else {
            System.out.println(post.getBody());
            System.out.println();
        }
        System.out.print(AnsiColors.toRed("UP ") + post.getVotes() + AnsiColors.toBlue(" DOWN "));
        System.out.println( "| " + post.getCommentsCounter() + " comments");
        if (isExpanded) {
            System.out.println(AnsiColors.DOUBLE_LINE_SEPARATOR + "\n");
            post.printPostComments(0);
        } else {
            System.out.println(AnsiColors.LINE_SEPARATOR);
        }
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
