package com.TillDawn.Models;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;

public class MusicManager {
    private Music currentMusic;
    private float volume = 1.0f;
    private boolean enabled = true;

    public void play(Music music, boolean loop) {
        stop();
        currentMusic = music;
        currentMusic.setLooping(loop);
        currentMusic.setVolume(enabled ? volume : 0f);
        currentMusic.play();
    }

    public void setVolume(float volume) {
        this.volume = MathUtils.clamp(volume, 0f, 1f);
        if (currentMusic != null && enabled) {
            currentMusic.setVolume(this.volume);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (currentMusic != null) {
            currentMusic.setVolume(enabled ? volume : 0f);
        }
    }

    public float getVolume() {
        return volume;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void stop() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic = null;
        }
    }
}
