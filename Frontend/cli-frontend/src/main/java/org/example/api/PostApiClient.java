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
import java.util.Map;
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

                JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();

                JsonArray postsArray = responseObject.getAsJsonArray("data");

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

        while (true) {
            UIPost.pleaseEnterPostId();

            try {
                postID = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                UIPost.invalidInput();
            }
        }
        return postID;
    }

    //GET /post/:id
    public Post getPost() {
        int postId = getPostIDUser();
        String postUUID = "";
        for (Post post : posts) {
            if (post.getPostID() == postId) {
                postUUID = post.getUUID();
            }
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/posts/" + postUUID))
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

    // POST /posts
    public void addPost(String authorName, String subreddit) {
        Map<String, String> postContent = UIPost.getPostDetailsFromUser();
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("title", postContent.get("title"));
        requestBody.addProperty("content", postContent.get("content"));
        requestBody.addProperty("author", authorName);
        requestBody.addProperty("subreddit", subreddit);

        String jsonPayload = requestBody.toString();

        String fullUrl = baseUrl + "/posts";
        System.out.println("DEBUG: Sending POST request to URL -> " + fullUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/posts"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200){
                System.err.println("Failed to create post. Status code: " + response.statusCode());
                System.err.println("Response body: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Create post request failed: " + e.getMessage());
        }
    }
}
