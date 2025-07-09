package org.example.textprocessors;

public class AnsiColors {
    public static final int MAX_TEXT_LENGTH = 20;
    public static final String LINE_SEPARATOR = "----------------------";

    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";

    public static String toGreen(String string) {
        return  GREEN + string + RESET;
    }
    public static String toRed(String string) {
        return  RED + string + RESET;
    }
    public static String toBlue(String string) {
        return  BLUE + string + RESET;
    }
}
