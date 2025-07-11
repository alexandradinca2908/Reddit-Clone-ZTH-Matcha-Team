package org.example;

import org.example.entities.ActionState;
import org.example.repositories.CommentRepo;
import org.example.textprocessors.AnsiColors;

import java.io.IOException;

public class Main {
    private static final CommentRepo commentRepo = CommentRepo.getInstance();

    public static void main(String[] args) throws IOException {
        ActionState actionState = ActionState.getInstance();
        commentRepo.load();

        // myLogger1 = new FileLogger("errors.txt");
        // myLogger2 = new FileLogger("info.txt");
        // LogManager.getInstance().RegisterLogger(myLogger1);
        // LogManager.getInstance().RegisterLogger(myLogger2);
        boolean isActive = true;

        System.out.println(AnsiColors.toPurple("Welcome to Reddit!\nPlease choose an option:\n"));

        while (isActive) {
            isActive = actionState.executeAction();
        }
    }
}