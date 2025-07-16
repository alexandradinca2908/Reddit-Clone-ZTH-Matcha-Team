package org.example.textprocessors;

import org.example.userinterface.UIPost;

public class TextSymbols {
    public static final String LINE_SEPARATOR = "────────────────────────────────────────────────";
    public static final String DOUBLE_LINE_SEPARATOR = "════════════════════════════════════════════════";
    public static final String HEADER = "ID: %d | USER: %s";
    public static final String REWARD = AnsiColors.toYellow("c[_] ");
    public static final String LEFT_BORDER = "║ ";
    public static final String LOWER_LEFT_CORNER = "╚";
    public static final String UPPER_LEFT_CORNER = "╔";

    public static String addReward(String string, int score) {
        if (score >= UIPost.MIN_REWARD_VOTES) {
            return TextSymbols.REWARD + string;
        } else {
            return string;
        }
    }
}
