package com.TillDawn.Models;

import com.badlogic.gdx.audio.Sound;

public class SFXManager {
    private boolean sfxEnabled = true;

    public void setSfxEnabled(boolean enabled) {
        sfxEnabled = enabled;
    }

    public boolean isSfxEnabled() {
        return sfxEnabled;
    }

    public void play(Sound sound) {
        if (sfxEnabled) {
            sound.play();
        }
    }

    public void play(Sound sound, float volume) {
        if (sfxEnabled) {
            sound.play(volume);
        }
    }
}
