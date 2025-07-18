package org.example;

import org.example.dbconnection.DatabaseConnection;
import org.example.loggerobjects.*;
import org.example.textprocessors.AnsiColors;
import org.example.menu.MenuOption;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.views.ViewManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.textprocessors.InputTranslator.translateInput;

public class Main {
    public static void main(String[] args) {

        // ===========================================
//         DatabaseConnection.cannotConnect(); // Comment this line if you want to connect to the database
        if (!DatabaseConnection.isConnected()) {
            System.out.println(AnsiColors.toRed("App is not connected to the database!"));
        }
        // ===========================================

        //  Get menu instance
        ViewManager viewManager = ViewManager.getInstance();

        //  Create logging directory (if it doesn't exist already)
        try {
            Files.createDirectories(Paths.get("logging"));
        } catch (IOException e) {
            System.out.println(AnsiColors.toRed("Failed to create directory!"));
            return;
        }

        //  Create current logger directory
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH-mm-ss");
        String timestamp = ".\\logging\\" + dtf.format(LocalDateTime.now());

        Path logDir = Paths.get(timestamp);

        try {
            Files.createDirectories(logDir);
        } catch (IOException e) {
            System.out.println(AnsiColors.toRed("Failed to create directory!"));
            return;
        }

        //  Instantiate loggers
        Loggable verboseLogger = new FileLogger(LogLevel.VERBOSE, timestamp + "\\verbose.log");
        Loggable debugLogger = new FileLogger(LogLevel.DEBUG, timestamp + "\\debug.log");
        Loggable infoLogger = new FileLogger(LogLevel.INFO, timestamp + "\\info.log");
        Loggable warningLogger = new FileLogger(LogLevel.WARN,timestamp + "\\warnings.log");
        Loggable errorLogger = new FileLogger(LogLevel.ERROR, timestamp + "\\errors.log");
        Loggable fatalLogger = new FileLogger(LogLevel.FATAL, timestamp + "\\fatal.log");
        LogManager.getInstance().registerMultipleLoggers(verboseLogger, debugLogger, errorLogger,
                infoLogger, warningLogger, fatalLogger);

        //  Start app
        System.out.println(AnsiColors.toPurple("Welcome to Reddit!\nPlease choose an option:"));

        //  Initialize necessary variables for the app
        boolean isActive = true;
        Scanner scan;
        String option;
        MenuOption translatedInput;

        while (isActive) {
            //  Get view data
            View currentViewObject = viewManager.getCurrentViewObject();
            ViewID currentViewID = viewManager.getCurrentViewID();

            //  Display menu
            currentViewObject.displayMenu();

            //  Take and process user input
            scan = new Scanner(System.in);
            option = scan.nextLine();
            translatedInput = translateInput(option, currentViewID, viewManager.isLoggedIn());

            isActive = currentViewObject.activateMenuOption(translatedInput);
        }
    }
}