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

    public void log(LogLevel level, String message) {
        for (Loggable logger : loggers) {
            logger.log(level, message);
        }
    }
}
