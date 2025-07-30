package org.matcha.springbackend.entities;

public enum VoteType {
    UP,
    DOWN;

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