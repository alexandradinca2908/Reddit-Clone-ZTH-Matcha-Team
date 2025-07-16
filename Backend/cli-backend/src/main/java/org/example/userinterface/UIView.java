package org.example.userinterface;

import org.example.views.MenuOption;
import org.example.views.View;
import org.example.views.ViewID;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UIView {
    private static UIView instance;

    private UIView() {
    }

    public static UIView getInstance() {
        if (instance == null) {
            instance = new UIView();
        }

        return instance;
    }

    public void renderMenu(ArrayList<MenuOption> menu) {
        int counter = 0;

        for (MenuOption option : menu) {
            System.out.println(counter++ + " " + option);
        }
    }
}
