package org.example.views;

import org.example.userinterface.UIView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class View {
    private ViewID viewID;
    private ArrayList<MenuOption> menu;
    private HashMap<ViewID, View> nextViews;
    private final ViewManager viewManager = ViewManager.getInstance();
    private final UIView uiView = UIView.getInstance();

    public ViewID getViewID() {
        return viewID;
    }

    public void setViewID(ViewID viewID) {
        this.viewID = viewID;
    }

    public ArrayList<MenuOption> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuOption> menu) {
        this.menu = menu;
    }

    public HashMap<ViewID, View> getNextViews() {
        return nextViews;
    }

    public void setNextViews(HashMap<ViewID, View> nextViews) {
        this.nextViews = nextViews;
    }

    void displayMenu() {
        uiView.renderMenu(menu);
    }

    void activateMenuOption() {}
}
