package org.example;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Comment implements Likeable {
    private int commentID;
    private int commentCounter = 0;
    private int voteCount = 0;
    private int replyCount = 0;
    private String body;
    private List<Comment> replies;
    private User author;
    private Post parentPost;
    private ArrayList<Comment> comments;
    private Comment parentComment;
    private static List<Comment> allComments = new ArrayList<>();

    public Comment(String body, User author, Post parentPost) {
        this.body = body;
        this.author = author;
        this.parentPost = parentPost;
        this.parentComment = null;
        this.commentID = commentCounter++;
        this.replies = new ArrayList<>();
        allComments.add(this);
    }

    // Constructor pentru reply-uri.
    public Comment(String body, User author, Post parentPost, Comment parentComment) {
        this.body = body;
        this.author = author;
        this.parentPost = parentPost;
        this.parentComment = parentComment;
        this.commentID = commentCounter++;
        this.replies = new ArrayList<>();
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

    public void addReply(Comment reply) {
        this.replies.add(reply);
    }

    public List<Comment> getReplies() {
        return new ArrayList<>(replies);
    }

    public static List<Comment> getCommentsForPost(Post post) {
        return allComments.stream()
                .filter(comment -> comment.parentPost.equals(post))
                .collect(Collectors.toList());
    }

    public static List<Comment> getAllCommentsForPost(Post post) {
        List<Comment> allPostComments = new ArrayList<>();
        List<Comment> topLevelComments = getCommentsForPost(post);

        for (Comment comment : topLevelComments) {
            allPostComments.add(comment);
            addAllReplies(comment, allPostComments);
        }

        return allPostComments;
    }

    private static void addAllReplies(Comment comment, List<Comment> allComments) {
        for (Comment reply : comment.replies) {
            allComments.add(reply);
            addAllReplies(reply, allComments);
        }
    }

    public int getCommentID() {
        return commentID;
    }

    public String getBody() {
        return body;
    }

    public User getAuthor() {
        return author;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isReply() {
        return parentComment != null;
    }

    public boolean hasReplies() {
        return !replies.isEmpty();
    }

}
