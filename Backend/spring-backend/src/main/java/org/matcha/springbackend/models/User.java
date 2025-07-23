package org.matcha.springbackend.models;

public record User (
        String userName,
        String email,
        String password
) {
}
