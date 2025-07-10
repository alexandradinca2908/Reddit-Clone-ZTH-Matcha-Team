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
        this.commentList.add(comment);
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
        String indent = "    ".repeat(indentLevel); // 4 spaces per level

        for (Comment comment : commentList) {
            // Print the main comment
            System.out.println(indent + AnsiColors.toOrange("CID: " + comment.getCommentID() + " | USER: " + comment.getParentUser().getUsername()));
            System.out.println(indent + comment.getCommentText());
            System.out.print(indent + AnsiColors.toRed("UP ") + comment.getVoteCount() + AnsiColors.toBlue(" DOWN "));
            System.out.println("| " + comment.replyList.size() + " replies");
            System.out.println(indent + LINE_SEPARATOR);

            // Now print replies with increased indent
            for (CommentReply reply : comment.replyList) {
                printReply(reply, indentLevel + 1);
            }
        }
    }

    public void printReply(CommentReply reply, int indentLevel) {
        String indent = "    ".repeat(indentLevel);

        System.out.println(indent + AnsiColors.toYellow("RID " + reply.getCommentReplyID() + " | USER: " + reply.getParentUser().getUsername()));
        System.out.println(indent + reply.getCommentReplyText());
        System.out.print(indent + AnsiColors.toRed("UP ") + reply.getVotes() + AnsiColors.toBlue(" DOWN "));
        System.out.println("| " + reply.commentReplies.size() + " replies");
        System.out.println(indent + LINE_SEPARATOR);

        // daca o sa avem nested replies
        for (CommentReply nested : reply.getCommentReplies()) {
            printReply(nested, indentLevel + 1);
        }
    }

}

