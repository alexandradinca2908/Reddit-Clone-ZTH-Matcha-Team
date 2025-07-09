package org.example.loggerobjects;

import java.io.FileWriter;
import java.io.IOException;

public class FileLogger extends Logger {
    FileWriter fileWriter;

    public FileLogger(String name, String dateTimeFormat, String filename) throws IOException {
        super(name, dateTimeFormat);
        fileWriter = new FileWriter(filename);
    }

    @Override
    void write(String message) throws IOException {
        fileWriter.write(message + "\n");
        fileWriter.flush();
    }
}
