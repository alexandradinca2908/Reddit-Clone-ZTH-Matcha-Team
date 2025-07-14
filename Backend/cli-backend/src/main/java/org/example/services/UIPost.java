package org.example.services;

import org.example.entities.Comment;
import org.example.entities.Post;
import org.example.textprocessors.AnsiColors;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UIPost {
    private static UIPost instance;

    public UIPost() {}
    public static UIPost getInstance() {
        if (instance == null) {
            instance = new UIPost();
        }
        return instance;
    }

    public void showFeed() {
        String headerText = String.format(AnsiColors.POST_COUNT_HEADER_FORMAT, Post.posts.size());
        System.out.println(AnsiColors.toGreen(headerText));
        System.out.println(AnsiColors.LINE_SEPARATOR);
        for (Post iter : Post.posts) {
            this.showPost(false, iter);
        }
    }

    public void showPost(boolean isExpanded, Post post) {
        if  (post == null) {
            System.out.println(AnsiColors.toRed("Post is null!"));
            return;
        }
        System.out.println(AnsiColors.toGreen("ID: " + post.getPostID() + " | USER: " + post.getUsername() + "\n"));
        System.out.println(AnsiColors.highlight(AnsiColors.addReward(post.getTitle(), post.getVotes())));
        if (!isExpanded) {
            if (post.getBody().length() > AnsiColors.MAX_TEXT_LENGTH) {
                System.out.println(post.getBody().substring(0, AnsiColors.MAX_TEXT_LENGTH) + "...\n");
            }
        } else {
            System.out.println(post.getBody());
            System.out.println();
        }
        System.out.print(AnsiColors.toRed("UP ") + post.getVotes() + AnsiColors.toBlue(" DOWN "));
        System.out.println( "| " + post.getCommentsCounter() + " comments");
        if (isExpanded) {
            System.out.println(AnsiColors.DOUBLE_LINE_SEPARATOR + "\n");
            this.printPostComments(post);
        } else {
            System.out.println(AnsiColors.LINE_SEPARATOR);
        }
    }

    public void printPostComments(Post post) {
        for (Comment comment : post.commentList) {
            printCommentAndReplies(comment, 0);
        }
    }

    private void printCommentAndReplies(Comment comment, int indentLevel) {
        String indent = "    ".repeat(indentLevel);

        System.out.println(indent + AnsiColors.toOrange("ID: " + comment.getCommentID() + " | USER: " + comment.getParentUser().getUsername()));
        System.out.println(indent + AnsiColors.addReward(comment.getCommentText(), comment.getVotes()));
        System.out.print(indent + AnsiColors.toRed("UP ") + comment.getVoteCount() + AnsiColors.toBlue(" DOWN "));
        System.out.println("| " + comment.replyList.size() + " replies");
        System.out.println(indent + AnsiColors.LINE_SEPARATOR);

        for (Comment reply : comment.replyList) {
            printCommentAndReplies(reply, indentLevel + 1);
        }
    }

    public static Map<String, String> getPostDetailsFromUser() {
        Scanner sc = new Scanner(System.in);
        Map<String, String> postData = new HashMap<>();

        System.out.println(AnsiColors.toGreen("Please enter title: "));
        String title = sc.nextLine();
        while (title.length() < AnsiColors.MAX_TEXT_LENGTH) {
            System.out.println(AnsiColors.toRed("Title must be at least " + AnsiColors.MAX_TEXT_LENGTH + " characters long."));
            title = sc.nextLine();
        }
        postData.put("title", title);

        System.out.println(AnsiColors.toGreen("Please enter description: "));
        String body = sc.nextLine();
        while (body.isEmpty()) {
            System.out.println(AnsiColors.toRed("Description can not be empty!"));
            body = sc.nextLine();
        }
        postData.put("body", body);

        return postData;
    }
}
