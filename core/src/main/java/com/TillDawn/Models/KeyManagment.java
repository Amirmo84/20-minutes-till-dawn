package com.TillDawn.Models;

import com.badlogic.gdx.Input;

public class KeyManagment {
    private int reloadButton = Input.Keys.R;
    private int autoAimButton = Input.Keys.SPACE;

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
}
