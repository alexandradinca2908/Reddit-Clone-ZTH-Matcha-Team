package org.matcha.springbackend.enumpackage;

public enum VoteType {
    UP,
    DOWN,
    NONE;

    public static VoteType stringToVoteType(String string) {
        if (string.equalsIgnoreCase("UP")) {
            return VoteType.UP;
        }

        if (string.equalsIgnoreCase("DOWN")) {
            return VoteType.DOWN;
        }

        return null;
    }
}