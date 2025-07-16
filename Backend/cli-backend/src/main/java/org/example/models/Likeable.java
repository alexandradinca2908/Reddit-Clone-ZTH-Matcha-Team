package org.example.models;

public interface Likeable {
    void upvote();
    void downvote();
    int getVotes();
}
