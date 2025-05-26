package com.TillDawn.Models;

import com.TillDawn.Models.Enums.Paths;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager manager;
    private final Skin skin = new Skin(Gdx.files.internal(Paths.SKIN.getPath()));
    private final Image image = new Image(new Texture(Gdx.files.internal(Paths.MAINMENUBACKIMAGE.getPath())));
    private final Pixmap pixmap = new Pixmap(Gdx.files.internal(Paths.CURSOR.getPath()));
    private final Cursor newCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
    private Music mainMusic = Gdx.audio.newMusic(Gdx.files.internal(Paths.MAINSONG.getPath()));
    private final Texture gameBackGround = new Texture(Gdx.files.internal(Paths.GAMEBACKGROUND.getPath()));
    private final Texture weapon = new Texture(Gdx.files.internal(Paths.WEAPON.getPath()));
    private final Image settingsImage = new Image(new Texture(Gdx.files.internal(Paths.SETTINGSBACKGROUND.getPath())));
    private final TextureRegion idel0 = new TextureRegion(new Texture(Paths.IDLE0.getPath() + "Idle_0.png"));
    private final TextureRegion idel1 = new TextureRegion(new Texture(Paths.IDLE0.getPath() + "Idle_1.png"));
    private final TextureRegion idel2 = new TextureRegion(new Texture(Paths.IDLE0.getPath() + "Idle_2.png"));
    private final TextureRegion idel3 = new TextureRegion(new Texture(Paths.IDLE0.getPath() + "Idle_3.png"));
    private final TextureRegion idel4 = new TextureRegion(new Texture(Paths.IDLE0.getPath() + "Idle_4.png"));
    private final TextureRegion idel5 = new TextureRegion(new Texture(Paths.IDLE0.getPath() + "Idle_5.png"));
    private final Texture bullet = new Texture(Paths.BULLET.getPath());
    private final Animation<TextureRegion> idle_frames = new Animation<>(0.1f, idel0, idel1, idel2, idel3, idel4, idel5);
    private final Animation<TextureRegion> treeAnimation = new Animation<>(0.4f, getTreeFrames());
    private final Animation<TextureRegion> tentacleAnimation = new Animation<>(0.3f, getTentacleFrames());
    private final Texture XpDrop = new Texture(Paths.DROP.getPath());
    private final Texture damage = new Texture(Paths.DAMAGE.getPath());
    private final Animation<TextureRegion> eyeBatAnimation = new Animation<>(0.2f, getEyeBatFrames());
    private final Texture oppBullet = new Texture(Paths.OPPBULLET.getPath());
    private final Sound shootSound = Gdx.audio.newSound(Gdx.files.internal(Paths.SHOOT.getPath()));
    private final Animation<TextureRegion> explosionAnimation = new Animation<>(0.1f, getExplosionFrames());
    private final Sound explosionSound = Gdx.audio.newSound(Gdx.files.internal(Paths.EXPLOSIONSOUND.getPath()));
    private final Animation<TextureRegion> reloadAnimation = new Animation<>(0.1f, getReloadFrames());
    private final Sound clickSound = Gdx.audio.newSound(Gdx.files.internal(Paths.CLICK.getPath()));
    private final Animation<TextureRegion> bossAnimation = new Animation<>(0.2f, getBossFrames());
    private final Sound youWinSound = Gdx.audio.newSound(Gdx.files.internal(Paths.WIN.getPath()));
    private final Sound youLoseSound = Gdx.audio.newSound(Gdx.files.internal(Paths.LOSE.getPath()));
    private final Sound levelUp = Gdx.audio.newSound(Gdx.files.internal(Paths.LevelUp.getPath()));

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

    public Animation<TextureRegion> getIdle_frames() {
        return idle_frames;
    }

    public TextureRegion getIdel0() {
        return idel0;
    }

    public Texture getBullet() {
        return bullet;
    }

    public TextureRegion[] getTreeFrames(){
        TextureRegion[] treeFrames = new TextureRegion[3];
        for (int i = 0; i < 3; i++){
            treeFrames[i] = new TextureRegion(new Texture(Paths.TREE.getPath() + i + ".png"));
        }
        return treeFrames;
    }

    public Animation<TextureRegion> getTreeAnimation() {
        return treeAnimation;
    }

    public TextureRegion[] getTentacleFrames(){
        TextureRegion[] tentacleFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++){
            tentacleFrames[i] = new TextureRegion(new Texture(Paths.TENTACLE.getPath() + i + ".png"));
        }
        return tentacleFrames;
    }

    public Animation<TextureRegion> getTentacleAnimation() {
        return tentacleAnimation;
    }

    public Texture getXpDrop() {
        return XpDrop;
    }

    public Texture getGameBackGround() {
        return gameBackGround;
    }

    public Texture getDamage() {
        return damage;
    }

    public Texture getOppBullet() {
        return oppBullet;
    }

    public TextureRegion[] getEyeBatFrames(){
        TextureRegion[] frames = new TextureRegion[4];
        for (int i = 0; i < 4; i++){
            frames[i] = new TextureRegion(new Texture(Paths.EYEBAT.getPath() + i + ".png"));
        }
        return frames;
    }

    public Animation<TextureRegion> getEyeBatAnimation() {
        return eyeBatAnimation;
    }

    public Sound getShootSound() {
        return shootSound;
    }

    public TextureRegion[] getExplosionFrames(){
        TextureRegion[] frames = new TextureRegion[6];
        for (int i = 0; i < 6; i++){
            frames[i] = new TextureRegion(new Texture(Paths.EXPLOSION.getPath() + i + ".png"));
        }
        return frames;
    }

    public Animation<TextureRegion> getExplosionAnimation() {
        return explosionAnimation;
    }

    public Sound getExplosionSound() {
        return explosionSound;
    }

    public Texture getWeapon() {
        return weapon;
    }

    public TextureRegion[] getReloadFrames(){
        TextureRegion[] frames = new TextureRegion[4];
        for (int i = 0; i < 4; i++){
            frames[i] = new TextureRegion(new Texture(Paths.RELOAD.getPath() + i + ".png"));
        }
        return frames;
    }

    public Animation<TextureRegion> getReloadAnimation() {
        return reloadAnimation;
    }

    public Sound getClickSound() {
        return clickSound;
    }

    public TextureRegion[] getCharFrames(String name){
        TextureRegion[] frames = new TextureRegion[6];
        for (int i = 0; i < 6; i++){
            frames[i] = new TextureRegion(new Texture(Paths.IDLE0.getPath() + name + "/" + i + ".png"));
        }
        return frames;
    }

    public Animation<TextureRegion> getCharAnimation(String name){
        return new Animation<>(0.2f, getCharFrames(name));
    }

    public TextureRegion firstFrameOfChar(String name){
        return new TextureRegion(new Texture(Paths.IDLE0.getPath() + name + "/" + "0.png"));
    }

    public TextureRegion[] getBossFrames(){
        TextureRegion[] frames = new TextureRegion[6];
        for (int i = 0; i < 6; i++){
            frames[i] = new TextureRegion(new Texture(Paths.ELDERBOSS.getPath() + i + ".png"));
        }
        return frames;
    }

    public Animation<TextureRegion> getBossAnimation() {
        return bossAnimation;
    }

    public Sound getYouWinSound() {
        return youWinSound;
    }

    public Sound getYouLoseSound() {
        return youLoseSound;
    }

    public Sound getLevelUp() {
        return levelUp;
    }
}
