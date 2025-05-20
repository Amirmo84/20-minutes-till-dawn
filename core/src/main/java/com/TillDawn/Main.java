package com.TillDawn;

import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Views.SignUpMenu;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private SpriteBatch batch;

    private Main() {}

    public static Main getMain() {
        if (main == null)
            main = new Main();
        return main;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        getMain().setScreen(new SignUpMenu(GameAssetManager.getManager().getSkin()));
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
}
