package org.example.loggerobjects;

import java.util.ArrayList;

public class LogManager {
    public static LogManager logManager;
    private ArrayList<Logger> loggers;

    private LogManager() {
        loggers = new ArrayList<>();
    }

    public static LogManager getInstance() {
        if (logManager == null)
            logManager = new LogManager();

        return logManager;
    }

    public void addLogger(Logger logger) {
        loggers.add(logger);
    }
}
