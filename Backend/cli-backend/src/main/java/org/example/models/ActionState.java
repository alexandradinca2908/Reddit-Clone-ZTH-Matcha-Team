package org.example.models;

import org.example.services.CommentService;
import org.example.services.PostService;
import org.example.services.VotingService;
import org.example.userinterface.UIComment;
import org.example.userinterface.UIPost;
import org.example.services.UserService;
import org.example.textprocessors.AnsiColors;

import java.util.Scanner;

import static org.example.textprocessors.InputTranslator.translateInput;

public class ActionState {
    private final static UserService userService = UserService.getInstance();
    private final static PostService postService = PostService.getInstance();
    private final static CommentService commentService = CommentService.getInstance();
    private final static VotingService votingService = VotingService.getInstance();
    private static ActionState actionState;
    private boolean isLoggedIn;
    private State currentState;
    private User user;
    private Post post;
    private Comment comment;
    private Comment commentReply;
    private UIPost UIPost = new UIPost();
    private UIComment UIComment = new UIComment();

    private ActionState() {
        this.isLoggedIn = false;
        this.currentState = State.MAIN_MENU;
        this.user = null;
        this.post = null;
    }

    public static ActionState getInstance() {
        if (actionState == null)
            actionState = new ActionState();

        return actionState;
    }

    public boolean executeAction() {
        switch (currentState) {
            case MAIN_MENU:
                if (!isLoggedIn) {
                    mainMenuNotLoggedIn();
                } else {
                    mainMenuLoggedIn();
                }

                break;

            case ON_FEED:
                onFeed();
                break;

            case ON_POST:
                if (!isLoggedIn) {
                    onPostNotLoggedIn();
                } else {
                    onPostLoggedIn();
                }

                break;

            case ON_COMMENT:
                onComment();
                break;

            case ON_REPLY:
                onReply();
                break;

            case LOGOUT:
                logout();
                break;

            case QUIT:
                quit();
                return false;

            default:
                break;
        }

        return true;
    }

    private void mainMenuNotLoggedIn() {
        System.out.println("""
                            1. Login
                            2. Register
                            3. Show feed
                            4. Quit""");

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String translatedInput = translateInput(option, currentState, isLoggedIn);

        if (translatedInput.equalsIgnoreCase("login")) {
            user = userService.userLoginCLI();

            //  Display login menu only if action was successful
            if (user != null) {
                isLoggedIn = true;
            }
        } else if (translatedInput.equalsIgnoreCase("register")) {
            user = userService.userRegisterCLI();

            //  Display login menu only if action was successful
            if (user != null) {
                isLoggedIn = true;
            }
        } else if (translatedInput.equalsIgnoreCase("show feed")) {
            UIPost.showFeed();
            changeState(State.ON_FEED);
        } else if (translatedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
        } else {
            unknownCommand();
        }
    }

    private void mainMenuLoggedIn() {
        System.out.println("""
                            1. Show feed
                            2. Create post
                            3. Logout
                            4. Delete Account
                            5. Quit""");

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String translatedInput = translateInput(option, currentState, isLoggedIn);

        if (translatedInput.equalsIgnoreCase("show feed")) {
            UIPost.showFeed();
            changeState(State.ON_FEED);
        }  else if (translatedInput.equalsIgnoreCase("create post")) {
            postService.addPost(user.getUsername());
        } else if (translatedInput.equalsIgnoreCase("logout")) {
            changeState(State.LOGOUT);
        } else if (translatedInput.equalsIgnoreCase("delete account")) {
            userService.userDeleteCLI(this.user);
            isLoggedIn = false;
        } else if (translatedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
        } else {
            unknownCommand();
        }
    }

