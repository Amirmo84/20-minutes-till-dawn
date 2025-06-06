package com.TillDawn;

import com.TillDawn.Controllers.ControllersManager;
import com.TillDawn.Models.*;
import com.TillDawn.Models.Enums.Paths;
import com.TillDawn.Views.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class TillDawn extends Game {
    private static TillDawn tillDawn;
    private SpriteBatch batch;
    private MusicManager musicManager = new MusicManager();
    private ShaderProgram grayscaleShader;
    private ShaderProgram lightShader;

    public TillDawn() {
        tillDawn = this;
    }

    public static TillDawn getTillDawn() {
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
         lightShader = new ShaderProgram(
                 Gdx.files.internal(Paths.GRAYSCALE.getPath() + "vertex.glsl"),
                 Gdx.files.internal(Paths.GRAYSCALE.getPath() + "f.glsl")
         );
        getTillDawn().setScreen(new MainMenu());
        musicManager.setPath(Paths.MAINSONG.getPath());
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

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public ShaderProgram getGrayscaleShader() {
        return grayscaleShader;
    }

    public ShaderProgram getLightShader() {
        return lightShader;
    }

    public void addClickSoundToButton(TextButton button, Runnable onClick) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAssetManager.getManager().getClickSound().play();
                onClick.run();
            }
        });
    }

    public void handleFileDrops(String[] files) {
        if (getScreen() instanceof ChangeAvatarMenu) {
            ((ChangeAvatarMenu) getScreen()).handleFileDrops(files);
        }
    }
}
