package org.example.loggerobjects;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Logger class used for logging app info
 * How to use:
 *      Logger.info("message");
 *      Logger.fatal("message");
 *      etc.
 */
public class FileLogger implements ILoggable {
    FileWriter fileWriter;
    LogLevel level;

    public FileLogger(LogLevel logLevel, String filename) {
        this.level = logLevel;

        try {
            this.fileWriter = new FileWriter(filename);
        } catch (IOException e) {
            System.out.println("Error opening file: " + filename);
        }
    }

    public void log(LogLevel level, String message) {
        //  Logger can only log the levels that come after it
        for (LogLevel logLevel : LogLevel.values()) {
            //  Current logger comes first or is on the same level
            if (logLevel == this.level) {
                break;
            }

            //  Current logger comes after -> can't log
            if (logLevel == level) {
                return;
            }
        }

        try {
            fileWriter.write(message + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