    private void onFeed() {
        System.out.println("""
                            1. Expand post
                            2. Return to menu
                            3. Quit""");

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String translatedInput = translateInput(option, currentState, isLoggedIn);

        if (translatedInput.equalsIgnoreCase("expand post")) {
            try {
                post = postService.getPost(postService.getPostIDUser());
                UIPost.showPost(true, post);
                changeState(State.ON_POST);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else if (translatedInput.equalsIgnoreCase("return to menu")) {
            post = null;
            changeState(State.MAIN_MENU);
        } else if (translatedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
        } else {
            unknownCommand();
        }

    }
    
    private void onPostNotLoggedIn() {
        System.out.println("""
                        1. Return to feed
                        2. Quit""");

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String translatedInput = translateInput(option, currentState, isLoggedIn);

        if (translatedInput.equalsIgnoreCase("return to feed")) {
            UIPost.showFeed();
            changeState(State.ON_FEED);
        } else if (translatedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
        } else {
            unknownCommand();
        }
    }

    private void onPostLoggedIn() {
        System.out.println("""
                        1. Comment
                        2. Upvote
                        3. Downvote
                        4. Select comment
                        5. Return to feed
                        6. Quit""");

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String translatedInput = translateInput(option, currentState, isLoggedIn);

        if (translatedInput.equalsIgnoreCase("comment")) {
            commentService.addComment(user, post);
            UIPost.showPost(true, post);
        } else if (translatedInput.equalsIgnoreCase("upvote")) {
            votingService.votePost(user, post, true);
            UIPost.showPost(true, post);
        } else if (translatedInput.equalsIgnoreCase("downvote")) {
            votingService.votePost(user, post, false);
            UIPost.showPost(true, post);
        } else if (translatedInput.equalsIgnoreCase("select comment")) {
            try {
                comment = commentService.selectComment(post);
                changeState(State.ON_COMMENT);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (translatedInput.equalsIgnoreCase("return to feed")) {
            UIPost.showFeed();
            changeState(State.ON_FEED);
        } else if (translatedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
        } else {
            unknownCommand();
        }
    }

    private void onComment() {
        UIComment.showAllCommentsAndReplies(post);
        //  This state can only be accessed if the user is logged in
        System.out.println("""
                        1. Reply
                        2. Upvote
                        3. Downvote
                        4. Select reply
                        5. Return to post
                        6. Quit""");

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String translatedInput = translateInput(option, currentState, isLoggedIn);

        if (translatedInput.equalsIgnoreCase("reply")) {
            commentService.addReply(user, comment);
        } else if (translatedInput.equalsIgnoreCase("upvote")) {
            votingService.voteComment(user, comment, true);
            changeState(State.ON_COMMENT);
        } else if (translatedInput.equalsIgnoreCase("downvote")) {
            votingService.voteComment(user, comment, false);
            changeState(State.ON_COMMENT);
        } else if (translatedInput.equalsIgnoreCase("select reply")) {
            commentReply = commentService.selectReply(comment);
            changeState(State.ON_REPLY);
        } else if (translatedInput.equalsIgnoreCase("return to post")) {
            UIPost.showPost(true, post);
            changeState(State.ON_POST);
        } else if (translatedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
        } else {
            unknownCommand();
        }
    }

    private void onReply() {
        //  This state can only be accessed if the user is logged in
        System.out.println("""
                        1. Upvote
                        2. Downvote
                        3. Return to comment
                        4. Quit""");

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String translatedInput = translateInput(option, currentState, isLoggedIn);

        if (translatedInput.equalsIgnoreCase("upvote")) {
            votingService.voteReply(user, commentReply, true);
            changeState(State.ON_REPLY);
        } else if (translatedInput.equalsIgnoreCase("downvote")) {
            votingService.voteReply(user, commentReply, false);
            changeState(State.ON_REPLY);
        } else if (translatedInput.equalsIgnoreCase("return to comment")) {
            UIComment.showAllCommentsAndReplies(post);
            changeState(State.ON_COMMENT);
        } else if (translatedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
        } else {
            unknownCommand();
        }
    }

    private void logout() {
        cleanUpData();
        System.out.println("You have been logged out.");

        changeState(State.MAIN_MENU);
    }

    private void quit() {
        if (isLoggedIn) {
            cleanUpData();
            System.out.println("You have been automatically logged out.");
        }

        System.out.println("See you soon!");
    }

    private void cleanUpData() {
        isLoggedIn  = false;
        user = null;
        post = null;
        comment = null;
        commentReply = null;
    }

    private void unknownCommand() {
        System.out.println(AnsiColors.toRed("Unknown command."));
        System.out.println(AnsiColors.toOrange("Make sure to type the corresponding number or exact command!"));
    }

    private void changeState(State state) {
        currentState = state;
    }
}
