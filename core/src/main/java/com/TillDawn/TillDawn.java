package com.TillDawn;

import com.TillDawn.Models.Enums.Paths;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.MusicManager;
import com.TillDawn.Models.SFXManager;
import com.TillDawn.Views.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class TillDawn extends Game {
    private static TillDawn tillDawn;
    private SpriteBatch batch;
    private MusicManager musicManager = new MusicManager();
    private SFXManager sfxManager = new SFXManager();
    private ShaderProgram grayscaleShader;
    private boolean isGray = false;

    private TillDawn() {}

    public static TillDawn getTillDawn() {
        if (tillDawn == null)
            tillDawn = new TillDawn();
        return tillDawn;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        ShaderProgram.pedantic = false;
         grayscaleShader = new ShaderProgram(
                Gdx.files.internal( Paths.GRAYSCALE.getPath() + "default.vert"),
                Gdx.files.internal(Paths.GRAYSCALE.getPath() + "greyScale.frag")
        );
        getTillDawn().setScreen(new SettingsView());
        musicManager.play(GameAssetManager.getManager().getMainMusic(), true);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public SFXManager getSfxManager() {
        return sfxManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public ShaderProgram getGrayscaleShader() {
        return grayscaleShader;
    }

    public boolean isGray() {
        return isGray;
    }

    public void setGray(boolean gray) {
        isGray = gray;
    }
}
