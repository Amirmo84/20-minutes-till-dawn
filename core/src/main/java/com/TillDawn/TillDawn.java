package com.TillDawn;

import com.TillDawn.Views.MainMenu;
import com.TillDawn.Views.SignUpMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class TillDawn extends Game {
    private static TillDawn tillDawn;
    private SpriteBatch batch;

    private TillDawn() {}

    public static TillDawn getTillDawn() {
        if (tillDawn == null)
            tillDawn = new TillDawn();
        return tillDawn;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        getTillDawn().setScreen(new MainMenu());
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
