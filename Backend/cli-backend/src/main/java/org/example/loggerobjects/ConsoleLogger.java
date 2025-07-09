package org.example.loggerobjects;

public class ConsoleLogger extends Logger {
    public ConsoleLogger(String name, String dateTimeFormat) {
        super(name, dateTimeFormat);
    }

    @Override
    void write(String message) {
        System.out.println(message);
    }
}
