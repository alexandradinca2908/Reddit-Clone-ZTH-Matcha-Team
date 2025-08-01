package org.example;

import org.example.dbconnection.DatabaseConnection;
import org.example.loggerobjects.LogManager;
import org.example.menu.MenuOption;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.views.ViewManager;
import org.example.textprocessors.AnsiColors;

//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.cfg.Configuration;

import java.util.Scanner;

import static org.example.textprocessors.InputTranslator.translateInput;

public class Main {
    public static void main(String[] args) {



            // Test DB connection (opțional, în funcție de implementarea ta)
            if (!DatabaseConnection.isConnected()) {
                System.out.println(AnsiColors.toRed("App is not connected to the database!"));
            }

            // Initialize logging system
            LogManager.getInstance().initLoggers();

            // Get ViewManager instance
            ViewManager viewManager = ViewManager.getInstance();

            System.out.println(AnsiColors.toPurple("Welcome to Matcha Reddit!\nPlease choose an option:"));

            try (Scanner scanner = new Scanner(System.in)) {
                boolean isActive = true;

                while (isActive) {
                    // Get current view and its ID
                    View currentView = viewManager.getCurrentViewObject();
                    ViewID currentViewID = viewManager.getCurrentViewID();

                    // Display menu for the current view
                    currentView.displayMenu();

                    // Read and translate user input
                    String input = scanner.nextLine();
                    MenuOption menuOption = translateInput(input, currentViewID, viewManager.isLoggedIn());

                    // Activate the chosen menu option
                    isActive = currentView.activateMenuOption(menuOption);
                }
            }


    }
}
