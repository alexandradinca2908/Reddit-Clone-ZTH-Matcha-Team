package org.example;
import java.util.Scanner;

public class Main {
    private final static UserService userService = new UserService();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to Reddit!\n Please choose an option:\n");
        System.out.println("1. Login\n2. Register\n3. Logout\n4. Quit" );

        while (true){
            String option = scan.nextLine();
            if (option.equals("2") || option.equalsIgnoreCase("register")) {
                userService.userRegisterCLI();
            }
            else if (option.equals("4") || option.equalsIgnoreCase("quit")){
                break;
            }
        }
        System.out.println("See you soon!");
    }
}