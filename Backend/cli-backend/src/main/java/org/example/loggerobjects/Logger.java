package org.example.loggerobjects;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Logger {
    private String name;
    private DateTimeFormatter dateTimeFormatter;

    public Logger(String name, String dateTimeFormat) {
        this.name = name;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    }

    public String getName() {
        return name;
    }

    String formatDate() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    public void log(LogLevel level, String message) throws IOException {
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

    void verbose(String message) throws IOException {
        String composedText = formatDate() + " [VERBOSE] " + message;
        write(composedText);
    }

    void debug(String message) throws IOException {
        String composedText = formatDate() + " [DEBUG] " + message;
        write(composedText);
    }

    void info(String message) throws IOException {
        String composedText = formatDate() + " [INFO] "  + message;
        write(composedText);
    }

    void warn(String message) throws IOException {
        String composedText = formatDate() + " [WARN] "  + message;
        write(composedText);
    }

    void error(String message) throws IOException {
        String composedText = formatDate() + " [ERROR] "  + message;
        write(composedText);
    }

    void fatal(String message) throws IOException {
        String composedText = formatDate() + " [FATAL] "  + message;
        write(composedText);
    }

    abstract void write(String message) throws IOException;
}
