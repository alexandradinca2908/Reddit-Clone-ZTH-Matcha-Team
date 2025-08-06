package org.matcha.springbackend.loggerobject;

import org.matcha.springbackend.textprocessor.AnsiColors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LogManager {
    public static LogManager logManager;
    private ArrayList<ILoggable> loggers;
    private final BlockingQueue<LogMessage> logQueue = new LinkedBlockingQueue<>();
    private final Thread logThread;
    private volatile boolean running = true;

    private static class LogMessage {
        final LogLevel level;
        final String message;
        LogMessage(LogLevel level, String message) {
            this.level = level;
            this.message = message;
        }
    }

    private LogManager() {
        loggers = new ArrayList<>();
        logThread = new Thread(() -> {
            while (running || !logQueue.isEmpty()) {
                try {
                    LogMessage logMsg = logQueue.poll(500, TimeUnit.MILLISECONDS);
                    if (logMsg != null) {
                        for (ILoggable logger : loggers) {
                            logger.log(logMsg.level, logMsg.message);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "LoggerThread");
        logThread.setDaemon(true);
        logThread.start();
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
        String timestamp = "logging/" + dtf.format(LocalDateTime.now());

        Path logDir = Paths.get(timestamp);

        try {
            Files.createDirectories(logDir);
        } catch (IOException e) {
            System.out.println(AnsiColors.toRed("Failed to create directory!"));
            return;
        }

        //  Instantiate loggers
        ILoggable verboseLogger = new FileLogger(LogLevel.VERBOSE, timestamp + "/verbose.log");
        ILoggable debugLogger = new FileLogger(LogLevel.DEBUG, timestamp + "/debug.log");
        ILoggable infoLogger = new FileLogger(LogLevel.INFO, timestamp + "/info.log");
        ILoggable warningLogger = new FileLogger(LogLevel.WARN, timestamp + "/warnings.log");
        ILoggable errorLogger = new FileLogger(LogLevel.ERROR, timestamp + "/errors.log");
        ILoggable fatalLogger = new FileLogger(LogLevel.FATAL, timestamp + "/fatal.log");
        LogManager.getInstance().registerMultipleLoggers(verboseLogger, debugLogger, errorLogger,
                infoLogger, warningLogger, fatalLogger);
    }

    public void registerLogger(ILoggable logger) {
        loggers.add(logger);
    }

    public void registerMultipleLoggers(ILoggable... loggers) {
        for (ILoggable logger : loggers) {
            registerLogger(logger);
        }
    }

    public void log(LogLevel level, String message) {
        logQueue.offer(new LogMessage(level, message));
    }

    public void shutdown() {
        running = false;
        logThread.interrupt();
    }
}
