package org.example.entities;

public interface Likeable {
    void upvote();
    void downvote();
    int getVotes();
}
