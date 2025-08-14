package org.example.apiclients;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ApiPostClient implements IApiClient {
    private final static String BASE_URL = "http://13.48.209.206:8080";
    private static HttpClient client;
    private static ApiPostClient instance;

    private ApiPostClient(HttpClient client) {
        this.client = client;
    }

    public static ApiPostClient getInstance(HttpClient client) {
        if (instance == null) {
            instance = new ApiPostClient(client);
        }
        return instance;
    }

    public JsonArray handleGet() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/posts"))
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
                .uri(URI.create(BASE_URL + "/posts"))
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
                .uri(URI.create(BASE_URL + "/posts"))
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
                .uri(URI.create(BASE_URL + "/posts" + id))
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("Couldn't connect to server");
        }
    }

    public void handleVote(String id, String json) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/posts/" + id + "/vote"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("Couldn't connect to server");
        }
    }
}
