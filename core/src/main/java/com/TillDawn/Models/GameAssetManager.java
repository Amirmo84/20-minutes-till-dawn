package com.TillDawn.Models;

import com.TillDawn.Models.Enums.Paths;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager manager;
    private final Skin skin = new Skin(Gdx.files.internal(Paths.SKIN.getPath()));
    private final Image image = new Image(new Texture(Gdx.files.internal(Paths.MAINMENUBACKIMAGE.getPath())));
    private final Pixmap pixmap = new Pixmap(Gdx.files.internal(Paths.CURSOR.getPath()));
    private final Cursor newCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
    private Music mainMusic = Gdx.audio.newMusic(Gdx.files.internal(Paths.MAINSONG.getPath()));
    private final Image settingsImage = new Image(new Texture(Gdx.files.internal(Paths.SETTINGSBACKGROUND.getPath())));
    private final Texture idel0 = new Texture(Paths.IDLE0.getPath() + "Idle_0.png");
    private final Texture idel1 = new Texture(Paths.IDLE0.getPath() + "Idle_1.png");
    private final Texture idel2 = new Texture(Paths.IDLE0.getPath() + "Idle_2.png");
    private final Texture idel3 = new Texture(Paths.IDLE0.getPath() + "Idle_3.png");
    private final Texture idel4 = new Texture(Paths.IDLE0.getPath() + "Idle_4.png");
    private final Texture idel5 = new Texture(Paths.IDLE0.getPath() + "Idle_5.png");
    private final Animation<Texture> idle_frames = new Animation<>(0.1f, idel0, idel1, idel2, idel3, idel4, idel5);

    private GameAssetManager() {}

    public static GameAssetManager getManager() {
        if (manager == null)
            manager = new GameAssetManager();
        return manager;
    }

    public Skin getSkin() {
        return skin;
    }

    public Image getImage() {
        return image;
    }

    public Cursor getNewCursor() {
        return newCursor;
    }

    public Music getMainMusic() {
        return mainMusic;
    }

    public Image getSettingsImage() {
        return settingsImage;
    }

    public Animation<Texture> getIdle_frames() {
        return idle_frames;
    }

    public Texture getIdel0() {
        return idel0;
    }
}
