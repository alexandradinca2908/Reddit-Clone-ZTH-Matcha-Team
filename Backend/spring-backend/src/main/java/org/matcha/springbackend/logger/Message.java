package org.matcha.springbackend.logger;

public record Message(
        String message,
        LogLevel level
) {
}
