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

        //  Initialize logging system
        LogManager.getInstance().initLoggers();

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