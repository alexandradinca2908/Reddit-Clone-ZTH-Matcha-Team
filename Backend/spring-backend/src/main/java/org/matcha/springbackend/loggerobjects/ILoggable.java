package org.matcha.springbackend.loggerobjects;

public interface ILoggable {
    void log(LogLevel level, String message);
}
