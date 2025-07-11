package org.example.loggerobjects;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
   // private String name;
   // private DateTimeFormatter dateTimeFormatter;
/*
    public Logger(String name, String dateTimeFormat) {
        this.name = name;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    }

    public String getName() {
        return name;
    }

*/
    static String formatDate() {
        return LocalDateTime.now().toString();
    }

    public static void log(LogLevel level, String message) throws IOException {
        // LogManager.getInstance().log(level,message);
        switch (level) {
            case VERBOSE:
                verbose(message);
                break;

            case DEBUG:
                debug(message);
                break;

            case INFO:
                info(message);
                break;

            case WARN:
                warn(message);
                break;

            case ERROR:
                error(message);
                break;

            case FATAL:
                fatal(message);
                break;
        }
    }

    public static void verbose(String message)  {
        String composedText = formatDate() + " [VERBOSE] " + message;
        write(composedText);
    }

    public static void debug(String message) {
        String composedText = formatDate() + " [DEBUG] " + message;
        write(composedText);
    }

    public static void info(String message)  {
        String composedText = formatDate() + " [INFO] "  + message;
        write(composedText);
    }

    public static void warn(String message)  {
        String composedText = formatDate() + " [WARN] "  + message;
        write(composedText);
    }

    public static void error(String message)  {
        String composedText = formatDate() + " [ERROR] "  + message;
        write(composedText);
    }

    public static void fatal(String message)  {
        String composedText = formatDate() + " [FATAL] "  + message;
        write(composedText);
    }

    public static void write(String message) {}
}
