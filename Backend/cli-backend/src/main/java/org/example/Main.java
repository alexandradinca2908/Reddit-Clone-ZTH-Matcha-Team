package org.example;

import org.example.entities.ActionState;
import org.example.loggerobjects.FileLogger;
import org.example.loggerobjects.LogLevel;
import org.example.loggerobjects.LogManager;
import org.example.loggerobjects.Loggable;
import org.example.repositories.CommentRepo;
import org.example.textprocessors.AnsiColors;

import java.io.IOException;

public class Main {
    private static final CommentRepo commentRepo = CommentRepo.getInstance();

    public static void main(String[] args) throws IOException {
        //  Get menu instance
        ActionState actionState = ActionState.getInstance();

        //  Instantiate loggers
        Loggable verboseLogger = new FileLogger(LogLevel.VERBOSE, "verbose.txt");
        Loggable debugLogger = new FileLogger(LogLevel.DEBUG, "debug.txt");
        Loggable errorLogger = new FileLogger(LogLevel.ERROR, "errors.txt");
        Loggable infoLogger = new FileLogger(LogLevel.INFO, "info.txt");
        Loggable warningLogger = new FileLogger(LogLevel.WARN,"warnings.txt");
        Loggable fatalLogger = new FileLogger(LogLevel.FATAL, "fatal.txt");

        LogManager.getInstance().registerMultipleLoggers(verboseLogger, debugLogger, errorLogger,
                infoLogger, warningLogger, fatalLogger);
        boolean isActive = true;

        //  Load comment repo
        commentRepo.load();

        //  Start app
        System.out.println(AnsiColors.toPurple("Welcome to Reddit!\nPlease choose an option:\n"));

        while (isActive) {
            isActive = actionState.executeAction();
        }
    }
}