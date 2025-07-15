package org.example.userinterface;

import org.example.entities.Comment;
import org.example.entities.Post;
import org.example.textprocessors.AnsiColors;
import org.example.textprocessors.TextSymbols;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UIPost {
    private static UIPost instance;
    public static final String POST_COUNT_HEADER_FORMAT = "=== Showing a total of %d posts ===";
    public static final int MAX_TEXT_LENGTH = 20;;
    private static final String PROMPT_TITLE = AnsiColors.toGreen("Please enter title: ");
    private static final String PROMPT_DESCRIPTION = AnsiColors.toGreen("Please enter description: ");
    private static final String ERROR_TITLE_TOO_SHORT = AnsiColors.toRed("Title must be at least %d characters long.");
    private static final String ERROR_DESCRIPTION_EMPTY = AnsiColors.toRed("Description can not be empty!");


    public UIPost() {}
    public static UIPost getInstance() {
        if (instance == null) {
            instance = new UIPost();
        }
        return instance;
    }

    public void showFeed() {
        String headerText = String.format(UIPost.POST_COUNT_HEADER_FORMAT, Post.posts.size());
        System.out.println(AnsiColors.toGreen(headerText));
        System.out.println(TextSymbols.LINE_SEPARATOR);
        for (Post iter : Post.posts) {
            this.showPost(false, iter);
        }
    }

    public void showPost(boolean isExpanded, Post post) {
        if  (post == null) {
            System.out.println(AnsiColors.toRed("Post is null!"));
            return;
        }
        UIComment uiComment = UIComment.getInstance();
        System.out.println(AnsiColors.toGreen("ID: " + post.getPostID() + " | USER: " + post.getUsername() + "\n"));
        System.out.println(AnsiColors.highlight(AnsiColors.addReward(post.getTitle(), post.getVotes())));
        if (!isExpanded) {
            if (post.getBody().length() > UIPost.MAX_TEXT_LENGTH) {
                System.out.println(post.getBody().substring(0, UIPost.MAX_TEXT_LENGTH) + "...\n");
            }
        } else {
            System.out.println(post.getBody());
            System.out.println();
        }
        System.out.print(AnsiColors.toRed("UP ") + post.getVotes() + AnsiColors.toBlue(" DOWN "));
        System.out.println( "| " + post.getCommentsCounter() + " comments");
        if (isExpanded) {
            System.out.println(TextSymbols.DOUBLE_LINE_SEPARATOR);
            uiComment.showAllCommentsAndReplies(post);
        } else {
            System.out.println(TextSymbols.LINE_SEPARATOR);
        }
    }

    public static Map<String, String> getPostDetailsFromUser() {
        Scanner sc = new Scanner(System.in);
        Map<String, String> postData = new HashMap<>();

        System.out.println(PROMPT_TITLE);
        String title = sc.nextLine();
        while (title.length() < UIPost.MAX_TEXT_LENGTH) {
            System.out.printf((ERROR_TITLE_TOO_SHORT) + "%n", UIPost.MAX_TEXT_LENGTH);
            title = sc.nextLine();
        }
        postData.put("title", title);

        System.out.println(PROMPT_DESCRIPTION);
        String body = sc.nextLine();
        while (body.isEmpty()) {
            System.out.println(ERROR_DESCRIPTION_EMPTY);
            body = sc.nextLine();
        }
        postData.put("body", body);

        return postData;
    }
}
