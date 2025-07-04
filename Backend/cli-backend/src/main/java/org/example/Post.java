package org.example;
import java.util.ArrayList;

public class Post implements Likeable{
    static int postsCounter = 0;
    int commentsCounter;
    int postID;
    int ownershipID;
    public String title;
    public String body;
    public int voteCount;

    public int getPostID() {
        return postID;
    }

    @Override
    public void upvote() {
        voteCount++;
    }
    @Override
    public void downvote() {
        voteCount--;
    }
    @Override
    public int getVotes() {
        return voteCount;
    }

    public Post(String title, String body, int ownershipID) {
        this.title = title;
        this.body = body;
        this.voteCount = 0;
        this.commentsCounter = 0;
        this.ownershipID = ownershipID;
        postID = postsCounter++;
    }




}

