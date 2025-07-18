package org.example.models;
import org.example.repositories.CommentRepo;
import org.example.repositories.PostRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Post extends Likeable {
    private static final PostRepo userRepo = PostRepo.getInstance();
    private static int postCounter = 0;
    private int displayIndex = ++postCounter;
    private ArrayList<Comment> commentList;
    private UUID postID;
    private String username;
    private String title;
    private String body;
    private int voteCount;
    private HashMap<String, Integer> votingUserID; //K = userID , V = -1/+1 -> downvote/upvote

    public Post(String title, String body, String username) {
        this.title = title;
        this.body = body;
        this.voteCount = 0;
        this.username = username;
        this.postID = null;
        this.commentList = new ArrayList<>();
        this.votingUserID = new HashMap<>();
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

    public UUID getPostID() {
        return postID;
    }

    public int getCommentsCounter() { return commentList.size(); }
    public ArrayList<Comment> getCommentList() { return commentList; }
    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }
    public HashMap<String, Integer> getVotingUserID() {
        return votingUserID;
    }
    public void setPostId(UUID dbPostID) {
        this.postID = dbPostID;
    }

    public String getUsername() {
        return username;
    }

}

