package org.matcha.springbackend.loggerobjects;

import java.time.LocalDateTime;

public class Logger {

    static String formatDate() {
        return LocalDateTime.now().toString();
    }

    private static void log(LogLevel level, String message) {
        String composedText = formatDate() + " [" + level.toString() + "] " + message;
        LogManager.getInstance().log(level, composedText);
    }

    public static void verbose(String message)  {
        log(LogLevel.VERBOSE, message);
    }

    public static void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public static void info(String message)  {
        log(LogLevel.INFO, message);
    }

    public static void warn(String message)  {
        log(LogLevel.WARN, message);
    }

    public static void error(String message)  {
        log(LogLevel.ERROR, message);
    }

    public static void fatal(String message)  {
        log(LogLevel.FATAL, message);
    }
}
