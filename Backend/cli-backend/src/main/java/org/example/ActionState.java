package org.example;

import org.example.entities.Post;
import org.example.entities.User;
import org.example.services.CommentService;
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
    private final static CommentService commentService = new CommentService();

    private ActionState() {
        this.isLoggedIn = false;
        this.currentState = State.MAIN_MENU;
        this.user = null;
    }

    public static ActionState getInstance()
    {
        if (actionState == null)
            actionState = new ActionState();

        return actionState;
    }

    boolean executeAction() {
        Scanner scan = new Scanner(System.in);
        String option;
        String sanitizedInput;

        switch (currentState) {
            case MAIN_MENU:
                if (!isLoggedIn) {
                    mainMenuNotLoggedIn();
                } else {
                    mainMenuIsLoggedIn();
                }
                break;

            case ON_FEED:
                System.out.println("1. Expand post\n2. Return to menu\n3. Quit");

                option = scan.nextLine();
                sanitizedInput = sanitizeInput(option);

                if (sanitizedInput.equalsIgnoreCase("expand post")) {
                    postService.expandPost();
                    changeState(State.ON_POST);
                } else if (sanitizedInput.equalsIgnoreCase("return to menu")) {
                    changeState(State.MAIN_MENU);
                } else if (sanitizedInput.equalsIgnoreCase("quit")) {
                    changeState(State.QUIT);
                }

                break;

            case ON_POST:
                if (!isLoggedIn) {
                    System.out.println("""
                        1. Return to feed
                        2. Quit""");

                    option = scan.nextLine();
                    sanitizedInput = sanitizeInput(option);

                    if (sanitizedInput.equalsIgnoreCase("return to feed")) {
                        postService.showFeed();
                        changeState(State.ON_FEED);
                    } else if (sanitizedInput.equalsIgnoreCase("quit")) {
                        changeState(State.QUIT);
                    }
                } else {
                    System.out.println("""
                        1. Comment
                        2. Upvote
                        3. Downvote
                        4. Select comment
                        5. Return to feed
                        6. Quit""");

                    option = scan.nextLine();
                    sanitizedInput = sanitizeInput(option);

                    if (sanitizedInput.equalsIgnoreCase("comment")) {
                        commentService.addComment(user, post);
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

                break;

            case ON_COMMENT:
                //  This state can only be accessed if the user is logged in
                System.out.println("""
                        1. Reply
                        2. Upvote
                        3. Downvote
                        4. Return to post
                        5. Quit""");

                option = scan.nextLine();
                sanitizedInput = sanitizeInput(option);

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
                break;

            case LOGOUT:
                isLoggedIn  = false;
                user = null;
                System.out.println("You have been logged out.");

                changeState(State.MAIN_MENU);

                break;

            case QUIT:
                if (isLoggedIn) {
                    isLoggedIn  = false;
                    user = null;
                    System.out.println("You have been automatically logged out.");
                }

                System.out.println("See you soon!");
                return false;

            default:
                break;
        }

        return true;
    }

    private void mainMenuNotLoggedIn() {
        System.out.println("1. Login\n2. Register\n3. Show feed\n4. Quit" );

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String sanitizedInput = sanitizeInput(option);

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

    private void mainMenuIsLoggedIn() {
        System.out.println("""
                            1. Show feed
                            2. Create post
                            3. Logout
                            4. Delete Account
                            5. Quit""");

        Scanner scan = new Scanner(System.in);
        String option = scan.nextLine();
        String sanitizedInput = sanitizeInput(option);

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
    
    private void changeState(State state) {
        currentState = state;
    }

    private String sanitizeInput(String input) {
        switch (currentState) {
            case MAIN_MENU:
                if (!isLoggedIn) {
                    // 1. Login, 2. Register, 3. Logout, 4. Quit
                    switch (input) {
                        case "1":
                            return "login";

                        case "2":
                            return "register";

                        case "3":
                            return "show feed";

                        case "4":
                            return "quit";
                    }
                } else {
                    //  1. Show feed, 2. Create post, 3. Logout, 4. Delete Account, 5. Quit
                    switch (input) {
                        case "1":
                            return "show feed";

                        case "2":
                            return "create post";

                        case "3":
                            return "logout";

                        case "4":
                            return "delete account";

                        case "5":
                            return "quit";
                    }
                }

            case ON_FEED:
                //  1. Expand post, 2. Return to menu, 3. Quit
                switch (input) {
                    case "1":
                        return "expand post";

                    case "2":
                        return "return to menu";

                    case "3":
                        return "quit";
                }

            case ON_POST:
                if (!isLoggedIn) {
                    //  1. Return to feed, 2. Quit
                    switch (input) {
                        case "1":
                            return "return to feed";

                        case "2":
                            return "quit";
                    }
                } else {
                    //  1. Comment, 2. Upvote, 3. Downvote, 4. Select comment
                    //  5. Return to feed, 6. Logout, 7. Quit
                    switch (input) {
                        case "1":
                            return "comment";

                        case "2":
                            return "upvote";

                        case "3":
                            return "downvote";

                        case "4":
                            return "select comment";

                        case "5":
                            return "return to feed";

                        case "6":
                            return "logout";

                        case "7":
                            return "quit";
                    }
                }

            case ON_COMMENT:
                //  1. Reply, 2. Upvote, 3. Downvote, 4. Return to post, 5. Quit
                switch (input) {
                    case "1":
                        return "reply";

                    case "2":
                        return "upvote";

                    case "3":
                        return "downvote";

                    case "4":
                        return "return to post";

                    case "5":
                        return "quit";
                }
        }

        return input;
    }

}
