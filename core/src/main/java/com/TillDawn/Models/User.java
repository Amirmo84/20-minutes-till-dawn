package com.TillDawn.Models;


import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class User {
    private String username;
    private String password;
    private final String answer;
    private String avatarPath;

    public User(String username, String password, String answer, String avatarPath) {
        this.username = username;
        this.password = password;
        this.answer = answer;
        this.avatarPath = avatarPath;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAnswer() {
        return answer;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static User getUserByUserName(String username) {
        for (User user : App.getApp().getUsers()){
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }
}
