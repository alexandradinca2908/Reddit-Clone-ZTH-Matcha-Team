package org.example.loggerobjects;

import java.io.IOException;
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

    public void addLogger(String name, String dateTimeFormat, String filename) throws IOException {
        Logger logger = new FileLogger(name, dateTimeFormat, filename);
        loggers.add(logger);
    }
}
