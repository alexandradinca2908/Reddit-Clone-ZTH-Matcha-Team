package org.example.loggerobjects;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Logger {
    String name;
    DateTimeFormatter dateTimeFormatter;

    public Logger(String name, String dateTimeFormat) {
        this.name = name;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    }

    String formatDate() {
        return LocalDate.now().format(dateTimeFormatter);
    }

    void log(LogLevel level, String message) throws IOException {
        switch (level) {
            case VERBOSE:
                verbose(message);

            case DEBUG:
                debug(message);

            case INFO:
                info(message);

            case WARN:
                warn(message);

            case ERROR:
                error(message);

            case FATAL:
                fatal(message);
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
