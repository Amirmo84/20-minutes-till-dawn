package com.TillDawn.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import java.util.ArrayList;

public class App {
    private static App app;
    private ArrayList<User> users = new ArrayList<>();
    private User loggedInUser;
    private User tempUser;
    private final Json json;
    private static final String USERS_FILE = "Data/users.json";

    private App() {
        json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        loadUsers();
        loadRememberedUser();
    }

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
        // Only clear remembered flag if explicitly logging out
        if (loggedInUser == null && this.loggedInUser != null) {
            this.loggedInUser.setRemembered(false);
        }
        
        this.loggedInUser = loggedInUser;
        saveUsers(); // Save whenever user changes
    }

    public User getTempUser() {
        return tempUser;
    }

    public void setTempUser(User tempUser) {
        this.tempUser = tempUser;
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers(); // Save whenever a new user is added
    }

    public void removeUser(User user) {
        users.remove(user);
        saveUsers(); // Save whenever a user is removed
    }

    private void saveUsers() {
        try {
            FileHandle file = Gdx.files.local(USERS_FILE);
            String jsonStr = json.prettyPrint(users);
            file.writeString(jsonStr, false);
            System.out.println("Users saved successfully");
        } catch (Exception e) {
            System.err.println("Error saving users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try {
            FileHandle file = Gdx.files.local(USERS_FILE);
            if (file.exists()) {
                String jsonStr = file.readString();
                users = json.fromJson(ArrayList.class, User.class, jsonStr);
                System.out.println("Users loaded successfully: " + users.size() + " users");
            }
        } catch (Exception e) {
            System.err.println("Error loading users: " + e.getMessage());
            e.printStackTrace();
            users = new ArrayList<>(); // Ensure we have an empty list if loading fails
        }
    }

    private void loadRememberedUser() {
        for (User user : users) {
            if (user.isRemembered()) {
                loggedInUser = user;
                break;
            }
        }
    }

    public void logout() {
        if (loggedInUser != null) {
            loggedInUser.setRemembered(false);
            saveUsers();
            loggedInUser = null;
        }
    }
}
