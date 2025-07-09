package org.example.entities;
import org.example.textprocessors.AnsiColors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Post extends AnsiColors implements Likeable{
    public static int postsCounter = 0;
    public static ArrayList<Post> posts = new ArrayList<>(
            List.of(
                    new Post("First Post", "This is the body of the first post.", "TestUser1"),
                    new Post("Second Post", "This is the body of the second post.", "TestUser2"),
                    new Post("Third Post", "This is the body of the third post.", "TestUser1")
            )
    );
    int commentsCounter;
    int postID;
    String ownershipName;
    public String title;
    public String body;
    public int voteCount;
    public HashMap<Integer, Integer> votingUserID; //K = userID , V = -1/+1 -> downvote/upvote
    public ArrayList<Comment> commentList;

    public int getPostID() {
        return postID;
    }

    public String getOwnershipName() {
        return ownershipName;
    }

    public int getCommentsCounter() {
        return commentsCounter;
    }

    public void addComment(User parentUser, String commentText) {
        Comment comment = new Comment(this, parentUser, commentText);
        commentsCounter++;
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
        this.ownershipName = username;
        this.commentList = new ArrayList<>();
        this.votingUserID = new HashMap<>();
        postID = postsCounter++;
    }

    public void printComments(int indentLevel) {
        for  (Comment comment : commentList) {
            System.out.println(AnsiColors.toGreen("CID: " + comment.getCommentID() + " | USER: " + comment.getParentUser()));
            System.out.print(comment.getCommentText());
            System.out.print(AnsiColors.toRed("UP ") + comment.getVoteCount() + AnsiColors.toBlue(" DOWN "));
            System.out.println( "| " + comment.replyList.toArray().length + " replies");
            System.out.println(LINE_SEPARATOR);
        }
    }




}

