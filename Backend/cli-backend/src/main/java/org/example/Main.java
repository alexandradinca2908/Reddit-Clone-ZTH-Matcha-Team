package org.example;

import org.example.services.UserService;

public class Main {
    public static void main(String[] args) {
        ActionState actionState = ActionState.getInstance();
        boolean isActive = true;

        System.out.println("Welcome to Reddit!\n4Please choose an option:\n");

        while (isActive) {
            isActive = actionState.executeAction();
        }
    }
}