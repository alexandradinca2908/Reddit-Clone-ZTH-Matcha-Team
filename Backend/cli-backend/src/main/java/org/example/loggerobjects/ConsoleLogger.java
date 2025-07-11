package org.example.loggerobjects;

public class ConsoleLogger implements Loggable {
    LogLevel level;

    public ConsoleLogger(LogLevel logLevel) {
        this.level = logLevel;
    }

    public void log(LogLevel level, String message) {
        //  Only log the logger's assigned level
        if (this.level != level) {
            return;
        }

        System.out.println(message);
    }
}
