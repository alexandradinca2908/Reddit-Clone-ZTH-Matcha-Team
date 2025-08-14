package org.matcha.springbackend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class MenuController {
    private final RestTemplate restTemplate;

    public MenuController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping
    public String hello() {
        return "🍵Hello from the Matcha team!";
    }

    @GetMapping("filters")
    public ResponseEntity<String> getFilters() {
        String kestrelServer = "http://51.21.228.104/filters";

        try {
            // Make request to Kestrel server
            ResponseEntity<String> response = restTemplate.getForEntity(kestrelServer, String.class);

            // Create response headers (optional - copy content type from original response)
            HttpHeaders headers = new HttpHeaders();
            if (response.getHeaders().getContentType() != null) {
                headers.setContentType(response.getHeaders().getContentType());
            } else {
                headers.setContentType(MediaType.APPLICATION_JSON);
            }

            // Return the response with the same status code and body
            return new ResponseEntity<>(response.getBody(), headers, response.getStatusCode());

        } catch (RestClientException e) {
            // Handle connection errors, timeouts, etc.
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Error connecting to filter service: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }
}
