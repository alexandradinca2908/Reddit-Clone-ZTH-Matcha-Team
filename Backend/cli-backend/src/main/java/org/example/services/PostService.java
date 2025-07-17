package org.example.services;
import org.example.dbconnection.DatabaseConnection;
import org.example.models.User;
import org.example.loggerobjects.Logger;
import org.example.repositories.PostRepo;
import org.example.textprocessors.AnsiColors;
import org.example.models.Post;
import org.example.userinterface.UIPost;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class PostService {
    private static PostService instance;
    public static final PostRepo postRepo = PostRepo.getInstance();
    public static ArrayList<Post> posts = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    private PostService() {}

    public static PostService getInstance() {
        if (instance == null) {
            instance = new PostService();

            try {
                postRepo.load(posts);
            } catch (SQLException e) {
                Logger.error("Failed to load posts from the database: " + e.getMessage());
                DatabaseConnection.cannotConnect();
            }
        }
        return instance;
    }

    public void createPost(String title, String body, String username) {
        Post post = new Post(title, body, username);
        posts.add(post);

        // it handles the case where the post is not saved to the database
        postRepo.save(post);

        Logger.info("Post created successfully by user: " + username);
    }

    public void addPost(String username) {
        Map<String, String> postData = UIPost.getPostDetailsFromUser();

        String title = postData.get("title");
        String body = postData.get("body");

        createPost(title, body, username);
        System.out.println(AnsiColors.toGreen("Post added successfully!"));
    }

    public int getPostIDUser() {
        int postID;

        while (true) {
            System.out.print(AnsiColors.toGreen("Please enter a PostID: "));
            try {
                postID = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(AnsiColors.toRed("Invalid input. Please enter a valid number."));
            }
        }
        return postID;
    }

    public static Post getPost(int postID) {
        Post post = findById(postID);

        if (post == null) {
            throw new IllegalArgumentException(AnsiColors.toRed(String.format("Post with ID %d not found.", postID)));
        }

        return post;
    }

    public static Post findById(int postId) {
        for (Post post : PostService.posts) {
            if (post.getPostID() == postId) {
                return post;
            }
        }
        return null;
    }

}
