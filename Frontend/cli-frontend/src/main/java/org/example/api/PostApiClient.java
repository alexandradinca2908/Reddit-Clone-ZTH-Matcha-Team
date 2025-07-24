package org.example.api;

import com.google.gson.reflect.TypeToken;
import org.example.DTO.Post;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class PostApiClient extends BaseApiClient {
    public static ArrayList<Post> posts = new ArrayList<>();

    public PostApiClient(String baseUrl) {
        super(baseUrl);
    }

    public void getPosts() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/posts"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                java.lang.reflect.Type postListType = new TypeToken<List<Post>>() {}.getType();
                List<Post> fetchedPosts = gson.fromJson(response.body(), postListType);
                if (fetchedPosts != null) {
                    posts.clear();
                    posts.addAll(fetchedPosts);
                }
            } else {
                System.err.println("Failed to fetch posts. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Get posts request failed: " + e.getMessage());
        }
    }

    public Post getPost() {
        // TODO
        return null;
    }

    public void addPost(String username) {
        //TODO
    }
}
