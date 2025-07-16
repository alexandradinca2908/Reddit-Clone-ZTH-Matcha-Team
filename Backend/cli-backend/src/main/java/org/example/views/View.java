package org.example.views;

import java.util.HashMap;

public class View {
    private ViewID viewID;
    private HashMap<ViewID, View> menu;
    private HashMap<ViewID, View> nextViews;
    private ViewManager viewManager;

    protected ViewID getViewID() {
        return viewID;
    }

    public HashMap<ViewID, View> getMenu() {
        return menu;
    }

    public HashMap<ViewID, View> getNextViews() {
        return nextViews;
    }

    void displayMenu() {}
    void activateMenuOption() {}
}
