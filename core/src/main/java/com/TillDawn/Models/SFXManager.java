package com.TillDawn.Models;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;

public class SFXManager extends InputAdapter {
    private boolean sfxEnabled = true;
    private transient Sound sound;

    public void setSfxEnabled(boolean enabled) {
        sfxEnabled = enabled;
    }

    public boolean isSfxEnabled() {
        return sfxEnabled;
    }

    public void play() {
        if (sfxEnabled) {
            sound.play();
        }
    }

    public void play(float volume) {
        if (sfxEnabled) {
            sound.play(volume);
        }
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (isSfxEnabled())
            sound.play();
        return false;
    }
}
