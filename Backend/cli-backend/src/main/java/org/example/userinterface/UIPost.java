package org.example.userinterface;

import org.example.models.Post;
import org.example.models.Comment;
import org.example.services.PostService;
import org.example.textprocessors.AnsiColors;
import org.example.textprocessors.TextSymbols;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UIPost {
    private static UIPost instance;
    public static final String POST_COUNT_HEADER_FORMAT = "\t\t=== Showing a total of %d posts ===";
    public static final int MIN_TEXT_LENGTH = 20;
    public static final int MAX_TITLE_LENGTH = 40;
    private static final int POST_WIDTH = 50;
    public static final int MIN_REWARD_VOTES = 1;
    private static final String PROMPT_TITLE = AnsiColors.toGreen("Please enter title: ");
    private static final String PROMPT_DESCRIPTION = AnsiColors.toGreen("Please enter description: ");
    private static final String ERROR_TITLE_TOO_LONG = AnsiColors.toRed("Title must be at most %d characters long.");
    private static final String ERROR_TITLE_TOO_SHORT = AnsiColors.toRed("Title must be at least %d characters long.");
    private static final String ERROR_DESCRIPTION_EMPTY = AnsiColors.toRed("Description can not be empty!");

    UIComment uiComment = UIComment.getInstance();

    public UIPost() {}
    public static UIPost getInstance() {
        if (instance == null) {
            instance = new UIPost();
        }
        return instance;
    }

    // deprecated
    public void showFeed() {
        String headerText = String.format(UIPost.POST_COUNT_HEADER_FORMAT, PostService.posts.size());
        System.out.println(AnsiColors.toGreen(headerText));
        System.out.println(AnsiColors.toGreen(TextSymbols.LINE_SEPARATOR));
        for (Post iter : PostService.posts) {
            this.showPost(false, iter);
        }
    }

    // deprecated
    public void showPost(boolean isExpanded, Post post) {
        if  (post == null) {
            System.out.println(AnsiColors.toRed("Post is null!"));
            return;
        }
        System.out.println(AnsiColors.toGreen("ID: " + post.getPostID() + " | USER: " + post.getUsername() + "\n"));
        System.out.println(TextSymbols.addReward(AnsiColors.highlight(post.getTitle()), post.getVotes()));
        if (!isExpanded) {
            if (post.getBody().length() > UIPost.MIN_TEXT_LENGTH) {
                System.out.println(post.getBody().substring(0, UIPost.MIN_TEXT_LENGTH) + "...\n");
            } else {
                System.out.println(post.getBody() + "\n");
            }
        } else {
            System.out.println(post.getBody() + "\n");
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
        while (title.length() < UIPost.MIN_TEXT_LENGTH || title.length() > UIPost.MAX_TITLE_LENGTH) {
            System.out.printf((ERROR_TITLE_TOO_SHORT) + "%n", UIPost.MIN_TEXT_LENGTH);
            System.out.printf((ERROR_TITLE_TOO_LONG) + "%n", UIPost.MAX_TITLE_LENGTH);
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

    public void printFeed() {
        String headerText = String.format(UIPost.POST_COUNT_HEADER_FORMAT, PostService.posts.size());
        System.out.println(AnsiColors.toGreen(headerText));
        for (Post iter : PostService.posts) {
            this.printPost(false, iter);
        }
    }

    public void printPost(boolean isExpanded, Post post) {
        printTopBorder();
        printHeader(post.getPostID(), post.getUsername());
        printSeparator();
        printTitleLine(post.getTitle(), post.getVotes());
        printSeparator();
        printContentLine(isExpanded, post.getBody());
        printEmptyContentLine();
        printSeparator();
        printFooter(post.getVotes(), post.getVotes(), post.getCommentsCounter());
        printBottomBorder(isExpanded);
        if (isExpanded) {
            uiComment.showAllCommentsAndReplies(post);
        }
    }

    private static void printTopBorder() {
        System.out.print("╔");
        for (int i = 0; i < POST_WIDTH - 2; i++) {
            System.out.print("═");
        }
        System.out.println("╗");
    }

    private static void printBottomBorder(boolean isExpanded) {
        System.out.print("╚");
        for (int i = 0; i < POST_WIDTH - 2; i++) {
            System.out.print("─");
        }

        if (isExpanded) {
            System.out.println("╝");
        } else {
            System.out.println("╝" + "\n");
        }
    }

    private static void printSeparator() {
        System.out.print("╠");
        for (int i = 0; i < POST_WIDTH - 2; i++) {
            System.out.print("─");
        }
        System.out.println("╣");
    }

    private static void printHeader(int postId, String user) {
        String postLabel = "ID: " + postId;
        String userLabel = "USER: " + user;
        int totalLength = postLabel.length() + userLabel.length();
        int padding = POST_WIDTH - 4 - totalLength;

        System.out.print("║ " + AnsiColors.toGreen(postLabel));
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.println(AnsiColors.toGreen(userLabel)+ " ║");
    }

    private static void printEmptyContentLine() {
        System.out.print("║ ");
        for (int i = 0; i < POST_WIDTH - 3; i++) {
            System.out.print(" ");
        }
        System.out.println("║");
    }

    private static void printTitleLine(String title, int score) {
        System.out.print("║ " + TextSymbols.addReward(title, score));
        int padding = POST_WIDTH - 4 - TextSymbols.addReward(title, score).length();
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.println(" ║");
    }


    private static void printContentLine(boolean isExpanded, String content) {
        if (!isExpanded) {
            int contentLength;
            if (content.length() > UIPost.MAX_TITLE_LENGTH) {
                String substring = content.substring(0, UIPost.MIN_TEXT_LENGTH) + "...";
                contentLength = substring.length();
                System.out.print("║ " + substring);
            } else {
                contentLength = content.length();
                System.out.print("║ " + content);
            }
            int padding = POST_WIDTH - 4 - contentLength;
            for (int i = 0; i < padding; i++) {
                System.out.print(" ");
            }
            System.out.println(" ║");
        } else {
            if (content.length() < POST_WIDTH - 4) {
                System.out.print("║ " + content);
                int padding = POST_WIDTH - 4 - content.length();
                for (int i = 0; i < padding; i++) {
                    System.out.print(" ");
                }
                System.out.println(" ║");
            } else {
                int maxLineLength = POST_WIDTH - 4;
                for (int i = 0; i < content.length(); i += maxLineLength) {
                    System.out.print("║ ");
                    int endIndex = Math.min(i + maxLineLength, content.length());
                    String line = content.substring(i, endIndex);
                    System.out.print(line);

                    if (endIndex == content.length()) {
                        int padding = maxLineLength - line.length();
                        for (int j = 0; j < padding; j++) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println(" ║");
                }
            }
        }
    }

    private static void printFooter(int upvotes, int downvotes, int comments) {
        String votes = AnsiColors.toRed("UP ") + upvotes + AnsiColors.toBlue(" DOWN");
        String commentsStr = comments + " comments";
        int totalLength = votes.replaceAll("\u001B\\[[;\\d]*m", "").length()
                + commentsStr.length();
        int padding = POST_WIDTH - 4 - totalLength;

        System.out.print("║ " + votes);
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }
        System.out.println(commentsStr + " ║");
    }




}
