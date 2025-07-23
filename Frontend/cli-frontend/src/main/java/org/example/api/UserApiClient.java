package org.example.api;

import org.example.DTO.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class UserApiClient extends BaseApiClient {
    public UserApiClient(String baseUrl) {
        super(baseUrl);
    }

    public User login(String username, String password) {
        Map<String, String> credentials = Map.of("username", username, "password", password);
        String requestBody = gson.toJson(credentials);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), User.class);
            } else {
                System.err.println("Login failed with status code: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Login request failed: " + e.getMessage());
            return null;
        }
    }
}
