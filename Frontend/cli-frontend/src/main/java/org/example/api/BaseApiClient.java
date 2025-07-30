package org.example.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public abstract class BaseApiClient {
    protected final String baseUrl;
    protected final HttpClient httpClient;
    protected final Gson gson;

    public BaseApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
    }

    public boolean isBackendReachable() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/healthcheck"))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() >= 200 && response.statusCode() < 300;
        } catch (IOException | InterruptedException e) {
            System.err.println("Connection to the database failed: " + e.getMessage());
            return false;
        }
    }
}

