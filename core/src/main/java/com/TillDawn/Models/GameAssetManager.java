package com.TillDawn.Models;

import com.TillDawn.Models.Enums.Paths;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager manager;
    private final Skin skin = new Skin(Gdx.files.internal(Paths.SKIN.getPath()));

    private GameAssetManager() {}

    public static GameAssetManager getManager() {
        if (manager == null)
            manager = new GameAssetManager();
        return manager;
    }

    public Skin getSkin() {
        return skin;
    }
}
