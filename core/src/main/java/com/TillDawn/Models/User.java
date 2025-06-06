package com.TillDawn.Models;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class User {
    private String username;
    private String password;
    private String answer;
    private String avatarPath;
    private float score;
    private int kills;
    private float maxTimeLived;
    private KeyManagment keyManagment;
    private boolean isGray;
    private boolean isControlDefault = true;
    private boolean reloadAuto = true;
    private float gameLength = 20f;
    private Game currentGame;
    private SFXManager sfxManager;

    // No-args constructor for JSON serialization
    public User() {
        this.username = "";
        this.password = "";
        this.answer = "";
        this.avatarPath = "";
        this.score = 0;
        this.kills = 0;
        this.maxTimeLived = 0;
        this.keyManagment = new KeyManagment();
        this.isGray = false;
        this.sfxManager = new SFXManager();
    }

    public User(String username, String password, String answer, String avatarPath) {
        this.username = username;
        this.password = password;
        this.answer = answer;
        this.avatarPath = avatarPath;
        this.score = 0;
        this.kills = 0;
        this.maxTimeLived = 0;
        this.keyManagment = new KeyManagment();
        this.isGray = false;
        this.sfxManager = new SFXManager();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
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

    public KeyManagment getKeyManagement() {
        return keyManagment;
    }

    public void setKeyManagement(KeyManagment keyManagment) {
        this.keyManagment = keyManagment;
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

    public void setSfxManager(SFXManager sfxManager) {
        this.sfxManager = sfxManager;
    }

    public String getFormattedTime() {
        int minutes = (int) (maxTimeLived / 60);
        int seconds = (int) (maxTimeLived % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static User getUserByUserName(String username) {
        for (User user : App.getApp().getUsers()){
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }
}
