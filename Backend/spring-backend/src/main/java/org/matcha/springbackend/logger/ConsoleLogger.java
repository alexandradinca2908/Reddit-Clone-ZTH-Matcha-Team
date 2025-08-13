package org.matcha.springbackend.logger;

public class ConsoleLogger implements ILoggable {
    LogLevel level;

    public ConsoleLogger(LogLevel logLevel) {
        this.level = logLevel;
    }

    public void log(LogLevel level, String message) {
        //  Logger can only log the levels that come after it
        for (LogLevel logLevel : LogLevel.values()) {
            //  Current logger comes first or is on the same level
            if (logLevel == this.level) {
                break;
            }

            //  Current logger comes after -> can't log
            if (logLevel == level) {
                return;
            }
        }

        System.out.println(message);
    }
}
