package com.TillDawn.Models;


import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class User {
    private String username;
    private String password;
    private final String answer;
    private String avatarPath;
    private KeyManagment keyManagment = new KeyManagment();
    private boolean isGray = false;
    private boolean isControlDefault = true;
    private boolean reloadAuto = true;
    private float gameLength = 20f;
    private Game currentGame;
    private SFXManager sfxManager = new SFXManager();
    private float score;
    private int kills;
    private float maxTimeLived = 0f;

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

    public KeyManagment getKeyManagment() {
        return keyManagment;
    }

    public static User getUserByUserName(String username) {
        for (User user : App.getApp().getUsers()){
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public boolean isGray() {
        return isGray;
    }

    public void setGray(boolean gray) {
        isGray = gray;
    }

    public boolean isControlDefault() {
        return isControlDefault;
    }

    public void setControlDefault(boolean controlDefault) {
        isControlDefault = controlDefault;
    }

    public boolean isReloadAuto() {
        return reloadAuto;
    }

    public void setReloadAuto(boolean reloadAuto) {
        this.reloadAuto = reloadAuto;
    }

    public float getGameLength() {
        return gameLength;
    }

    public void setGameLength(float gameLength) {
        this.gameLength = gameLength;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public SFXManager getSfxManager() {
        return sfxManager;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public float getMaxTimeLived() {
        return maxTimeLived;
    }

    public void setMaxTimeLived(float maxTimeLived) {
        this.maxTimeLived = maxTimeLived;
    }

    public String getFormattedTime() {
        int minutes = (int)(this.maxTimeLived / 60);
        int seconds = (int)(this.getMaxTimeLived() % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
