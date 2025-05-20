package com.TillDawn.Models;

import java.util.ArrayList;

public class App {
    private static App app;
    private ArrayList<User> users = new ArrayList<>();

    private App() {}

    public static App getApp() {
        if (app == null)
            app = new App();
        return app;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
