package org.example.loggerobjects;

import java.io.FileWriter;
import java.io.IOException;

public class FileLogger extends Logger {
    FileWriter fileWriter;

    public FileLogger(String filename) throws IOException {
        fileWriter = new FileWriter(filename);
    }
    
    @Override
    void write(String message) {

    }
}
