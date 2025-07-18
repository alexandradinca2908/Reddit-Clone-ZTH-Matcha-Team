package org.example.loggerobjects;

import org.example.textprocessors.AnsiColors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LogManager {
    public static LogManager logManager;
    private ArrayList<Loggable> loggers;
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final LoggerType DEFAULT_LOGGER_TYPE = LoggerType.FILE;
    private static final String DEFAULT_FILEPATH = "logging.log";

    private LogManager() {
        loggers = new ArrayList<>();
    }

    public static LogManager getInstance() {
        if (logManager == null)
            logManager = new LogManager();

        return logManager;
    }

    public void initLoggers() {
        //  Create logging directory (if it doesn't exist already)
        try {
            Files.createDirectories(Paths.get("logging"));
        } catch (IOException e) {
            System.out.println(AnsiColors.toRed("Failed to create directory!"));
            return;
        }

        //  Create current logger directory
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH-mm-ss");
        String timestamp = ".\\logging\\" + dtf.format(LocalDateTime.now());

        Path logDir = Paths.get(timestamp);

        try {
            Files.createDirectories(logDir);
        } catch (IOException e) {
            System.out.println(AnsiColors.toRed("Failed to create directory!"));
            return;
        }

        //  Instantiate loggers
        Loggable verboseLogger = new FileLogger(LogLevel.VERBOSE, timestamp + "\\verbose.log");
        Loggable debugLogger = new FileLogger(LogLevel.DEBUG, timestamp + "\\debug.log");
        Loggable infoLogger = new FileLogger(LogLevel.INFO, timestamp + "\\info.log");
        Loggable warningLogger = new FileLogger(LogLevel.WARN,timestamp + "\\warnings.log");
        Loggable errorLogger = new FileLogger(LogLevel.ERROR, timestamp + "\\errors.log");
        Loggable fatalLogger = new FileLogger(LogLevel.FATAL, timestamp + "\\fatal.log");
        LogManager.getInstance().registerMultipleLoggers(verboseLogger, debugLogger, errorLogger,
                infoLogger, warningLogger, fatalLogger);
    }

    public void registerLogger(Loggable logger) {
        loggers.add(logger);
    }

    public void registerMultipleLoggers(Loggable... loggers) {
        for (Loggable logger : loggers) {
            registerLogger(logger);
        }
    }

    public void log(LogLevel level, String message) {
        for (Loggable logger : loggers) {
            logger.log(level, message);
        }
    }
}
