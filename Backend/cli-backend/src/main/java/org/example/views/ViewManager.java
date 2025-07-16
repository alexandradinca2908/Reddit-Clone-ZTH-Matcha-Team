package org.example.views;

import org.example.loggerobjects.Logger;

import java.util.HashMap;

public class ViewManager {
    public HashMap<ViewID, View> views;
    public ViewID currentViewID;
    public static ViewManager viewManager;

    private ViewManager() {
        this.views = new HashMap<>();
        currentViewID = ViewID.MAIN_MENU;
    }

    public static ViewManager getInstance() {
        if (viewManager == null)
            viewManager = new ViewManager();

        return viewManager;
    }

    private void addView(View view) {
        this.views.put(view.getViewID(), view);
    }

    public void addMultipleViews(View... views) {
        for (View view : views) {
            this.views.put(view.getViewID(), view);
        }
    }

    void switchToNextView(ViewID viewID) {
        View currentViewObject = views.get(currentViewID);

        //  Improbable error: manager doesn't have a reference to current view
        if (currentViewObject == null) {
            Logger.fatal("No view found for current view " + currentViewID);
            return;
        }

        //  Likely error: current view doesn't have access to requested next view
        if (currentViewObject.getNextViews().get(viewID) == null) {
            Logger.error("Current view" + currentViewID + " doesn't have access to next view " + viewID);
            return;
        }

        //  Switch to next view
        currentViewID = viewID;
    }
}
