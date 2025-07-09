package org.example;

import org.example.entities.ActionState;

public class Main {
    public static void main(String[] args) {
        ActionState actionState = ActionState.getInstance();
        boolean isActive = true;

        System.out.println("Welcome to Reddit!\nPlease choose an option:\n");

        while (isActive) {
            isActive = actionState.executeAction();
        }
    }
}