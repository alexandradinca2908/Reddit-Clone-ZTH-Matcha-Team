package org.example;

public class Post implements Likeable{
    int postID;
    static int postCounter = 0;
    int voteCount = 0;
    String title;
    String body;

    @Override
    public void upvote() {
        postCounter++;
    }
    @Override
    public void downvote() {
        postCounter--;
    }
    @Override
    public int getVotes() {
        return voteCount;
    }


}
