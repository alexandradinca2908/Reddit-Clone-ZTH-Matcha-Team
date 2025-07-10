package org.example.loggerobjects;

import java.io.IOException;
import java.util.ArrayList;

/**
 * LogManager handles all loggers; comes by default with MainLogger
 *
 * How to use main logger:
 *      Logger mainLogger = LogManager.getInstance().getLogger("MainLogger");
 *      mainLogger.log(LogLevel, message");
 *
 * You can add any other type of logger in order to change the date format, output type etc.
 */

public class LogManager {
    public static LogManager logManager;
    private ArrayList<Logger> loggers;
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final LoggerType DEFAULT_LOGGER_TYPE = LoggerType.FILE;
    private static final String DEFAULT_FILEPATH = "logging.log";

    private LogManager() throws IOException {
        loggers = new ArrayList<>();
        addLogger("MainLogger", DEFAULT_DATE_FORMAT, DEFAULT_FILEPATH, DEFAULT_LOGGER_TYPE);
    }

    public static LogManager getInstance() throws IOException {
        if (logManager == null)
            logManager = new LogManager();

        return logManager;
    }

    public void addLogger(String name, String dateTimeFormat, String filename, LoggerType type) throws IOException {
        //  Don't add duplicate loggers
        if (findLogger(name)) {
            return;
        }

        Logger logger = switch (type) {
            case FILE -> new FileLogger(name, dateTimeFormat, filename);
            case CONSOLE -> new ConsoleLogger(name, dateTimeFormat);
        };

        loggers.add(logger);
    }

    public Logger getLogger(String name) throws IOException {
        for (Logger logger : loggers) {
            if (logger.getName().equals(name)) {
                return logger;
            }
        }

        //  Logger doesn't exist; create new one
        addLogger(name, DEFAULT_DATE_FORMAT, "logging.log", DEFAULT_LOGGER_TYPE);
        return loggers.getLast();
    }

    public boolean findLogger(String name) throws IOException {
        for (Logger logger : loggers) {
            if (logger.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
