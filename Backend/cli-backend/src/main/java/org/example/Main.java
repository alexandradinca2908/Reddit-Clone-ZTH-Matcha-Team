package org.example;

import org.example.entities.ActionState;
import org.example.textprocessors.AnsiColors;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ActionState actionState = ActionState.getInstance();

        boolean isActive = true;

        System.out.println(AnsiColors.toOrange("Welcome to Reddit!\nPlease choose an option:\n"));

        while (isActive) {
            isActive = actionState.executeAction();
        }
    }
}