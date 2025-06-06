package com.TillDawn.Models;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class User {
    private String username = "";
    private String password = "";
    private String answer = "";
    private String avatarPath = "";
    private float score = 0;
    private int kills = 0;
    private float maxTimeLived = 0;
    private KeyManagment keyManagment = new KeyManagment();
    private boolean isGray = false;
    private boolean isControlDefault = true;
    private boolean reloadAuto = true;
    private float gameLength = 20f;
    private transient Game currentGame;
    private SFXManager sfxManager = new SFXManager();

    // No-args constructor for JSON serialization
    public User() {
        // All fields are already initialized with their default values
    }

    public User(String username, String password, String answer, String avatarPath) {
        this.username = username;
        this.password = password;
        this.answer = answer;
        this.avatarPath = avatarPath;
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

    public void modifyScore(float score) {
        this.score += score;
        App.getApp().setLoggedInUser(this);
    }

    public int getKills() {
        return kills;
    }

    public void modifyKills(int kills) {
        this.kills += kills;
        App.getApp().setLoggedInUser(this);
    }

    public float getMaxTimeLived() {
        return maxTimeLived;
    }

    public void setMaxTimeLived(float maxTimeLived) {
        if (maxTimeLived > this.maxTimeLived)
            this.maxTimeLived = maxTimeLived;
        App.getApp().setLoggedInUser(this);
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

    /**
     * Updates the user's stats after a game ends
     * @param gameScore The score achieved in the game
     * @param gameKills Number of kills in the game
     * @param gameTime Time survived in the game
     */
    public void updateStats(float gameScore, int gameKills, float gameTime) {
        // Update high score if the new score is higher
        this.score += gameScore;
        
        // Update max kills if the new kills count is higher
        this.kills += gameKills;
        
        // Update max time lived if the new time is higher
        if (gameTime > this.maxTimeLived) {
            this.maxTimeLived = gameTime;
        }
        
        // Save the updated stats
        App.getApp().setLoggedInUser(this);
    }
}
