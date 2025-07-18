package org.example.loggerobjects;

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
