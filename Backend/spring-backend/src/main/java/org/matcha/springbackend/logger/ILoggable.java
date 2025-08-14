package org.matcha.springbackend.logger;

public interface ILoggable {
    void log(LogLevel level, String message);
}
