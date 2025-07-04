package org.example;

import org.example.services.PostService;
import org.example.services.UserService;

import java.util.Scanner;

public class ActionState {
    public enum State {
        NOT_LOGGED_IN,
        LOGGED_IN,
        ON_FEED,
        ON_POST,
        ON_COMMENT,
        LOGOUT,
        QUIT
    }

    private final static UserService userService = UserService.getInstance();
    private final static PostService postService = new PostService();
    State currentState = State.NOT_LOGGED_IN;
    User user;

    public boolean executeAction() {
        Scanner scan = new Scanner(System.in);
        String option;
        String sanitizedInput;

        switch (currentState) {
            case NOT_LOGGED_IN:
                System.out.println("1. Login\n2. Register\n3. Show feed\n4. Quit" );

                option = scan.nextLine();
                sanitizedInput = sanitizeInput(option);

                if (sanitizedInput.equalsIgnoreCase("login")) {
                    user = userService.userLoginCLI();

                    //  Display login menu only if action was successful
                    if (user != null) {
                        changeState(State.LOGGED_IN);
                    }
                } else if (sanitizedInput.equalsIgnoreCase("register")) {
                    user = userService.userRegisterCLI();
                    changeState(State.LOGGED_IN);
                } else if (sanitizedInput.equalsIgnoreCase("show feed")) {
                    postService.showFeed();
                    changeState(State.ON_FEED);
                } else if (sanitizedInput.equalsIgnoreCase("quit")) {
                    changeState(State.QUIT);
                }

                break;

            case LOGGED_IN:
                System.out.println("1. Show feed\n2. Create post\n3. Add comment\n4. Logout\n5. Delete Account\n6. Quit");

                option = scan.nextLine();
                sanitizedInput = sanitizeInput(option);

                if (sanitizedInput.equalsIgnoreCase("show feed")) {
                    postService.showFeed();
                    changeState(State.ON_FEED);
                }  else if (sanitizedInput.equalsIgnoreCase("create post")) {
                    postService.addPost(user.getUsername());
                } else if (sanitizedInput.equalsIgnoreCase("logout")) {
                    user = null;
                    changeState(State.LOGOUT);
                } else if (sanitizedInput.equalsIgnoreCase("delete account")) {
                    userService.userDeleteCLI(this.user);
                    changeState(State.NOT_LOGGED_IN);
                } else if (sanitizedInput.equalsIgnoreCase("quit")) {
                    changeState(State.QUIT);
                }

                break;

            case ON_FEED:
                System.out.println("1. Expand post\n2. Logout\n3. Quit");

                option = scan.nextLine();
                sanitizedInput = sanitizeInput(option);

                if (sanitizedInput.equalsIgnoreCase("expand post")) {
                    postService.expandPost();
                    changeState(State.ON_POST);
                } else if (sanitizedInput.equalsIgnoreCase("logout")) {
                    user = null;
                    changeState(State.LOGOUT);
                } else if (sanitizedInput.equalsIgnoreCase("quit")) {
                    changeState(State.QUIT);
                }
                break;

            case ON_POST:
                System.out.println("1. Comment\n2. Upvote\n 3. Downvote\n4. Select comment\n5. Return to feed\n" +
                        "6. Logout\n7. Quit");

                option = scan.nextLine();
                sanitizedInput = sanitizeInput(option);

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
                } else if (sanitizedInput.equalsIgnoreCase("logout")) {
                    user = null;
                    changeState(State.LOGOUT);
                } else if (sanitizedInput.equalsIgnoreCase("quit")) {
                    changeState(State.QUIT);
                }
                break;

            case ON_COMMENT:
                //  TODO ON_COMMENT STATE
                break;

            case LOGOUT:
                System.out.println("You have been logged out.");
                changeState(State.NOT_LOGGED_IN);
                break;

            case QUIT:
                System.out.println("See you soon!");
                return false;

            default:
                break;
        }

        return true;
    }

    private void changeState(State state) {
        currentState = state;
    }

    private String sanitizeInput(String input) {
        switch (currentState) {
            case NOT_LOGGED_IN:
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

            case LOGGED_IN:
                //  1. Show feed, 2. Create post, 3. Add comment, 4. Logout, 5. Quit
                switch (input) {
                    case "1":
                        return "show feed";

                    case "2":
                        return "create post";

                    case "3":
                        return "logout";

                    case "4":
                        return "quit";
                }

            case ON_FEED:
                //  1. Expand post, 2. Logout, 3. Quit
                switch (input) {
                    case "1":
                        return "expand post";

                    case "2":
                        return "logout";

                    case "3":
                        return "quit";
                }

            case ON_POST:
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

        return input;
    }

}
