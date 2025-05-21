package com.TillDawn.Models;

import java.util.ArrayList;

public class App {
    private static App app;
    private ArrayList<User> users = new ArrayList<>();
    private User loggedInUser;
    private User tempUser;

    private App() {}

    public static App getApp() {
        if (app == null)
            app = new App();
        return app;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getTempUser() {
        return tempUser;
    }

    public void setTempUser(User tempUser) {
        this.tempUser = tempUser;
    }
}
