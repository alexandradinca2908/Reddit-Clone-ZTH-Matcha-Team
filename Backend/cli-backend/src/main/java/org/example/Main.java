package main.java.org.example;

import java.sql.SQLOutput;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Reddit!\n Please choose an option:\n");
        System.out.println("1. Login\n2. Register\n3. Logout\n4. Quit" );
        while(true){
            String option = scan.nextLine();
            if (option.equals("4") || option.equalsIgnoreCase("quit")){
                break;
            }
        }
        System.out.println("See you soon!");
    }
}