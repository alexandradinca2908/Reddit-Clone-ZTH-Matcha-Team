package org.matcha.springbackend.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class LogManager extends Thread {
    public static LogManager logManager;
    private final ArrayList<ILoggable> loggers;

    //  Multithreading variables
    //  LogManager state
    private volatile boolean running = true;
    //  Lock
    protected final Semaphore availableMessages = new Semaphore(0);
    //  Message queue
    protected final Queue<Message> logMessages = new ConcurrentLinkedQueue<>();

    //  MULTITHREADING LOGIC starts here
    public void run() {
        initLoggers();

        while (running) {
            //  Wait for a message
            try {
                availableMessages.acquire();
            } catch (InterruptedException e) {
                break;
            }

            //  Check running state
            if (!running) {
                return;
            }

            //  Check for the message
            Message message = logMessages.poll();
            if (message != null) {
                logManager.log(message.level(), message.message());

                //  Drain the rest of the queue
                while (availableMessages.tryAcquire()) {
                    message = logMessages.poll();

                    if (message != null) {
                        logManager.log(message.level(), message.message());
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public void shutdown() {
        running = false;
        availableMessages.release();
    }

    protected void addMessage(Message message) {
        logMessages.add(message);
    }

    //  MULTITHREADING LOGIC ends here

    private LogManager() {
        loggers = new ArrayList<>();
    }

    public static LogManager getInstance() {
        if (logManager == null)
            logManager = new LogManager();

        return logManager;
    }

    private void initLoggers() {
        //  Create logging directory (if it doesn't exist already)
        try {
            Files.createDirectories(Paths.get("logging"));
        } catch (IOException e) {
            System.out.println("Failed to create directory!");
            return;
        }

        //  Create current logger directory
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH-mm-ss");
        String timestamp = ".\\logging\\" + dtf.format(LocalDateTime.now());

        Path logDir = Paths.get(timestamp);

        try {
            Files.createDirectories(logDir);
        } catch (IOException e) {
            System.out.println("Failed to create directory!");
            return;
        }

        //  Instantiate loggers
        ILoggable verboseLogger = new FileLogger(LogLevel.VERBOSE, timestamp + "\\verbose.log");
        ILoggable debugLogger = new FileLogger(LogLevel.DEBUG, timestamp + "\\debug.log");
        ILoggable infoLogger = new FileLogger(LogLevel.INFO, timestamp + "\\info.log");
        ILoggable warningLogger = new FileLogger(LogLevel.WARN,timestamp + "\\warnings.log");
        ILoggable errorLogger = new FileLogger(LogLevel.ERROR, timestamp + "\\errors.log");
        ILoggable fatalLogger = new FileLogger(LogLevel.FATAL, timestamp + "\\fatal.log");
        LogManager.getInstance().registerMultipleLoggers(verboseLogger, debugLogger, errorLogger,
                infoLogger, warningLogger, fatalLogger);
    }

    private void registerLogger(ILoggable logger) {
        loggers.add(logger);
    }

    private void registerMultipleLoggers(ILoggable... loggers) {
        for (ILoggable logger : loggers) {
            registerLogger(logger);
        }
    }

    private void log(LogLevel level, String message) {
        for (ILoggable logger : loggers) {
            logger.log(level, message);
        }
    }
}
