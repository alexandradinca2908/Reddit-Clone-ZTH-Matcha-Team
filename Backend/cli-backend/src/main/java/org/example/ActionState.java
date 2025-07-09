package org.example;

import org.example.services.PostService;
import org.example.services.UserService;

import java.util.Scanner;

public class ActionState {
    public enum State {
        MAIN_MENU,
        ON_FEED,
        ON_POST,
        ON_COMMENT,
        LOGOUT,
        QUIT
    }

    private static ActionState actionState;
    private final static UserService userService = UserService.getInstance();
    private final static PostService postService = new PostService();
    private boolean isLoggedIn = false;
    private State currentState;
    private User user;
    private Post post;

    private ActionState() {
        this.isLoggedIn = false;
        this.currentState = State.MAIN_MENU;
        this.user = null;
        this.post = null;
    }

    public static ActionState getInstance()
    {
        if (actionState == null)
            actionState = new ActionState();

        return actionState;
    }

    boolean executeAction() {
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
        String sanitizedInput = translateInput(option);

        if (sanitizedInput.equalsIgnoreCase("login")) {
            user = userService.userLoginCLI();

            //  Display login menu only if action was successful
            if (user != null) {
                isLoggedIn = true;
            }
        } else if (sanitizedInput.equalsIgnoreCase("register")) {
            user = userService.userRegisterCLI();

            //  Display login menu only if action was successful
            if (user != null) {
                isLoggedIn = true;
            }
        } else if (sanitizedInput.equalsIgnoreCase("show feed")) {
            postService.showFeed();
            changeState(State.ON_FEED);
        } else if (sanitizedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
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
        String sanitizedInput = translateInput(option);

        if (sanitizedInput.equalsIgnoreCase("show feed")) {
            postService.showFeed();
            changeState(State.ON_FEED);
        }  else if (sanitizedInput.equalsIgnoreCase("create post")) {
            postService.addPost(user.getUsername());
        } else if (sanitizedInput.equalsIgnoreCase("logout")) {
            changeState(State.LOGOUT);
        } else if (sanitizedInput.equalsIgnoreCase("delete account")) {
            userService.userDeleteCLI(this.user);
            isLoggedIn = false;
        } else if (sanitizedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
        }
    }

    private void onFeed() {
        System.out.println("""
                            1. Expand post
                            2. Return to menu
                            3. Quit""");

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String sanitizedInput = translateInput(option);

        if (sanitizedInput.equalsIgnoreCase("expand post")) {
            try {
                postService.expandPost();
                changeState(State.ON_POST);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else if (sanitizedInput.equalsIgnoreCase("return to menu")) {
            changeState(State.MAIN_MENU);
        } else if (sanitizedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
        }

    }

    private void onPostNotLoggedIn() {
        System.out.println("""
                        1. Return to feed
                        2. Quit""");

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String sanitizedInput = translateInput(option);

        if (sanitizedInput.equalsIgnoreCase("return to feed")) {
            postService.showFeed();
            changeState(State.ON_FEED);
        } else if (sanitizedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
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
        String sanitizedInput = translateInput(option);

        if (sanitizedInput.equalsIgnoreCase("comment")) {
            //  TODO COMMENT
        } else if (sanitizedInput.equalsIgnoreCase("upvote")) {
            //  TODO UPVOTE
        } else if (sanitizedInput.equalsIgnoreCase("downvote")) {
            //  TODO DOWNVOTE
        } else if (sanitizedInput.equalsIgnoreCase("select comment")) {
            //  TODO SELECT COMMENT
            changeState(State.ON_COMMENT);
        } else if (sanitizedInput.equalsIgnoreCase("return to feed")) {
            postService.showFeed();
            changeState(State.ON_FEED);
        } else if (sanitizedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
        }
    }

    private void onComment() {
        //  This state can only be accessed if the user is logged in
        System.out.println("""
                        1. Reply
                        2. Upvote
                        3. Downvote
                        4. Return to post
                        5. Quit""");

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String sanitizedInput = translateInput(option);

        if (sanitizedInput.equalsIgnoreCase("reply")) {
            //  TODO REPLY
        } else if (sanitizedInput.equalsIgnoreCase("upvote")) {
            //  TODO UPVOTE
        } else if (sanitizedInput.equalsIgnoreCase("downvote")) {
            //  TODO DOWNVOTE
        } else if (sanitizedInput.equalsIgnoreCase("return to post")) {
            postService.expandPost();
            changeState(State.ON_POST);
        } else if (sanitizedInput.equalsIgnoreCase("quit")) {
            changeState(State.QUIT);
        }
    }

    private void logout() {
        isLoggedIn  = false;
        user = null;
        System.out.println("You have been logged out.");

        changeState(State.MAIN_MENU);
    }

    private void quit() {
        if (isLoggedIn) {
            isLoggedIn  = false;
            user = null;
            System.out.println("You have been automatically logged out.");
        }

        System.out.println("See you soon!");
    }

    private void changeState(State state) {
        currentState = state;
    }

    private String translateInput(String input) {
        switch (currentState) {
            case MAIN_MENU:
                if (!isLoggedIn) {
                    return translateMenuInputNotLoggedIn(input);
                } else {
                    return translateMenuInputLoggedIn(input);
                }

            case ON_FEED:
                return translateOnFeedInput(input);

            case ON_POST:
                if (!isLoggedIn) {
                    return translateOnPostInputNotLoggedIn(input);
                } else {
                    return translatePostInputLoggedIn(input);
                }

            case ON_COMMENT:
                return translateOnCommentInput(input);
        }

        return input;
    }

    private String translateMenuInputNotLoggedIn(String input) {
        // 1. Login, 2. Register, 3. Logout, 4. Quit
        return switch (input) {
            case "1" -> "login";
            case "2" -> "register";
            case "3" -> "show feed";
            case "4" -> "quit";
            default -> "";
        };
    }

    private String translateMenuInputLoggedIn(String input) {
        //  1. Show feed, 2. Create post, 3. Logout, 4. Delete Account, 5. Quit
        return switch (input) {
            case "1" -> "show feed";
            case "2" -> "create post";
            case "3" -> "logout";
            case "4" -> "delete account";
            case "5" -> "quit";
            default -> "";
        };
    }

    private String translateOnFeedInput(String input) {
        //  1. Expand post, 2. Return to menu, 3. Quit
        return switch (input) {
            case "1" -> "expand post";
            case "2" -> "return to menu";
            case "3" -> "quit";
            default -> "";
        };
    }

    private String translateOnPostInputNotLoggedIn(String input) {
        //  1. Return to feed, 2. Quit
        return switch (input) {
            case "1" -> "return to feed";
            case "2" -> "quit";
            default -> "";
        };
    }

    private String translatePostInputLoggedIn(String input) {
        //  1. Comment, 2. Upvote, 3. Downvote, 4. Select comment
        //  5. Return to feed, 6. Logout, 7. Quit
        return switch (input) {
            case "1" -> "comment";
            case "2" -> "upvote";
            case "3" -> "downvote";
            case "4" -> "select comment";
            case "5" -> "return to feed";
            case "6" -> "logout";
            case "7" -> "quit";
            default -> "";
        };
    }

    private String translateOnCommentInput(String input) {
        //  1. Reply, 2. Upvote, 3. Downvote, 4. Return to post, 5. Quit
        return switch (input) {
            case "1" -> "reply";
            case "2" -> "upvote";
            case "3" -> "downvote";
            case "4" -> "return to post";
            case "5" -> "quit";
            default -> "";
        };
    }
}
