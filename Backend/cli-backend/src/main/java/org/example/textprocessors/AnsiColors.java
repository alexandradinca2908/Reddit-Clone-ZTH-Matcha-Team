package org.example.textprocessors;

public class AnsiColors {
    public static final int MAX_TEXT_LENGTH = 20;
    public static final int MIN_REWARD_VOTES = 10;
    public static final String LINE_SEPARATOR = "----------------------";
    public static final String DOUBLE_LINE_SEPARATOR = "======================";
    public static final String POST_COUNT_HEADER_FORMAT = "=== Showing a total of %d posts ===";
    public static final String REWARD = AnsiColors.toOrange("c[_] ");

    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    public static final String ORANGE = "\u001B[38;2;255;165;0m";
    public static final String BLACK_ON_WHITE = "\u001B[30;47m";

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
    public static String highlight(String string) {
        return   BLACK_ON_WHITE + string + RESET;
    }

    public static String addReward(String string, int score) {
        if (score >= MIN_REWARD_VOTES) {
            return REWARD.concat(string);
        } else {
            return string;
        }
    }

}
