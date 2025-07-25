package org.example.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.dto.PostDTO;
import org.example.models.Post;
import org.example.mapper.PostMapper;
import org.example.userinterface.UIPost;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class PostApiClient extends BaseApiClient {
    private static PostApiClient instance;
    public static ArrayList<Post> posts = new ArrayList<>();

    private PostApiClient(String baseUrl) {
        super(baseUrl);
    }

    public static PostApiClient getInstance(String baseUrl) {
        if (instance == null) {
            instance = new PostApiClient(baseUrl);
        }
        return instance;
    }

    // GET /posts
    public void getPosts() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/posts"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                posts.clear();

                JsonArray postsArray = JsonParser.parseString(response.body()).getAsJsonArray();
                for (JsonElement postElement : postsArray) {
                    PostDTO postDto = getPostDTO(postElement);
                    posts.add(PostMapper.toModel(postDto));
                }
            } else {
                System.err.println("Failed to fetch posts. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Get posts request failed: " + e.getMessage());
        }
    }

    private static PostDTO getPostDTO(JsonElement postElement) {
        JsonObject postObject = postElement.getAsJsonObject();
        return new PostDTO(
                postObject.get("id").getAsString(),
                postObject.get("title").getAsString(),
                postObject.get("content").getAsString(),
                postObject.get("author").getAsString(),
                postObject.get("subreddit").getAsString(),
                postObject.get("upvotes").getAsInt(),
                postObject.get("downvotes").getAsInt(),
                postObject.get("score").getAsInt(),
                postObject.get("commentCount").getAsInt(),
                postObject.get("userVote").getAsString(),
                postObject.get("createdAt").getAsString(),
                postObject.get("updatedAt").getAsString()
        );
    }

    private int getPostIDUser() {
        int postID;
        Scanner sc = new Scanner(System.in);
        UIPost uiPost = UIPost.getInstance();

        while (true) {
            uiPost.pleaseEnterPostId();

            try {
                postID = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                uiPost.invalidInput();
            }
        }
        return postID;
    }

    //GET /post/:id
    public Post getPost() {
        int postId = getPostIDUser();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/posts/" + postId))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonElement postElement = JsonParser.parseString(response.body());
                PostDTO postDto = getPostDTO(postElement);
                return PostMapper.toModel(postDto);
            } else {
                System.err.println("Failed to fetch post with ID " + postId + ". Status code: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Get post request failed for ID " + postId + ": " + e.getMessage());
            return null;
        }
    }

    public void addPost(String username) {
        //TODO
    }
}
