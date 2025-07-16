package org.example.views;

import org.example.loggerobjects.Logger;
import org.example.userinterface.UIView;
import org.example.views.commandexecution.IMenuCommand;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class View {
    private ViewID viewID;
    private HashMap<MenuOption, Availability> menu;
    private HashMap<MenuOption, IMenuCommand> commands;
    private HashMap<ViewID, View> nextViews;
    private final ViewManager viewManager = ViewManager.getInstance();
    private final UIView uiView = UIView.getInstance();

    protected ViewID getViewID() {
        return viewID;
    }

    protected void setViewID(ViewID viewID) {
        this.viewID = viewID;
    }

    protected HashMap<MenuOption, Availability> getMenu() {
        return menu;
    }

    protected void setMenu(HashMap<MenuOption, Availability> menu) {
        this.menu = menu;
    }

    protected HashMap<ViewID, View> getNextViews() {
        return nextViews;
    }

    protected void setNextViews(HashMap<ViewID, View> nextViews) {
        this.nextViews = nextViews;
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    public void displayMenu() {
        uiView.renderMenu(menu, viewManager.isLoggedIn);
    }

    public boolean activateMenuOption(MenuOption menuOption) {
        //  Check for exceptions
        if (menuOption == MenuOption.UNKNOWN_COMMAND) {
            uiView.unknownCommand();
            return true;
        }

        if (!menu.containsKey(menuOption)) {
            Logger.fatal("In " + viewID + " it is possible to access " + menuOption
                    + " but it should NOT be available");
            return true;
        }

        //  Do command
        return commands.get(menuOption).execute(this);
    }
}
