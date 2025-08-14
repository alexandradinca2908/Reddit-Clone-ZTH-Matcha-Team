package org.example.apiclients;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiSubredditClient {
    private static final String BASE_URL = "http://13.48.209.206:8080";
    private static HttpClient client;
    private static ApiSubredditClient instance;

    private ApiSubredditClient(HttpClient httpClient) {
        client = httpClient;
    }

    public static ApiSubredditClient getInstance(HttpClient httpClient) {
        if (instance == null) {
            instance = new ApiSubredditClient(httpClient);
        }
        return instance;
    }

    public JsonArray handleGet() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/subreddits"))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
            return responseObject.getAsJsonArray("data");
        } catch (IOException | InterruptedException e) {
            System.err.println("Couldn't connect to server");
        }
        return null;
    }

    public JsonObject handlePost(String json) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/subreddits"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
            return responseObject.getAsJsonObject("data");
        } catch (IOException | InterruptedException e) {
            System.err.println("Couldn't connect to server");
        }
        return null;
    }

    public JsonObject handlePut(String json) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/subreddits"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
            return responseObject.getAsJsonObject("data");
        } catch (IOException | InterruptedException e) {
            System.err.println("Couldn't connect to server");
        }
        return null;
    }

    public void handleDelete(String id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/subreddits" + id))
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("Couldn't connect to server");
        }
    }
}
