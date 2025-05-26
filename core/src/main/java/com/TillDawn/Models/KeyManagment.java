package com.TillDawn.Models;

import com.badlogic.gdx.Input;

public class KeyManagment {
    private int reloadButton = Input.Keys.R;
    private int autoAimButton = Input.Keys.SPACE;
    private int up = Input.Keys.W;
    private int down = Input.Keys.S;
    private int left = Input.Keys.A;
    private int right = Input.Keys.D;

    public int getReloadButton() {
        return reloadButton;
    }

    public void setReloadButton(int reloadButton) {
        this.reloadButton = reloadButton;
    }

    public int getAutoAimButton() {
        return autoAimButton;
    }

    public void setAutoAimButton(int autoAimButton) {
        this.autoAimButton = autoAimButton;
    }

    public void changeMovement(){
        if (up == Input.Keys.W){
            up = Input.Keys.UP;
            down = Input.Keys.DOWN;
            left = Input.Keys.LEFT;
            right = Input.Keys.RIGHT;
        } else {
            up = Input.Keys.W;
            down = Input.Keys.S;
            left = Input.Keys.A;
            right = Input.Keys.D;
        }
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
}
