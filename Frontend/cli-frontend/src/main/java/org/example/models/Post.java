package org.example.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Post extends Likeable {
    private static int postCounter = 1;
    private ArrayList<Comment> commentList;
    private int postID;
    private String UUID;
    private String authorName;
    private String title;
    private String content;
    private int score;
    private HashMap<String, Integer> votingUserID; //K = userID , V = -1/+1 -> downvote/upvote


    public Post(String UUID, String title, String content, String username) {
        this.UUID = UUID;
        this.title = title;
        this.content = content;
        this.score = 0;
        this.authorName = username;
        this.postID = postCounter++;
        this.commentList = new ArrayList<>();
        this.votingUserID = new HashMap<>();
    }


    @Override
    public void upvote() {
        score++;
    }
    @Override
    public void downvote() {
        score--;
    }
    @Override
    public int getVotes() {
        return score;
    }

    public String getUUID() {
        return UUID;
    }

    public int getScore() {
        return score;
    }
    public int getPostID() {
        return postID;
    }
    public String getAuthorName() {
        return authorName;
    }
    public int getCommentsCounter() { return commentList.size(); }
    public ArrayList<Comment> getCommentList() { return commentList; }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public HashMap<String, Integer> getVotingUserID() {
        return votingUserID;
    }


}

