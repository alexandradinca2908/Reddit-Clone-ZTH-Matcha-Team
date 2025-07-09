package org.example.loggerobjects;

import org.example.textprocessors.AnsiColors;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

abstract class Logger {
    String name;
    DateTimeFormatter dateTimeFormatter;

    String formatDate() {
        return LocalDate.now().format(dateTimeFormatter);
    }

    void log(LogLevel level, String message) {
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

    void verbose(String message) {
        String composedText = formatDate() + " [VERBOSE] " + message;
        write(composedText);
    }

    void debug(String message) {
        String composedText = formatDate() + " [DEBUG] " + message;
        write(composedText);
    }

    void info(String message) {
        String composedText = formatDate() + " [INFO] "  + message;
        write(composedText);
    }

    void warn(String message) {
        String composedText = formatDate() + " [WARN] "  + message;
        write(composedText);
    }

    void error(String message) {
        String composedText = formatDate() + " [ERROR] "  + message;
        write(composedText);
    }

    void fatal(String message) {
        String composedText = formatDate() + " [FATAL] "  + message;
        write(composedText);
    }

    abstract void write(String message);
}
