package org.example;
import java.util.ArrayList;

public class Post implements Likeable{
    static int postsCounter = 0;
    int commentsCounter;
    int postID;
    int ownershipName;
    public String title;
    public String body;
    public int voteCount;

    public int getPostID() {
        return postID;
    }

    public int getOwnershipID() {
        return ownershipName;
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

    public Post(String title, String body, String username) {
        this.title = title;
        this.body = body;
        this.voteCount = 0;
        this.commentsCounter = 0;
        this.ownershipName = ownershipName;
        postID = postsCounter++;
    }




}

