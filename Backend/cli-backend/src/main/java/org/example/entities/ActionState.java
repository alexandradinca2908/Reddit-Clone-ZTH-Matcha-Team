package org.example.entities;

import org.example.services.PostService;
import org.example.services.UserService;

import java.util.Scanner;

import static org.example.textprocessors.InputTranslator.translateInput;

public class ActionState {
    private static ActionState actionState;
    private final static UserService userService = UserService.getInstance();
    private final static PostService postService = new PostService();
    private final static Scanner scanner = new Scanner(System.in);
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
        String sanitizedInput = translateInput(option, currentState, isLoggedIn);

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
        String sanitizedInput = translateInput(option, currentState, isLoggedIn);

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
        String sanitizedInput = translateInput(option, currentState, isLoggedIn);

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
        String sanitizedInput = translateInput(option, currentState, isLoggedIn);

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
        String sanitizedInput = translateInput(option, currentState, isLoggedIn);

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
        String sanitizedInput = translateInput(option, currentState, isLoggedIn);

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


}
