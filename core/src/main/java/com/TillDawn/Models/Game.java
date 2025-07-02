package com.TillDawn.Models;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class Game {
    private Player player;
    private boolean isOver = false;
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Tentacle> tentacles = new ArrayList<>();
    private ArrayList<EyeBat> eyeBats = new ArrayList<>();
    private ArrayList<XpDrop> xpDrops = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private float timeGone = 0;
    private float maxTime = 20 * 60; // both in seconds
    private float backWidth;
    private float backHeight;
    private ElderBoss elderBoss = null;

    public Game() {
        initializeTrees(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 60);
    }

    public void updateItemsTime(float time){
        for (Tree tree : this.getTrees()){
            tree.modifyStateTime(time);
        }
        for (Tentacle tentacle : this.getTentacles()){
            tentacle.modifyStateTime(time);
        }
        for (EyeBat eyeBat : this.getEyeBats()){
            eyeBat.modifyStateTime(time);
        }
        for (Explosion explosion : this.getExplosions()){
            explosion.modifyStateTime(time);
        }
        if (elderBoss != null){
            elderBoss.modifyStateTime(time);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public void initializeTrees(float xBound, float yBound, int count) {
        for (int i = 0; i < count; i++) {
            float xPos = (float) (Math.random() * (xBound * 4));
            float yPos = (float) (Math.random() * (yBound * 4));
            Tree tree = new Tree(xPos, yPos);
            trees.add(tree);
        }
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public void setTrees(ArrayList<Tree> trees) {
        this.trees = trees;
    }

    public ArrayList<XpDrop> getXpDrops() {
        return xpDrops;
    }

    public void setXpDrops(ArrayList<XpDrop> xpDrops) {
        this.xpDrops = xpDrops;
    }

    public float getTimeGone() {
        return timeGone;
    }

    public void setTimeGone(float timeGone) {
        this.timeGone = timeGone;
    }

    public void modifyTimeGone(float timeGone){
        this.timeGone += timeGone;
    }

    public float getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(float maxTime) {
        this.maxTime = maxTime * 60;
    }

    public float getBackWidth() {
        return backWidth;
    }

    public void setBackWidth(float backWidth) {
        this.backWidth = backWidth;
    }

    public float getBackHeight() {
        return backHeight;
    }

    public void setBackHeight(float backHeight) {
        this.backHeight = backHeight;
    }

    public ArrayList<Tentacle> getTentacles() {
        return tentacles;
    }

    public void setTentacles(ArrayList<Tentacle> tentacles) {
        this.tentacles = tentacles;
    }

    public ArrayList<EyeBat> getEyeBats() {
        return eyeBats;
    }

    public void setEyeBats(ArrayList<EyeBat> eyeBats) {
        this.eyeBats = eyeBats;
    }

    public ArrayList<Explosion> getExplosions() {
        return explosions;
    }

    public void setExplosions(ArrayList<Explosion> explosions) {
        this.explosions = explosions;
    }

    public ElderBoss getElderBoss() {
        return elderBoss;
    }

    public void setElderBoss(ElderBoss elderBoss) {
        this.elderBoss = elderBoss;
    }
}
