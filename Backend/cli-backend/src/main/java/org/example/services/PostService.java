package org.example.services;
import org.example.entities.User;
import org.example.loggerobjects.Logger;
import org.example.repositories.PostRepo;
import org.example.textprocessors.AnsiColors;
import org.example.entities.Post;

import java.io.IOException;
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

        Logger.fatal("Great Success!");
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

    public void votePost(User user, Post post, boolean vote) {
        if(vote) {
            if(post.votingUserID.containsKey(user.getUserID())) {
                if(post.votingUserID.get(user.getUserID()).equals(1)) {
                    post.downvote();
                    post.votingUserID.remove(user.getUserID());
                }
                else {
                    post.upvote();
                    post.upvote();
                    post.votingUserID.put(user.getUserID(), 1);
                }
            }
            else {
                post.upvote();
                post.votingUserID.put(user.getUserID(), 1);
            }
        }
        else {
            if(post.votingUserID.containsKey(user.getUserID())) {
                if(post.votingUserID.get(user.getUserID()).equals(-1)) {
                    post.upvote();
                    post.votingUserID.remove(user.getUserID());
                }
                else {
                    post.downvote();
                    post.downvote();
                    post.votingUserID.put(user.getUserID(), -1);
                }
            }
            else {
                post.downvote();
                post.votingUserID.put(user.getUserID(), -1);
            }
        }
    }
}
