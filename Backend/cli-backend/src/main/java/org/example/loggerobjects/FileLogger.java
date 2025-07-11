package org.example.loggerobjects;

import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Loggable {
    FileWriter fileWriter;
    LogLevel level;

    public FileLogger(LogLevel logLevel, String filename) {
        try {
            this.fileWriter = new FileWriter(filename);
        } catch (IOException e) {
            System.out.println("Error opening file: " + filename);
        }

        this.level = logLevel;
    }

    public void log(LogLevel level, String message) {
        //  Only log the logger's assigned level
        if (this.level != level) {
            return;
        }

        try {
            fileWriter.write(message + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
