package org.matcha.springbackend.response;

public class MessageResponse {
    private boolean success;
    private String message;

    public MessageResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
