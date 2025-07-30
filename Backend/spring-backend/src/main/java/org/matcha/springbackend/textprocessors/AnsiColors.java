package org.matcha.springbackend.textprocessors;

public class AnsiColors {
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    public static final String ORANGE = "\u001B[38;2;255;165;0m";

    public static String toGreen(String string) {
        return  GREEN + string + RESET;
    }
    public static String toRed(String string) {
        return  RED + string + RESET;
    }
    public static String toBlue(String string) {
        return  BLUE + string + RESET;
    }
    public static String toYellow(String string) {
        return  YELLOW + string + RESET;
    }
    public static String toPurple(String string) {
        return  PURPLE + string + RESET;
    }
    public static String toOrange(String string) {
        return  ORANGE + string + RESET;
    }



}
