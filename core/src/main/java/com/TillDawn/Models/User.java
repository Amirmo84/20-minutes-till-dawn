package com.TillDawn.Models;


public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static User getUserByUserName(String username) {
        for (User user : App.getApp().getUsers()){
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }
}
